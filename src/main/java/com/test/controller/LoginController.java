package com.test.controller;

import com.test.external.FBConnection;
import com.test.external.FBGraph;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This controller class handles all the login related tasks
 */
@Controller
public class LoginController {

    /**
     * This method is the redirect from facebook OAuth to register a new user.
     * @param code code returned from FB's OAuth
     * @param model Model to show information on page
     * @return String either new-user page or error page, depending on result
     */
    @RequestMapping(value="welcome-new-user")
    public String showLoginResult(@RequestParam("code") String code,
                                  HttpServletResponse response,
                                  Model model) {

        // See if we got anything back for code
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



        // If it gets here, then we can send a cookie to the user to remember.
        response.addCookie(new Cookie("userID", fbProfileData.get("id")));

        // Add a attribute to reference the user later
        model.addAttribute("userFirstName", fbProfileData.get("name"));

        return "new-user";
    }

}
