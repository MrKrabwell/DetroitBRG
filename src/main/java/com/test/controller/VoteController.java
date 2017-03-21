package com.test.controller;

import com.test.dataaccess.DatabaseAccess;
import com.test.entity.Photos;
import com.test.entity.Users;
import com.test.entity.VoteHistory;
import com.test.external.GoogleMapsAPI;
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
     * @param photoID
     * @param upvote
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value="vote")
    public String votePhoto(@RequestParam("photoId") int photoID,
                            @RequestParam("type") boolean upvote,
                            HttpSession session,
                            HttpServletRequest request,
                            Model model) {

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
                if (DatabaseAccess.updatePhoto(photo) && DatabaseAccess.updateVoteHistory(user,photo,upvote)) {
                    model.addAttribute("photo", photo);
                    return "photo-detail";
                }
                else {
                    System.out.println("Error while voting.");
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
                    if (DatabaseAccess.updatePhoto(photo) && DatabaseAccess.updateVoteHistory(user,photo,upvote)) {
                        model.addAttribute("photo", photo);
                        return "photo-detail";
                    }
                    else {
                        System.out.println("Error updating vote information!");
                        return "error";
                    }
                }
                else {
                    model.addAttribute("message", "You can only cast one vote per photo!");
                    model.addAttribute("photo", photo);
                    return "photo-detail";
                }
            }
        }
        // If user isn't logged in, then tell them you can't do that
        else {
            model.addAttribute("message", "You must be logged in to do that!");
            model.addAttribute("photo", photo);
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
        List<VoteHistory> voteHistories = DatabaseAccess.getVoteHistory(user, photo);

        // If no history of particular vote, let user vote
        if (voteHistories == null || voteHistories.isEmpty()) {
            return true;
        }

        // If only one history, and upvote is same, then cannot vote
        if (voteHistories.size() == 1 && ((voteHistories.get(0).getUpvote()&0x01) != 0) == upvote) {
            return false;
        }

        // If two consecutive votes are the same and the next is also the same, then don't let them vote
        if (((voteHistories.get(0).getUpvote()&0x01) != 0) == ((voteHistories.get(1).getUpvote()&0x01) != 0)
                && ((voteHistories.get(0).getUpvote()&0x01) != 0) == upvote) {
            return false;
        }

        // Else, let them vote
        return true;
    }

}
