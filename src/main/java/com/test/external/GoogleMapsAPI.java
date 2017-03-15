package com.test.external;

import com.test.entity.Photos;

import java.util.List;

/**
 * Created by yosuk on 3/15/2017.
 */
public class GoogleMapsAPI {

    // This is the default string for Google Maps API URL
    private static final String GMAP_DEFAULT_URL  =
            "https://maps.googleapis.com/maps/api/staticmap?" +
                    "center=Detroit+Michigan&zoom=10&size=1000x600&maptype=roadmap&markers=color:blue%7C";
    // This is Rob's Google Maps API Key
    private static final String GMAP_API_KEY = "AIzaSyBh9tfoktOW9Xl28YUX7l6jipoSN4ji_7I";

    public static String getMapsURLOfPhotoLocations(List<Photos> photos) {

        String gMapsURL = GMAP_DEFAULT_URL;

        // Concatenate all the latitude and longitudes of the photos
        for (Photos p : photos) {
            gMapsURL += (p.getLatitude() + "," + p.getLongitude() + "%7C");
        }

        gMapsURL += ("&key=" + GMAP_API_KEY);

        return gMapsURL;
    }


}
