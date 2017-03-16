package com.test.controller;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.exif.GpsDirectory;
import com.test.entity.PhotoCategory;
import com.test.dataaccess.DatabaseAccess;
import com.test.entity.Photos;
import com.test.external.ClarifaiAPI;
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.IOException;
import java.util.List;

/**
 * This controller class is for file uploads
 */
@Controller
public class FileUploadController {

    /* Class fields */
    private static final String UPLOAD_DIRECTORY = "images";  // Directory path of uploads TODO: Do we want this in WEB-INF??
    private static final String CLARIFAI_APP_ID = "zVFg39Fi---sN1IcbsSsG13I7Ldc1Xdb2adszB5A"; // Coleman's APP ID for Clarifai
    private static final String CLARIFAI_API_KEY = "1KBAt4KfnY6gG094Okne07fpNI0aXn0drfVBAZ5U"; // Coleman's API Key for Clarifai

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
     * This method will validate whether the image is within the category
     * @param file CommonsMultipartFile of file to validate
     * @param category String category to check against
     * @return boolean true if within category, false if otherwise
     */
    private boolean validateImageClarifai(CommonsMultipartFile file, PhotoCategory category) {
        // Initialize the ClarifaiClient
        ClarifaiClient client = new ClarifaiBuilder(CLARIFAI_APP_ID, CLARIFAI_API_KEY).buildSync();

        // Send the image to Clarifai API and get the results
        final List<ClarifaiOutput<Concept>> predictionResults =
                client.getDefaultModels().generalModel() // You can also do Clarifai.getModelByID("id") to get custom models
                        .predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(file.getBytes())))
                        .executeSync() // optionally, pass a ClarifaiClient parameter to override the default client instance with another one
                        .get();

        // TODO: parse through predictionResults data object to get the results
        // This puts the data into a JSON Array TODO: doesn't work...yet,  We can use the ClarifaiController
        JSONArray jsonDataArray = new JSONArray(predictionResults.get(0).data());


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
    @RequestMapping(value="uploadPhoto", method= RequestMethod.POST)
    public String uploadPhoto(@RequestParam("file") CommonsMultipartFile file,
                                  @RequestParam("category") PhotoCategory category,
                                        HttpSession session,
                                        HttpServletRequest request,
                                        Model model ) {

        // Define the file path and name
        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);

        // Create a new directory, if fails, show error page
        if (!createImagesDirectory(path)) {
            return "error";
        }

        // Check to see if image meets the criteria
        switch (category) {
            case BEAUTY:
                if (!ClarifaiAPI.determineBeautyClarifai(file.getBytes())) {
                    return "error";
                }
                break;
            case ART:
                if (!ClarifaiAPI.streetArtModelClarifai(file.getBytes())) {
                    return "error";
                }
                break;
            case REMAINS:
                if (!ClarifaiAPI.oldDetroitModelClarifai(file.getBytes())) {
                    return "error";
                }
                break;
            default:
                System.out.println("Error!  Can't determine category");
                break;
        }


        // Get geolocation
        // double[] latLon is a two element array, where first element is latitude, and second is Longitude
        double[] latLng = getGeoLocation(file);

        // TODO: if latLng is null, ask user to input geoLocation, maybe do this in the getGeoLocation method
//        if (latLng == null) {
//            return "error";
//        }

        // Get highest primary key of Photos
        String filename = file.getOriginalFilename();
        filename = DatabaseAccess.getNextPhotoPrimaryKey() + "_" + filename;

        // Create a new Photos entity to store into the database
        // TODO: Change Entity to take in double for lat and lng, currently taking in string.
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

        // Add the image attribute to model
        model.addAttribute("uploadedImage",
                        request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/images/" + filename);

        return "confirm-upload";
    }

}
