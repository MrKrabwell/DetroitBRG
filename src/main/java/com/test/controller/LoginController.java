package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
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
    @RequestMapping(value="register")
    public String showLoginResult(@RequestParam("code") String code,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  HttpSession session,
                                  Model model) {

        // See if we got anything back for code
        if (code == null || code.equals("")) {
            // TODO: can we make it to show an error page here??
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
        // TODO: register to database, if it doesn't exist already
        Users user = new Users();
        user.setUserId(fbProfileData.get("id"));
        user.setFirstName(fbProfileData.get("first_name"));
        user.setLastName(fbProfileData.get("last_name"));
        if (!DatabaseAccess.userExists(user.getUserId())) {
            DatabaseAccess.registerNewUser(user);
        }

        // If it gets here, then we can send a cookie to the user to remember.
        // TODO: Do we need this?
        response.addCookie(new Cookie("userID", fbProfileData.get("id")));

        // Use a session to remember that user is logged in.
        if (session.getAttribute("loggedIn") == null) {
            session.setAttribute("loggedIn",true);
        }

        // Add a attribute to reference the user later
        model.addAttribute("userFirstName", fbProfileData.get("first_name"));

        return "user";
    }


    @RequestMapping("login")
    public String login() {

        return "";
    }


    /**
     * This method determines if the user is logged in
     * @param session
     * @return
     */
    public static boolean userLoggedIn(HttpSession session) {
        if (session.getAttribute("loggedIn") != null && (Boolean)session.getAttribute("loggedIn")) {
            return true;
        }
        else {
            return false;
        }
    }

}
