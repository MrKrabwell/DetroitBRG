package com.test.external;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the FBGraph API to get information from user
 */
public class FBGraph {
    private String accessToken;


    /**
     * Contructor, need an accessToken to construct
     * @param accessToken String of access Token received by FB OAuth
     */
    public FBGraph(String accessToken) {
        this.accessToken = accessToken;
    }


    /**
     * Method to get FB Graph data
     * @return String of the graph response from API
     */
    public String getFBGraph() {
        String graph;
        try {

            // The API request is asking for ID, first name, last name, full name, and email
            String g = "https://graph.facebook.com/me?fields=id,name,first_name,last_name,email&" + accessToken;
            URL u = new URL(g);
            URLConnection c = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    c.getInputStream()));
            String inputLine;
            StringBuffer b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine + "\n");
            in.close();
            graph = b.toString();
            // Logging what the graph data looks like
            System.out.println(graph);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in getting FB graph data. ");
        }
        return graph;
    }

    /**
     * This method maps the User node to a Map for easy access.
     * See facebook Graph API for more details
     * @param fbGraph fbGraph data from API to convert into a JSONObject
     * @return fbProfile hash map for easy access in controller/database access
     */
    public Map<String,String> getGraphData(String fbGraph) {
        Map<String,String> fbProfile = new HashMap<String,String>();
        try {
            JSONObject json = new JSONObject(fbGraph);
            fbProfile.put("id", json.getString("id"));
            fbProfile.put("name", json.getString("name"));
            if (json.has("first_name")) {
                fbProfile.put("first_name", json.getString("first_name"));
            }
            if (json.has("last_name")) {
                fbProfile.put("last_name", json.getString("last_name"));
            }
            if (json.has("email")) {
                fbProfile.put("email", json.getString("email"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR in parsing FB graph data. ");
        }
        return fbProfile;
    }
}
