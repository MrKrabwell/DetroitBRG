package com.test.dataaccess;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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
     * This method will save the entity information to the database
     * @param objectEntity
     * @return
     */
    public static boolean insertToDatabase(Object objectEntity) {
        // Logging
        System.out.println("Storing " + objectEntity.toString() + " to database.");

        try {
            // Create a new session
            Session session = DatabaseAccess.sessionFactory.openSession();

            // Begin the transaction
            Transaction tr = session.beginTransaction();

            // Insert into the database
            session.save(objectEntity);
            tr.commit();

            // Close the session
            session.close();

            System.out.println("Successfully stored " + objectEntity + " to databased");
            return true;
        }
        catch (Exception e) {
            System.out.println("Error storing to database!!");
            e.printStackTrace();
            return false;
        }
    }

}
