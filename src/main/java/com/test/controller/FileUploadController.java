package com.test.controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.exif.GpsDirectory;
import com.test.entity.PhotoCategory;
import com.test.dataaccess.DatabaseAccess;
import com.test.entity.Photos;
import com.test.external.ClarifaiAPI;
import com.test.external.GoogleMapsAPI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.binary.Base64;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.*;
import java.io.IOException;


/**
 * This controller class is for file uploads
 */
@Controller
public class FileUploadController {

    /* Class fields */
    private static final String UPLOAD_DIRECTORY = "images";  // Directory path of uploads
    private final double[] DEFAULT_PHOTO_LOCATION = {42.3314, -83.0458}; // In center of Detroit

    /**
     * This method creates a new images directory if it doesn't exist
     * @return boolean true if already created or successfully created, false otherwise
     */
    private boolean createImagesDirectory(String path) {

        // Define the directory
        File imagesDir = new File(path);

        // if the directory does not exist, create it
        if (!imagesDir.exists()) {
            System.out.println(imagesDir.getName() + " directory doesn't exist.  Creating directory...");
            try {
                imagesDir.mkdir();
                System.out.println("Directory successfully created!");
                return true;
            }
            catch (SecurityException se) {
                System.out.println(imagesDir.getName() + " directory could not be created!");
                return false;
            }
        }
        return true;
    }


