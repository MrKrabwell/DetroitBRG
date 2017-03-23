package com.test.dataaccess;

import com.test.entity.PhotoCategory;
import com.test.entity.Photos;
import com.test.entity.Users;
import com.test.entity.VoteHistory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.SourceType;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yosuk on 3/11/2017.
 */
public class DatabaseAccess {

    // Class fields
    // Instantiate a single SessionFactory since it takes up alot of footprint
    // Set up configuration (This is for using Hibernate 4.3.11)
    // SessionFactory has a large footprint, should only have one instance
    private static Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
    private static StandardServiceRegistryBuilder ssrb
            = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    private static SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());


    /**
     * This method will save the Photos entity information to the database
     * @param photo Photos entity to store to database
     * @return boolean true if executed successfully, false otherwise
     */
    public static boolean insertPhotoToDatabase(Photos photo) {
        // Logging
        System.out.println("Storing " + photo.toString() + " to database.");

        try {
            // Create a new session and start transaction
            Session session = sessionFactory.openSession();

            // Begin the transaction
            Transaction tr = session.beginTransaction();

            // Insert into the database
            session.save(photo);
            tr.commit();

            // Close the session
            session.close();

            System.out.println("Successfully stored " + photo.toString() + " to database");
            return true;
        }
        catch (Exception e) {
            System.out.println("Error storing " + photo.toString() + " to database!!");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * This method returns the next primary key value for the Photos entity
     * @return int of next value to use for primary key
     */
    public static int getNextPhotoPrimaryKey() {

        try {

            // Create a new session
            Session session = sessionFactory.openSession();

            // Create criteria to get the last photo primary key, ###Be careful with query case!!!!###
            Criteria criteria = session.createCriteria(Photos.class);
            criteria.addOrder(Order.desc("photoId"));
            criteria.setMaxResults(1);
            List<Photos> maxIDPhoto = (List<Photos>)criteria.list();

            // Close the session
            session.close();

            // +1 because we want the next ID.
            return maxIDPhoto.get(0).getPhotoId() + 1;
        }
        catch (IndexOutOfBoundsException e) {
            // If it gets here, then the table is likely empty
            System.out.println("Photos has zero entries");
            // TODO: change the auto_increment back to 0.
            e.printStackTrace();
            return 1;
        }
        catch (Exception e) {
            System.out.println("Error getting last Photos primary key!");
            e.printStackTrace();
            return -1; // -1 for error
        }
    }


    /**
     * This method returns all photos uploaded
     * TODO: Do we need this method???
     * @return List<Photos> of all photos
     */
    public static List<Photos> getAllPhotos() {

        // Logging
        System.out.println("Getting all Photos from database!");

        try {
            // Create a new session
            Session session = sessionFactory.openSession();

            // Create criteria to get the last photo primary key, ###Be careful with query case!!!!###
            Criteria criteria = session.createCriteria(Photos.class);
            List<Photos> allPhotosList = (List<Photos>) criteria.list();

            // Close the session
            session.close();

            // Successfully got photos
            System.out.println("Successfully got all photos");
            return allPhotosList;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting all Photos from database!");
            return null;
        }
    }


    // TODO: Create a overloaded method where if no category is passed, it is all categories
    /**
     * This method will get the list of Photos entity of the provided index, sorted by number of votes.
     * @param startIdx int start index of list sorted by top votes
     * @param stopIdx int stop index of list sorted by top votes
     * @return List<Photos> of top results, null if nothing
     */
    public static List<Photos> getTopPhotos(int startIdx, int stopIdx, PhotoCategory category) {
        // Logging
        System.out.println("Getting top Photos (index " + startIdx + " to " + stopIdx + ") from database!");

        // Make this so that it can still run with the index parameters jumbled i.e. swap
        if (startIdx > stopIdx) {
            System.out.println("Warning: getTopPhotos start index is larger and stop index.  Fixing...");
            startIdx = startIdx + stopIdx;
            stopIdx= startIdx - stopIdx;
            startIdx = startIdx - stopIdx;
        }

        // TODO: Make sure there are enough items in the list with category

        try {
            // Create a new session
            Session session = sessionFactory.openSession();

            // Create criteria to get the top photos of index, ###Be careful with query case!!!!###
            Criteria criteria = session.createCriteria(Photos.class);
            criteria.addOrder( Order.desc("votes") )
                    .add(Restrictions.eq("category",category.toString()))
                    .setFirstResult(startIdx)
                    .setMaxResults(stopIdx - startIdx + 1); // + 1 to make sure to include indexes
            List<Photos> topPhotosList = (List<Photos>)criteria.list();

            // Close the session
            session.close();

            // Make sure we got something back
            if (topPhotosList == null) {
                System.out.println("Warning: No row entries in " + category.toString() + "!");
                return null;
            }

            // Successfully got photos
            System.out.println("Successfully got top Photos (index " + startIdx + " to " + stopIdx + ") " +
                    "for " + category.toString() + "!");
            return topPhotosList;
        }
        catch (Exception e) {
            System.out.println("Error getting top Photos (index " + startIdx + " to " + stopIdx + ") " +
                    "for " + category.toString() +"!");
            return null;
        }
    }


    /**
     * This returns the number of entries in entity
     * @param entity Class object of entity you want to get
     * @return int of number of rows
     */
    public static int getNumberOfEntries(Class entity) {

        //Logging
        System.out.println("DatabaseAccess.getNumberOfEntries(" + entity.toString() + ")");

        try {
            // Create a new session
            Session session = sessionFactory.openSession();

            // Create criteria to get the top photos of index, ###Be careful with query case!!!!###
            Criteria criteria = session.createCriteria(entity);
            Long numRows = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();

            // Close the session
            session.close();

            // Successfully got photos
            System.out.println("Successfully got number of rows for " + entity.toString() + "!");
            return numRows.intValue();
        }
        catch (Exception e) {
            System.out.println("Error getting number of rows for " + entity.toString() + "!");
            return -1;
        }

    }


    /**
     * This is an overloaded method to get the number of entries in the Photos table with a specific category
     * @param category PhotosCategory of photos
     * @return int number of entries in the Photos table with cateory
     */
    public static int getNumberOfEntries(PhotoCategory category) {
        //Logging
        System.out.println("DatabaseAccess.getNumberOfEntries(" + category.toString() + ")");

        try {
            // Create a new session
            Session session = sessionFactory.openSession();

            // Create criteria to get the top photos of index, ###Be careful with query case!!!!###
            // return is a long because uniqueResults() returns a long
            Criteria criteria = session.createCriteria(Photos.class);
            Long numRows = (Long)criteria.add(Restrictions.eq("category",category.toString()))
                    .setProjection(Projections.rowCount())
                    .uniqueResult();  // TODO: CAUSING PROBLEM HERE!!!!

            // Close the session
            session.close();

            // Successfully got photos
            System.out.println("Successfully got number of rows for " + category.toString() + "!");
            return numRows.intValue();
        }
        catch (Exception e) {
            System.out.println("Error getting number of rows for " + category.toString() + "!");
            return -1;
        }
    }


    /**
     * This method return a single Photo entity with provided photoID primary key
     * @param photoID int of photoID primary key
     * @return Photos entity
     */
    public static Photos getPhoto(int photoID) {

        //Logging
        System.out.println("DatabaseAccess.getPhoto(" + photoID + ")");

        try {
            // Create a new session
            Session session = sessionFactory.openSession();

            // Get a unique photo with ID
            Photos photo = (Photos)session.get(Photos.class, photoID);

            // Close the session
            session.close();

            // Successfully got photos
            System.out.println("Successfully got Photo with photoID = " + photoID + "!");
            return photo;
        }
        catch (Exception e) {
            System.out.println("Error getting Photo with photoID = " + photoID + "!");
            return null;
        }
    }


    /**
     * This method will get a single Users entity provided the userID
     * @param userID String userID of the Users entity you want to get
     * @return Users single Users entity
     */
    public static Users getUser(String userID) {

        //Logging
        System.out.println("DatabaseAccess.getUser(" + userID + ")");

        try {
            // Create a new session
            Session session = sessionFactory.openSession();

            // Get a unique user with ID
            Users user = (Users)session.get(Users.class, userID);

            // Close the session
            session.close();

            // Successfully got user
            System.out.println("Successfully got User with userID = " + userID + "!");
            return user;
        }
        catch (Exception e) {
            System.out.println("Error getting Photo with photoID = " + userID + "!");
            return null;
        }
    }


    /**
     * This method determines if an user exists in the database
     * @param userID String userID of the user you're trying to determine if they exist or not
     * @return boolean true if user exists, false otherwise
     */
    public static boolean userExists(String userID) {

        //Logging
        System.out.println("DatabaseAccess.userExists(" + userID + ")");

        if (getUser(userID) == null) {
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * This method will register a new user into the
     * @param user Users entity to register
     * @return boolean true if registered successfully, false otherwise
     */
    public static boolean registerNewUser(Users user) {
        // Logging
        System.out.println("Registering new user " + user.getUserId() + " to database.");

        try {
            // Create a new session and start transaction
            Session session = sessionFactory.openSession();

            // Begin the transaction
            Transaction tr = session.beginTransaction();

            // Insert into the database
            session.save(user);
            tr.commit();

            // Close the session
            session.close();

            System.out.println("Successfully registering new user " + user.getUserId() + " to database");
            return true;
        }
        catch (Exception e) {
            System.out.println("Error registering new user " + user.getUserId() + " to database!!");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * This method will update the update the information about the photo TODO: Do we need?
     * @param photo Photos of the photo to update
     * @return boolean true if successful, false otherwise.
     */
    public static boolean updatePhoto(Photos photo) {

        // Logging
        System.out.println("DatabaseAccess.updatePhoto(" + photo.toString() + ")");

        try {
            // Create a new session and start transaction
            Session session = sessionFactory.openSession();

            // Begin the transaction
            Transaction tr = session.beginTransaction();

            // Insert into the database
            session.update(photo);
            tr.commit();

            // Close the session
            session.close();

            System.out.println("Successfully updated " + photo.toString() + " to database");
            return true;
        } catch (Exception e) {
            System.out.println("Error updating " + photo.toString() + " to database");
            return false;
        }
    }


    /**
     * This method return the Vote History entity with the particular userID and photoID,
     * in descending order chronologically
     * @param user Users entity to get userID
     * @param photo Photos entity to get photoID
     * @return List of VoteHisotry objects with the matching userID and photoID
     */
    public static List<VoteHistory> getVoteHistory(Users user, Photos photo, int numEntries) {

        try {

            // Logging
            System.out.println("DatabaseAccess.getVoteHistory(" + user.toString() + ", " +
                    photo.toString() + ")");

            // Create a new session
            Session session = sessionFactory.openSession();

            // Create criteria to get the last photo primary key, ###Be careful with query case!!!!###
            Criteria criteria = session.createCriteria(VoteHistory.class);
            criteria.add(Restrictions.eq("photoId", photo.getPhotoId()));
            criteria.add(Restrictions.eq("userId", user.getUserId()));
            criteria.addOrder(Order.desc("historyId"));
            criteria.setMaxResults(numEntries);
            List<VoteHistory> voteHistories = criteria.list();

            // Close the session
            session.close();

            System.out.println("Successfully received VoteHistory");
            return voteHistories;
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Error getting vote history");
            e.printStackTrace();
            return null;
        }
        catch (NullPointerException e) {
            System.out.println("Error!  This could! happen when photo.toString is null??");
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            System.out.println("Error getting vote history!");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * This method saves a new VoteHistory to the database TODO: Do we need?
     * @param user Users entity to create a new VoteHistory object to save
     * @param photo Photos entity to create a new VoteHistory object to save
     * @param upvote boolean, true if upvote, false if downvote
     * @return boolean true if update was successful, false otherwise
     */
    public static boolean updateVoteHistory(Users user, Photos photo, boolean upvote) {
        // Logging
        System.out.println("DatabaseAccess.updateVoteHistory(user,photo,upvote)");

        try {
            // Create a new session and start transaction
            Session session = sessionFactory.openSession();

            // Begin the transaction
            Transaction tr = session.beginTransaction();

            // Insert into the database
            VoteHistory history = new VoteHistory();
            history.setPhotoId(photo.getPhotoId());
            history.setUserId(user.getUserId());
            history.setTotalVotes(photo.getVotes());
            history.setUpvote(upvote ? (byte)1 : (byte)0);
            history.setTimestamp(new Timestamp(System.currentTimeMillis()));
            session.save(history);
            tr.commit();

            // Close the session
            session.close();

            System.out.println("Successfully updated VoteHistory to database");
            return true;
        } catch (Exception e) {
            System.out.println("Error updating VoteHistory to database");
            return false;
        }
    }

    /**
     * This method will update the photo's vote count and also insert a new vote history,
     * and fail if either fails
     * @param user Users user to get the userID
     * @param photo Photos to update the photo
     * @param upvote boolean, direction of vote, true if upvote, false if downvote
     * @return true if successful, false otherwise
     */
    public static boolean updateVoteOnPhotoAndHistory(Users user, Photos photo, boolean upvote){
        // Logging
        System.out.println("DatabaseAccess.updateVoteOnPhotoAndHistory(user,photo,upvote)");

        try {
            // Create a new session and start transaction
            Session session = sessionFactory.openSession();

            // Begin the transaction
            Transaction tr = session.beginTransaction();

            // Update the photo the database
            session.update(photo);

            // Insert into the database
            VoteHistory history = new VoteHistory();
            history.setPhotoId(photo.getPhotoId());
            history.setUserId(user.getUserId());
            history.setTotalVotes(photo.getVotes());
            history.setUpvote(upvote ? (byte)1 : (byte)0);
            history.setTimestamp(new Timestamp(System.currentTimeMillis()));
            session.save(history);

            // Finally commit
            tr.commit();

            // Close the session
            session.close();

            // Success
            System.out.println("Successfully updated Photos and VoteHistory in database");
            return true;
        } catch (Exception e) {
            System.out.println("Error updating Photos and VoteHistory to database");
            return false;
        }
    }
}
