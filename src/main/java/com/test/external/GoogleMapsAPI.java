package com.test.external;

import com.test.entity.Photos;

import java.util.List;

/**
 * This class is for interfacing with the Google Maps API
 */
public class GoogleMapsAPI {

    // This is the default string for Google Maps API URL
    private static final String GMAP_DEFAULT_URL  =
            "https://maps.googleapis.com/maps/api/staticmap?" +
                    "center=Detroit+Michigan&zoom=10&size=1000x600&maptype=roadmap&markers=color:blue%7C";

    private static final String GMAP_SINGLE_PHOTO_URL =
            "https://maps.googleapis.com/maps/api/staticmap?" +
                    "zoom=12&size=250x250&maptype=roadmap&markers=color:blue%7C";

    // This is Yosuke's Google Maps JS API Key
    private static final String GMAP_API_KEY = "AIzaSyB5R8XSQs8UQrW49sj_AM9nrQBuKOGRIak";


    /**
     * Getter for the API Key
     * @return String API key
     */
    public static String getApiKey() {
        return GMAP_API_KEY;
    }


    /**
     * This method will return a static Google Map with pins of the locations of the photos
     * @param photos List<Photos> of photos to display the location
     * @return String URL to use the Google Maps API
     */
    public static String getMapsURLOfPhotoLocation(List<Photos> photos) {

        String gMapsURL = GMAP_DEFAULT_URL;

        // Concatenate all the latitude and longitudes of the photos
        for (Photos p : photos) {
            gMapsURL += (p.getLatitude() + "," + p.getLongitude() + "%7C");
        }

        // Concatenate the API Key
        gMapsURL += ("&key=" + GMAP_API_KEY);

        // Return the URL
        return gMapsURL;
    }

    /**
     * This method will return a static Google Map with a single pin of a single photo
     * @param photo the Photos entity to get the latitude and longitude information
     * @return String Google Maps URL for the Google Maps API
     */
    public static String getMapsURLOfPhotoLocation(Photos photo) {

        String gMapsURL = GMAP_SINGLE_PHOTO_URL;

        // Concatenate all the latitude and longitudes of the photos
        gMapsURL += (photo.getLatitude() + "," + photo.getLongitude());

        // Concatenate the API Key
        gMapsURL += ("&key=" + GMAP_API_KEY);

        // Return the URL
        return gMapsURL;
    }

}