    /**
     * This method will save the file to the path with filename
     * @param file CommonsMultipartFile from user
     * @param path String of path to save
     * @param filename String of filename to save
     * @return boolean true if saved successfully, false otherwise.
     */
    private boolean saveImageToDirectory(CommonsMultipartFile file,
                                         String path,
                                         String filename) {

        // Create a new directory, if fails, show error page
        if (!createImagesDirectory(path)) {
            return false;
        }

        // Attempt to store file to path
        System.out.println("Attempting to store to " + path);
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(path + File.separator + filename)));
            stream.write(bytes);
            stream.flush();
            stream.close();
            System.out.println(filename + " successfully stored!");

            return true;
        }
        catch (IOException e) {
            System.out.println("Error saving file...");
            e.printStackTrace();
            return false;
        }
    }



    /**
     * This method will get the latitude and longitude of the image, if available
     * @param file CommonsMultipartFile to get information
     * @return double[] first element is latitude, second element is longitude
     */
    private double[] getGeoLocation(CommonsMultipartFile file) {
        double lat = 0.0;
        double lng = 0.0;

        try {
            // Convert CommonsMultipartFile to File
            File convFile = new File(file.getOriginalFilename());
            if (convFile.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(file.getBytes());
                fos.close();
            }

            // Get the get location
            Metadata metadata = ImageMetadataReader.readMetadata(convFile);
            if (metadata.containsDirectory(GpsDirectory.class)) {
                GpsDirectory gpsDir = (GpsDirectory) metadata.getDirectory(GpsDirectory.class);
                GeoLocation location = gpsDir.getGeoLocation();
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        }
        catch (ImageProcessingException e) {
            System.out.println("Uh oh!  Error getting geolocation information");
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            System.out.println("Uh oh!  Error getting geolocation information");
            e.printStackTrace();
            return null;
        }
        catch (NullPointerException e) {
            System.out.println("No latitude or longitude information available!!");
            e.printStackTrace();
            return null;
        }

        return (new double[]{lat, lng});
    }



    /**
     * This method will upload the photo that the user
     * @param file CommonsMultipartFile file uploaded by user
     * @param session HttpSession current HTTP session
     * @param model Model of view
     * @return
     */
    @RequestMapping(value="preview", method= RequestMethod.POST)
    public String showUploadPreview(@RequestParam("file") CommonsMultipartFile file,
                                    @RequestParam("category") PhotoCategory category,
                                    HttpSession session,
                                    HttpServletRequest request,
                                    Model model ) {

        // Get geolocation if available
        // double[] latLon is a two element array, where first element is latitude, and second is Longitude
        double[] latLng = getGeoLocation(file);

        // If latLng is null, default location is set
        if (latLng == null) {
            model.addAttribute("lat", DEFAULT_PHOTO_LOCATION[0]);
            model.addAttribute("lng", DEFAULT_PHOTO_LOCATION[1]);
        }
        else {
            model.addAttribute("lat", latLng[0]);
            model.addAttribute("lng", latLng[1]);
        }


        // Get URL of images and add to model
        model.addAttribute("imageURL",
                request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/temp");

        // Add the Google maps API key
        model.addAttribute("apiKey", GoogleMapsAPI.getApiKey());

        // Add the categories to display
        model.addAttribute("category", PhotoCategory.values());

        /////////////////////

        // Define the file path and name
        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);

        // Check to see if image meets the criteria
        switch (category) {
            case BEAUTY:
                if (!ClarifaiAPI.determineBeautyClarifai(file.getBytes())) {
                    return "invalid-photo";
                }
                break;
            case ART:
                if (!ClarifaiAPI.streetArtModelClarifai(file.getBytes())) {
                    return "invalid-photo";
                }
                break;
            case REMAINS:
                if (!ClarifaiAPI.oldDetroitModelClarifai(file.getBytes())) {
                    return "invalid-photo";
                }
                break;
            default:
                System.out.println("Error!  Can't determine category");
                return "error";
        }

        // Get highest primary key of Photos
        String filename = file.getOriginalFilename();
        filename = DatabaseAccess.getNextPhotoPrimaryKey() + "_" + filename;  // TODO: This method may cause an exception if no photos available

        // Create a new Photos entity to store into the database
        Photos photo = new Photos();
        photo.setFileName(filename);
        photo.setCategory(category.toString());
        if (latLng != null) {
            photo.setLatitude(Double.toString(latLng[0]));
            photo.setLongitude(Double.toString(latLng[1]));
        }
        else {
            photo.setLatitude("0.0");
            photo.setLongitude("0.0");
        }

        // Store photo entity to database
        if (!DatabaseAccess.insertPhotoToDatabase(photo)) {
            return "error";
        }

        // Write to the images directory
        if (!saveImageToDirectory(file, path, filename)) {
            return "error";
        }




        /////////////////////



        // Show preview-upload page
        // TODO: This needs to be changed back to preview upload
        // return "preview-upload";
        return "confirm-upload";
    }



    // TODO: Upload after confiramtion
    @RequestMapping(value="upload", method=RequestMethod.POST)
    public String uploadPhoto(@RequestParam("file") CommonsMultipartFile file,
                              @RequestParam("category") PhotoCategory category,
                              @RequestParam("lat") double lat,
                              @RequestParam("lng") double lng,
                              HttpSession session) {


        // Define the file path and name
        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);

        // Check to see if image meets the criteria
        switch (category) {
            case BEAUTY:
                if (!ClarifaiAPI.determineBeautyClarifai(file.getBytes())) {
                    return "invalid-photo";
                }
                break;
            case ART:
                if (!ClarifaiAPI.streetArtModelClarifai(file.getBytes())) {
                    return "invalid-photo";
                }
                break;
            case REMAINS:
                if (!ClarifaiAPI.oldDetroitModelClarifai(file.getBytes())) {
                    return "invalid-photo";
                }
                break;
            default:
                System.out.println("Error!  Can't determine category");
                return "error";
        }

        // Get highest primary key of Photos
        String filename = file.getOriginalFilename();
        filename = DatabaseAccess.getNextPhotoPrimaryKey() + "_" + filename;  // TODO: This method may cause an exception if no photos available

        // Get the lat and lng from the page
        double[] latLng = {lat, lng};

        // Create a new Photos entity to store into the database
        Photos photo = new Photos();
        photo.setFileName(filename);
        photo.setCategory(category.toString());
        if (latLng != null) {
            photo.setLatitude(Double.toString(latLng[0]));
            photo.setLongitude(Double.toString(latLng[1]));
        }
        else {
            photo.setLatitude("0.0");
            photo.setLongitude("0.0");
        }

        // Store photo entity to database
        if (!DatabaseAccess.insertPhotoToDatabase(photo)) {
            return "error";
        }

        // Write to the images directory
        if (!saveImageToDirectory(file, path, filename)) {
            return "error";
        }

        // Return the view.
        return "confirm-upload";
    }


//    @RequestMapping(value = "temp")
//    @ResponseBody
//    public byte[] showTempImage()  {
//        return tempImage.getBytes();
//    }

}
