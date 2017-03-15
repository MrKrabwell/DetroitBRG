package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
import com.test.external.FBConnection;
import com.test.external.FBGraph;
import com.test.external.GoogleMapsAPI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * This controller class is for showing pages
 */

@Controller
public class HomeController {

    /**
     * This method will show the index page
     * @param model Model for this page
     * @return String index.jsp page
     */
    @RequestMapping(value="/")
    public String showIndexPage(Model model, HttpServletRequest request)
    {
        // TODO: Use a cookie to remember if the user is logged in or not.
        Cookie[] cookie = request.getCookies();

        // Add a facebook login URL to the model
        model.addAttribute("facebookLogin", FBConnection.getFBAuthUrl());

        // Add the top photos to front page
        // TODO: Modify this to get top photos, not all photos
        model.addAttribute("gMapTopPhotosLocationURL",
                GoogleMapsAPI.getMapsURLOfPhotoLocations(DatabaseAccess.getAllPhotos()));

        // Show the index page
        return "index";
    }


    /**
     * This method will show the image submission page
     * @return String submit page
     */
    @RequestMapping(value="submit")
    public String showSubmitPhotoPage() {
        return "submit-photo";
    }


    /**
     * This method will show the browse page for the appropriate category
     * @param category String category of the browse TODO: Can this be an enum??
     * @param model Model, if needed TODO: remove this if not needed
     * @return String browse page
     */
    @RequestMapping(value="browse")
    public String showBrowsePage(@RequestParam("cat") String category,
                                 Model model) {

        // Logging
        System.out.println("Showing category " + category + " browse page");

        // TODO: Access database to retrieve the photos in order of top score

        // Show the browse page to user
        return "browse";
    }

}
