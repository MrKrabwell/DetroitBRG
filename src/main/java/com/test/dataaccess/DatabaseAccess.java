package com.test.dataaccess;

import com.test.entity.PhotoCategory;
import com.test.entity.Photos;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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

            System.out.println("Successfully stored " + photo.toString() + " to databased");
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

        // Make sure there are enough items in the list with cateory
        if ((stopIdx - startIdx + 1) < getNumberOfEntries(category)) {
            System.out.println("Warning: not enough entries in " + category.toString() + ", changing stop index.");
            stopIdx = (startIdx + getNumberOfEntries(category) - 1); // -1 to account for index vs number of rows
        }

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
            int numRows = (Integer)criteria.setProjection(Projections.rowCount()).uniqueResult();

            // Close the session
            session.close();

            // Successfully got photos
            System.out.println("Successfully got number of rows for " + entity.toString() + "!");
            return numRows;
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
            Criteria criteria = session.createCriteria(Photos.class);
            int numRows = (Integer)criteria.add(Restrictions.eq("category",category.toString()))
                    .setProjection(Projections.rowCount())
                    .uniqueResult();

            // Close the session
            session.close();

            // Successfully got photos
            System.out.println("Successfully got number of rows for " + category.toString() + "!");
            return numRows;
        }
        catch (Exception e) {
            System.out.println("Error getting number of rows for " + category.toString() + "!");
            return -1;
        }
    }

}
