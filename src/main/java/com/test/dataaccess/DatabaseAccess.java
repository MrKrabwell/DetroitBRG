package com.test.dataaccess;

import com.test.entity.Photos;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;

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
    public static SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());



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
            return -1;
        }
    }

}
