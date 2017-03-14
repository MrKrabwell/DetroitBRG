package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


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





}
