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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private final double[] LAT_COORDINATE_LIMIT = {42.30, 42.40};
    private final double[] LNG_COORDINATE_LIMIT = {-83.13, -82.96};
    private static byte[] tempFile = null;
    private static String tempFileName = null;

    /**
     * This method creates a new images directory if it doesn't exist
     * @return boolean true if already created or successfully created, false otherwise
     */
    private boolean createDirectory(String path) {

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
    private boolean saveImageToDirectory(byte[] file,
                                         String path,
                                         String filename) {

        // Create a new directory, if fails, show error page
        if (!createDirectory(path)) {
            return false;
        }

        // Attempt to store file to path
        System.out.println("Attempting to store to " + path);
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(path + File.separator + filename)));
            stream.write(file);
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
    private double[] getGeoLocation(CommonsMultipartFile file, String currPath) {
        double lat = 0.0;
        double lng = 0.0;

        try {

            // Create new directory to store the temporary file
            if (!createDirectory(currPath + "temp/")) {
                return null;
            }

            // Convert CommonsMultipartFile to File
            File convFile = new File(currPath + "temp/" ,file.getOriginalFilename());
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
     * @return String view of preview page
     */
    @RequestMapping(value="preview", method= RequestMethod.POST)
    public String showUploadPreview(@RequestParam("file") CommonsMultipartFile file,
                                    @RequestParam("category") PhotoCategory category,
                                    HttpSession session,
                                    HttpServletRequest request,
                                    Model model ) {

        // Temporarily store file to store later
        tempFile = null;
        tempFile = file.getBytes();
        tempFileName = file.getOriginalFilename();

        // Need current path for server deployment
        String currPath = request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort() + "/";

        // Get geolocation if available
        // double[] latLon is a two element array, where first element is latitude, and second is Longitude
        double[] latLng = getGeoLocation(file, currPath);

        // If latLng is null, default location is set
        if (latLng == null) {
            model.addAttribute("lat", DEFAULT_PHOTO_LOCATION[0]);
            model.addAttribute("lng", DEFAULT_PHOTO_LOCATION[1]);
            model.addAttribute("geoMessage", "Your photo did not contain data " +
                    "that indicates where the picture was taken.<br>" +
                    "We've dropped a pin in the center of Detroit.  " +
                    "Move it to where the photo was taken by double-clicking on the map.");
        }
        else if ( // If not in limit, return invalid
                latLng[0] < LAT_COORDINATE_LIMIT[0]
                        || latLng[0] > LAT_COORDINATE_LIMIT[1]
                        || latLng[1] < LNG_COORDINATE_LIMIT[0]
                        || latLng[1] > LNG_COORDINATE_LIMIT[1]) {
            model.addAttribute("message", "Your photo contained data that tells us it was taken outside of Detroit!");
            return "invalid-photo";
        }
        else {
            model.addAttribute("lat", latLng[0]);
            model.addAttribute("lng", latLng[1]);
            model.addAttribute("geoMessage", "Your photo contained data " +
                    "that indicates the picture was taken where the pin is dropped.<br>" +
                    "If this is incorrect, double click on the map where it was taken to change the pin location.");
        }

        // Get URL of images and add to model
        model.addAttribute("imageURL", currPath + "temp/" + file.getOriginalFilename());

        // Add the Google maps API key
        model.addAttribute("apiKey", GoogleMapsAPI.getApiKey());

        // Add the categories to display
        model.addAttribute("category", category);

        // Show preview-upload page
        return "preview-upload";
    }


    /**
     * This method will finally upload the photo once the user confirms the details on the preview page
     * @param category PhotoCategory category of the
     * @param lat double latitude of the photo
     * @param lng double longitude of the photo
     * @param session HttpSession current session to referene path
     * @param model Model to return error message
     * @return String view of confirm upload or error
     */
    @RequestMapping(value="upload", method=RequestMethod.POST)
    public String uploadPhoto(@RequestParam("category") PhotoCategory category,
                              @RequestParam("lat") double lat,
                              @RequestParam("lng") double lng,
                              HttpSession session,
                              Model model) {


        // Define the file path and name
        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);

        // Check to see if image meets the criteria
        switch (category) {
            case SKYLINE:
                if (!ClarifaiAPI.determineBeautyClarifai(tempFile)) {
                    model.addAttribute("message", "Photo photo doesn't match the " + category.toString() + " category!");
                    return "invalid-photo";
                }
                break;
            case STREET_ART:
                if (!ClarifaiAPI.streetArtModelClarifai(tempFile)) {
                    model.addAttribute("message", "Photo photo doesn't match the " + category.toString() + " category!");
                    return "invalid-photo";
                }
                break;
            case OLD_DETROIT:
                if (!ClarifaiAPI.oldDetroitModelClarifai(tempFile)) {
                    model.addAttribute("message", "Photo photo doesn't match the " + category.toString() + " category!");
                    return "invalid-photo";
                }
                break;
            default:
                System.out.println("Error!  Can't determine category.");
                model.addAttribute("message", "Can't determine category.");
                return "error";
        }

        // Get highest primary key of Photos
        String filename = tempFileName;
        filename = DatabaseAccess.getNextPhotoPrimaryKey() + "_" + filename;  // TODO: This method may cause an exception if no photos available

        // Get the lat and lng from the page
        double[] latLng = {lat, lng};

        if ( // If not in limit, return invalid
                latLng[0] < LAT_COORDINATE_LIMIT[0]
                        || latLng[0] > LAT_COORDINATE_LIMIT[1]
                        || latLng[1] < LNG_COORDINATE_LIMIT[0]
                        || latLng[1] > LNG_COORDINATE_LIMIT[1]) {
            model.addAttribute("message", "You chose a location that is outside of Detroit!");
            return "invalid-photo";
        }

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
            model.addAttribute("message", "Could not store photo information to database.  " +
                    "Please contact administrator.");
            return "error";
        }

        // Write to the images directory
        if (!saveImageToDirectory(tempFile, path, filename)) {
            model.addAttribute("message", "Could not store image data to server.  " +
                    "Please contact administrator.");
            return "error";
        }

        // Let user browse the category
        model.addAttribute("category", category);

        // Return the view.
        return "confirm-upload";
    }


    /**
     * This handles showing the temporary
     * @param imageId
     * @return
     */
    @RequestMapping(value = "/temp/{imageId}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imageId)  {
        // Wait until byte array
        while (tempFile == null) {
            // Do nothing
        }
        return tempFile;
    }

}
