package com.test.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * This controller class is for file uploads
 */

@Controller
public class FileUploadController {

    /* Class fields */
    private static final String UPLOAD_DIRECTORY ="images";  // Directory path of uploads TODO: Do we want this in WEB-INF??


    /**
     * This method creates a new images directory if it doesn't exist
     * @return boolean true if already created or successfully created, false otherwise
     */
    private boolean createImagesDirectory(String path) {

        // Define the directory
        File imagesDir = new File(path);

        // if the directory does not exist, create it
        if (!imagesDir.exists()) {
            System.out.println("creating directory: " + imagesDir.getName());
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
     * This method will upload the photo that the user
     * @param file CommonsMultipartFile file uploaded by user
     * @param session HttpSession current HTTP session
     * @param model Model of view
     * @return
     */
    @RequestMapping(value="uploadPhoto", method= RequestMethod.POST)
    public String uploadResources(@RequestParam CommonsMultipartFile file,
                                        HttpSession session,
                                        HttpServletRequest request,
                                        Model model ) {

        // Define the file path and name
        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);
        String filename = file.getOriginalFilename();

        System.out.println("Attempting to store to " + path);

        // Create a new directory, if fails, show error page
        if (!createImagesDirectory(path)) {
            return "error";
        }

        // Write to the images directory
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                    new File(path + File.separator + filename)));
            stream.write(bytes);
            stream.flush();
            stream.close();
            System.out.println(filename + " successfully stored!");
        }
        catch (IOException e) {
            System.out.println("Error saving file...");
            e.printStackTrace();
            return "error";
        }

        // Add the image attribute to model
        model.addAttribute("uploadedImage",
                        request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/images/" + filename);

        return "confirmUpload";
    }

}
