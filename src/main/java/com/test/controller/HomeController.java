package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
import com.test.entity.PhotoCategory;
import com.test.entity.Users;
import com.test.external.FBConnection;
import javafx.scene.chart.PieChart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
    public String showIndexPage(HttpServletRequest request,
                                HttpSession session,
                                Model model) {

        // Report Spring Version to Console
        System.out.println("Welcome, this website is built using Spring " +
                org.springframework.core.SpringVersion.getVersion());

        // Check to see if user is logged in, and provide name
        if (LoginController.userLoggedIn(session)) {
            model.addAttribute("loggedIn", true);
            Cookie[] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if("userID".equals(cookie.getName())){
                    Users user = DatabaseAccess.getUser(cookie.getValue());
                    model.addAttribute("userFirstName", user.getFirstName());
                }
            }
        }
        else {
            model.addAttribute("loggedIn", false);
            model.addAttribute("userFirstName", null);
        }

        // Add a facebook login URL to the model, first get redirect URL
        model.addAttribute("facebookLogin",
                FBConnection.getFBAuthUrl(
                request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/"
        ));

        // TODO: Add the top 3 photos to front page
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

    @RequestMapping(value="TermOfService")
    public String showTermsOfService(){
        return "TermOfService";
    }

}
