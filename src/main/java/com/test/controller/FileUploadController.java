package com.test.controller;

import com.test.entity.Photos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.hibernate.cfg.Configuration;
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
     * This method will save the file information to the
     * @param path
     * @param filename
     * @return
     */
    private boolean storeInfoToDatabase(String path, String filename) {
        // Set up configuration (This is for using Hibernate 4.3.11)
        // TODO: SessionFactory has a large footprint, should only have one instance
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        // Create session with session factory
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
        Session session = sessionFactory.openSession();

        // Begin the transaction
        Transaction tr = session.beginTransaction();

        //
        Photos photo = new Photos();
        photo.setFileName(filename);
        photo.setLatitude("");
        photo.setLongitude("");

        session.save(photo);
        tr.commit();

        // TODO: Need to fix to store information
        // Create a criteria
        //Criteria criteria = session.createCriteria(Photos.class);

        //List<Photos> list = criteria.list();

        // Close the session
        session.close();

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

        // Create a new directory, if fails, show error page
        if (!createImagesDirectory(path)) {
            return "error";
        }

        // Write to the images directory
        if (!saveImageToDirectory(file, path, filename)) {
            return "error";
        }

        // TODO: Get geolocation

        // TODO: Store to database
        if (!storeInfoToDatabase(path, filename)) {
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
