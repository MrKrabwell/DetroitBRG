package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
import com.test.entity.PhotoCategory;
import com.test.entity.Photos;
import org.hibernate.metamodel.relational.Database;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yosuk on 3/15/2017.
 */
@Controller
public class PhotoDisplayController {

    // Class fields
    private int NUM_PHOTO_PER_PAGE = 8;  // number of photos per page


    /**
     * This method will show the browse page for the appropriate category
     * @param category String category of the browse TODO: Can this be an enum??
     * @param model Model, if needed TODO: remove this if not needed
     * @return String browse page
     */
    @RequestMapping(value="browse")
    public String showBrowsePage(@RequestParam("cat") PhotoCategory category,
                                 @RequestParam("page") int currentPage,
                                 HttpServletRequest request,
                                 Model model) {

        // Logging
        System.out.println("PhotoDisplayController.showBrowsePage: Showing category " + category + " browse page");

        // Get total number of photos for a category
        int numEntries = DatabaseAccess.getNumberOfEntries(category);

        // If no entries, show empty page
        // TODO: think of good logic here for JSP page instead of error
        if (numEntries < 1) {
            return "error";
        }

        // Determine number of pages needed
        int numPages = 0;
        if (numEntries % NUM_PHOTO_PER_PAGE != 0) {
            numPages = numEntries / NUM_PHOTO_PER_PAGE + 1;
        }
        else {
            numPages = numEntries / NUM_PHOTO_PER_PAGE;
        }
        model.addAttribute("numPages", numPages);

        // create Photo list to add to model depending on current page
        List<Photos> photos = null;  // Initialize
        if ( // This if statement is for displaying pages with NUM_PHOTO_PER_PAGE photos
                (currentPage == numPages && numEntries % NUM_PHOTO_PER_PAGE == 0)
                || (numPages >= 1 && currentPage < numPages))
        {
            photos = DatabaseAccess
                    .getTopPhotos((currentPage-1)*NUM_PHOTO_PER_PAGE,
                            (currentPage-1)*NUM_PHOTO_PER_PAGE+(NUM_PHOTO_PER_PAGE-1),
                            category);
        }
        else if ( // This if statement is for the last page if there are less than NUM_PHOTO_PER_PAGE photos
                currentPage == numPages && numEntries % NUM_PHOTO_PER_PAGE != 0)
        {
            photos = DatabaseAccess
                    .getTopPhotos((currentPage-1)*NUM_PHOTO_PER_PAGE,
                            (currentPage-1)*NUM_PHOTO_PER_PAGE+(NUM_PHOTO_PER_PAGE-1),
                            category);
        }
        else { // You shouldn't get here...
            return "error";
        }

        // Add the photos list to attribute
        model.addAttribute("photos", photos);
        model.addAttribute("imageURL",
                request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/images/");

        // Add current page information
        model.addAttribute("currentPage", currentPage);

        // Add number of pages
        model.addAttribute("numPages",numPages);

        // Add the current category
        model.addAttribute("category",category);

        // Show the browse page to user
        return "browse";
    }

}