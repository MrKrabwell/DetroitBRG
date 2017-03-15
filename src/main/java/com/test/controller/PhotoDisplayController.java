package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
import com.test.entity.Photos;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yosuk on 3/15/2017.
 */
@Controller
public class PhotoDisplayController {

    /**
     * This method will show the browse page for the appropriate category
     * @param category String category of the browse TODO: Can this be an enum??
     * @param model Model, if needed TODO: remove this if not needed
     * @return String browse page
     */
    @RequestMapping(value="browse")
    public String showBrowsePage(@RequestParam("cat") String category,
                                 HttpServletRequest request,
                                 Model model) {

        // Logging
        System.out.println("Showing category " + category + " browse page");

        // TODO: becareful with the start and stop index
        // TODO: Make sure to take into consideration the category
        List<Photos> photos = DatabaseAccess.getTopPhotos(0,3);

        // Add the model names
        model.addAttribute("photos", photos);
        model.addAttribute("imageURL",
                request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/images/");

        // Show the browse page to user
        return "browse";
    }

}