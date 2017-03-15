package com.test.controller;

import com.test.external.FBConnection;
import com.test.external.FBGraph;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

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
    public String showIndexPage(Model model)
    {
        model.addAttribute("facebookLogin", FBConnection.getFBAuthUrl());

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
     * This method is the redirect from facebook OAuth to register a new user.
     * @param code code returned from FB's OAuth
     * @param model Model to show information on page
     * @return String either new-user page or error page, depending on result
     */
    @RequestMapping(value="welcome-new-user")
    public String showLoginResult(@RequestParam("code") String code,
                                  Model model) {

        // See if we got anyting back for code
        if (code == null || code.equals("")) {
            // TODO: can we make it to show an error page here??
            throw new RuntimeException("ERROR: Did not get any code parameter in callback.  Registeration failed");
        }

        // If code is valid, create a new fb connection
        String accessToken = FBConnection.getAccessToken(code);

        // Use the token to get fbGraph object i.e. user's info
        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

        // Create strings for displaying info
        // TODO: register to database, if it doesn't exist already
//        String out = "";
//        out = out.concat("<div>Welcome " + fbProfileData.get("name"));
//        out = out.concat("<div>Your Email: " + fbProfileData.get("email"));

        model.addAttribute("userFirstName", fbProfileData.get("name"));

        return "new-user";
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
