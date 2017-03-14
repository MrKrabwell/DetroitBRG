package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * This controller class is for showing pages
 */

@Controller
public class HomeController {


    /**
     * This method will show the index page
     * @return ModelAndView of index page todo: maybe we don't need a model here...
     */
    @RequestMapping(value="/")
    public ModelAndView showIndexPage()
    {
        return new ModelAndView("index", "message", "Go Team 3!");
    }


    /**
     * This method will show the image submission page
     * @return ModelAndView of submission page
     */
    @RequestMapping(value="showSubmitPhotoPage")
    public ModelAndView showSubmitPhotoPage() {
        return new ModelAndView("submitPhoto");
    }



    /**
     * This method is only for testing database access using Hibernate
     * TODO: Delete this
     * @return ModelAndView
     */
//    @RequestMapping(value="test")
//    public ModelAndView test() {
//        /*
//        // Set up configuration to use hibernate
//        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
//
//        // Create a new SessionFactory object
//        SessionFactory sessionFactory = cfg.buildSessionFactory();
//        */
//
//        // Set up configuration (This is for using Hibernate 4.3.11)
//        // TODO: SessionFactory has a large footprint, should only have one instance
//        Configuration configuration = new Configuration();
//        configuration.configure("hibernate.cfg.xml");
//        StandardServiceRegistryBuilder ssrb =
//                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//
//        // Create session with session factory
//        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
//        Session session = sessionFactory.openSession();
//
//        // Begin the transaction
//        session.getTransaction().begin();
//
//        // Create a criteria
//        Criteria criteria = session.createCriteria(UsersEntity.class);
//
//        List<UsersEntity> list = criteria.list();
//
//        // Close the session
//        session.close();
//
//        return new ModelAndView("test","list",list);
//    }


}
