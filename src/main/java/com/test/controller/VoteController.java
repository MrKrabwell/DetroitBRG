package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
import com.test.entity.PhotoCategory;
import com.test.entity.Photos;
import com.test.entity.Users;
import com.test.entity.VoteHistory;
import com.test.external.GoogleMapsAPI;
import javafx.scene.chart.PieChart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by yosuk on 3/20/2017.
 */
@Controller
public class VoteController {

    /**
     * This method is responsible for updating the votes
     * @param photoID int photoID to identify the photo in database
     * @param upvote boolean, true if user is upvoting, false if user is downvoting
     * @param session HttpSession to see if user is logged in
     * @param model Model to be returned in the view
     * @return String view of resulting page
     */
    @RequestMapping(value="vote")
    public String votePhoto(@RequestParam("photoId") int photoID,
                            @RequestParam("type") boolean upvote,
                            @RequestParam("prev") int prevPage,
                            HttpSession session,
                            HttpServletRequest request,
                            Model model) {

        // Add the return to gallery page number
        model.addAttribute("prevPage", prevPage);

        // Add the return to gallery category
        model.addAttribute("category", PhotoCategory.getEnum(DatabaseAccess.getPhoto(photoID).getCategory()));

        // Get URL of images and add to model
        model.addAttribute("imageURL",
                request.getScheme() + "://" +
                        request.getServerName() + ":" +
                        request.getServerPort() + "/images/");

        // Get URL of Google Maps
        // Add attribute of Google maps to model.  List because the method takes in a List of Photos
        model.addAttribute("gMapPhotoLocationURL",
                GoogleMapsAPI.getMapsURLOfPhotoLocation(DatabaseAccess.getPhoto(photoID)));

        // Get photo entity
        Photos photo = DatabaseAccess.getPhoto(photoID);

        // Get API Key for Google Maps
        model.addAttribute("apiKey", GoogleMapsAPI.getApiKey());

        // Update the votes
        // If user is logged in
        if (LoginController.userLoggedIn(session)) {

            // Add model to dynamically show the user that they can/can't vote
            model.addAttribute("loggedIn", true);

            // Get user information
            Users user = DatabaseAccess.getUser(session.getAttribute("userID").toString());

            // If user is admin, then let them vote regardless
            if (user.getAdmin() == true) {
                if (upvote) {
                    photo.setVotes(photo.getVotes() + 1);
                }
                else {
                    photo.setVotes(photo.getVotes() - 1);
                }
                // Update database
                if (DatabaseAccess.updateVoteOnPhotoAndHistory(user,photo,upvote)) {
                    model.addAttribute("photo", photo);
                    // Add vote status to color the buttons
                    model.addAttribute("voteStat",
                            VoteController.userLastVote(user.getUserId(), photoID));

                    return "photo-detail";
                }
                else {
                    System.out.println("Error!  Could not update admin vote information.");
                    model.addAttribute("message", "Error occurred while storing your vote.  " +
                            "Please contact the administrator.");
                    return "error";
                }
            }
            // You're not an admin, if you get to here
            else {
                // Check to see if user has already voted on this photo
                if (canVote(user,photo,upvote)) {
                    if (upvote) {
                        photo.setVotes(photo.getVotes() + 1);
                    }
                    else {
                        photo.setVotes(photo.getVotes() - 1);
                    }
                    // Update database
                    if (DatabaseAccess.updateVoteOnPhotoAndHistory(user,photo,upvote)) {
                        model.addAttribute("photo", photo);
                        model.addAttribute("voteStat",
                                VoteController.userLastVote(user.getUserId(), photoID));
                        return "photo-detail";
                    }
                    else {
                        System.out.println("Error updating user vote information!");
                        model.addAttribute("message", "Error occurred while storing your vote.  " +
                                "Please contact the administrator.");
                        return "error";
                    }
                }
                else {
                    model.addAttribute("message", "You can only cast one vote per photo!");
                    model.addAttribute("photo", photo);
                    model.addAttribute("voteStat",
                            VoteController.userLastVote(user.getUserId(), photoID));
                    return "photo-detail";
                }
            }
        }
        // If user isn't logged in, then tell them you can't do that
        else {
            model.addAttribute("message", "You must be logged in to do that!");
            model.addAttribute("photo", photo);
            model.addAttribute("voteStat", 0);
            return "photo-detail";
        }

    }


    /**
     * This method determines whether the user can vote on a photo
     * @param user Users entity to determine userID
     * @param photo Photos entity to deterine photoID
     * @param upvote boolean upvote direction to determine whether it can vote
     * @return boolean true if user can vote, false otherwise
     */
    private boolean canVote(Users user, Photos photo, boolean upvote) {

        // Logging
        System.out.println("Checking to see if user can vote on photo");

        // Get the vote history
        List<VoteHistory> voteHistories = DatabaseAccess.getVoteHistory(user, photo, 2);

        // If no history of particular vote, let user vote
        if (voteHistories.isEmpty() || voteHistories == null) {
            return true;
        }

        // If only one history, and upvote is same, then cannot vote
        if (voteHistories.size() == 1 && ((voteHistories.get(0).getUpvote()&0x01) != 0) == upvote) {
            return false;
        }
        // If only one history, and different vote, then can vote
        else if (voteHistories.size() == 1 && ((voteHistories.get(0).getUpvote()&0x01) != 0) != upvote) {
            return true;
        }

        // If two consecutive votes are the same and the next is also the same, then don't let them vote
        if (((voteHistories.get(0).getUpvote()&0x01) != 0) == ((voteHistories.get(1).getUpvote()&0x01) != 0)
                && ((voteHistories.get(0).getUpvote()&0x01) != 0) == upvote) {
            return false;
        }

        // Else, let them vote
        return true;
    }


    /**
     * This method will let you know what the last vote of the user was
     * @param userID String userID to check against
     * @param photoID int photoID to check against
     * @return byte -1 if downvote, +1 if upvote, and 0 if neutral
     */
    public static byte userLastVote(String userID, int photoID) {

        // Get user
        Users user = DatabaseAccess.getUser(userID);

        // Get Photo
        Photos photo = DatabaseAccess.getPhoto(photoID);

        // Get the vote history
        List<VoteHistory> voteHistories = DatabaseAccess.getVoteHistory(user, photo, 2);

        // if no vote history, then return 0 (neutral)
        if (voteHistories.isEmpty() || voteHistories == null) {
            return 0;
        }
        // if one history and upvote, return 1 (upvote) return 1 (downvote)
        else if ((voteHistories.size() == 1) && (voteHistories.get(0).getUpvote() == 1)) {
            if ((voteHistories.get(0).getUpvote() == 1)) {
                return 1;
            } else {
                return -1;
            }
        }
        //If two or more histories and same vote, return last vote else, neutral
        else {
            if (voteHistories.get(0).getUpvote() == voteHistories.get(1).getUpvote()) {
                if ((voteHistories.get(0).getUpvote() == 1)) {
                    return 1;
                } else {
                    return -1;
                }
            }
            else {
                return 0;
            }
        }
    }

}
