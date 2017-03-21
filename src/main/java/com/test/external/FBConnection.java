package com.test.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class uses facebook's OAuth to register users
 */
public class FBConnection {
    // These values are provided from facebook developer site.  Yosuke's Detroit BRG App ID and App Secret
    public static final String FB_APP_ID = "1114928768619109";
    public static final String FB_APP_SECRET = "a07cefe0048cd27d95c4aaae28a75c05";
    // The URI below is what facebook uses to comeback to request a re-direct.
    public static final String REDIRECT_REQUEST = "login";
    public static String accessToken = "";


    /**
     * This method returns the FB authentication URL
     * @return String URL of the FB authentication
     */
    public static String getFBAuthUrl(String redirectURL) {
        return "http://www.facebook.com/dialog/oauth?" + "client_id="
                + FBConnection.FB_APP_ID + "&redirect_uri="
                + redirectURL + REDIRECT_REQUEST
                + "&scope=email";
    }


    /**
     * This method returns the accessToken for a user
     * @param code
     * @return
     */
    public static String getFBGraphUrl(String code, String redirectURL) {

        String fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=" + FBConnection.FB_APP_ID + "&redirect_uri="
                + redirectURL + REDIRECT_REQUEST
                + "&client_secret=" + FB_APP_SECRET + "&code=" + code;

        return fbGraphUrl;
    }

    /**
     * This method will provide the access token, provided the code from facebook and a redirect URL
     * @param code String code obtained by facebook
     * @param redirectURL String URL to redirect to login
     * @return
     */
    public static String getAccessToken(String code, String redirectURL) {
        //if (accessToken.equals("")) {  // Comment this out to allow for more users to login using facebook
            URL fbGraphURL;
            try {
                fbGraphURL = new URL(getFBGraphUrl(code,redirectURL));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Invalid code received " + e);
            }
            URLConnection fbConnection;
            StringBuffer b = null;
            try {
                fbConnection = fbGraphURL.openConnection();
                BufferedReader in;
                in = new BufferedReader(new InputStreamReader(
                        fbConnection.getInputStream()));
                String inputLine;
                b = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                    b.append(inputLine + "\n");
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect with Facebook "
                        + e);
            }

            accessToken = b.toString();
            if (accessToken.startsWith("{")) {
                throw new RuntimeException("ERROR: Access Token Invalid: "
                        + accessToken);
            }
        //}
        return accessToken;
    }
}