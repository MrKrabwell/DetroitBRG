package com.test.controller;

import com.test.entity.PhotoCategory;
import com.test.external.FBConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * This controller class is for the home page
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

        // Add the top 3 photos to front page
//        model.addAttribute("gMapTopPhotosLocationURL",
//                GoogleMapsAPI.getMapsURLOfPhotoLocations(DatabaseAccess.getTopPhotos(0,3)));

        // Add an ability to call the category
        model.addAttribute("category", PhotoCategory.values());

        // Show the index page
        return "index";
    }


    /**
     * This method will show the image submission page
     * @param model Model to add the categories drop down
     * @return submit-photo page
     */
    @RequestMapping(value="submit")
    public String showSubmitPhotoPage(Model model) {
        model.addAttribute("category", PhotoCategory.values());
        return "submit-photo";
    }

}
