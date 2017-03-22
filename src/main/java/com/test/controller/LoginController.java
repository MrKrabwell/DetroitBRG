package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
import com.test.entity.PhotoCategory;
import com.test.entity.Users;
import com.test.external.FBConnection;
import com.test.external.FBGraph;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * This controller class handles all the login related tasks
 */
@Controller
public class LoginController {


    /**
     * This method will check if the user is already logged in or not depending
     * TODO: WAIT DO WE EVEN NEED THIS??
     * @param cookies
     * @return
     */
    public static boolean userLoggedIn(Cookie[] cookies) {
        for(Cookie cookie : cookies){
            if("userID".equals(cookie.getName())){
                // Check if user logged in;
                Users user = DatabaseAccess.getUser(cookie.getName());

                return true;
            }
        }
        return false;
    }


    /**
     * This method is the redirect from facebook OAuth to register a new user.
     * @param code code returned from FB's OAuth
     * @param model Model to show information on page
     * @return String either new-user page or error page, depending on result
     */
    @RequestMapping(value="user")
    public String showLoginResult(@RequestParam("code") String code,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  HttpSession session,
                                  Model model) {

        // See if we got anything back for code
        if (code == null || code.equals("")) {
            throw new RuntimeException("ERROR: Did not get any code parameter in callback.  " +
                    "Facebook registration failed");
        }

        // If code is valid, create a new fb connection
        String accessToken = FBConnection.getAccessToken(code,
                request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/");

        // Use the token to get fbGraph object i.e. user's info
        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

        // Create strings for displaying info
        Users user = new Users();
        user.setUserId(fbProfileData.get("id"));
        user.setFirstName(fbProfileData.get("first_name"));
        user.setLastName(fbProfileData.get("last_name"));
        if (!DatabaseAccess.userExists(user.getUserId())) {
            DatabaseAccess.registerNewUser(user);
        }

        // If it gets here, then we can send a cookie to the user to remember.
        // TODO: Do we need this?
        Cookie cookie = new Cookie("userID", fbProfileData.get("id"));
        cookie.setMaxAge(-1); // Set it to only last for one browser session
        response.addCookie(cookie);

        // Use a session to remember that user is logged in.
        if (session.getAttribute("loggedIn") == null || !LoginController.userLoggedIn(session)) {
            session.setAttribute("loggedIn",true);
            session.setAttribute("userID", fbProfileData.get("id"));
        }

        // Add a attribute to reference the user later
        model.addAttribute("userFirstName", fbProfileData.get("first_name"));

        return "user";
    }


    /**
     * This method determines if the user is logged in via current session
     * @param session HttpSession current session
     * @return boolean true if user is logged in, false otherwise
     */
    public static boolean userLoggedIn(HttpSession session) {
        if (session.getAttribute("loggedIn") != null && (Boolean)session.getAttribute("loggedIn")) {
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * This method will logout the user and return the home page
     * @param request HttpServletRequest, to get the current context for FB Login URL
     * @param session HttpSession session to change the user login status
     * @param model Model to add all the attributes to the homepage
     * @return String homepage
     */
    @RequestMapping(value="logout")
    public static String logoutUser(HttpServletRequest request,
                                    HttpSession session,
                                    Model model) {

        // If user is not logged in, just return the home page
        if (session.getAttribute("loggedIn") == null || !LoginController.userLoggedIn(session)) {
            return "/";
        }

        // Logout user
        session.setAttribute("loggedIn",false);
        model.addAttribute("userFirstName", null);

        // Add a facebook login URL to the model, first get redirect URL
        model.addAttribute("facebookLogin",
                FBConnection.getFBAuthUrl(
                        request.getScheme() + "://" +
                                request.getServerName() + ":" +
                                request.getServerPort() + "/"
                ));

        // Add an ability to call the category
        model.addAttribute("category", PhotoCategory.values());

        return "index";
    }


}
