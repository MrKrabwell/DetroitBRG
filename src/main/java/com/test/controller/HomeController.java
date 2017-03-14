package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

/**
 * This controller class is for showing pages
 */

@Controller
public class HomeController {


    /**
     * This method will show the index page
     * @return ModelAndView of index page todo: maybe we don't need a model here...
     */
    @RequestMapping(value="/")
    public ModelAndView showIndexPage()
    {
        return new ModelAndView("index", "message", "Go Team 3!");
    }


    /**
     * This method will show the image submission page
     * @return ModelAndView of submission page
     */
    @RequestMapping(value="showSubmitPhotoPage")
    public ModelAndView showSubmitPhotoPage() {
        return new ModelAndView("submitPhoto");
    }


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
