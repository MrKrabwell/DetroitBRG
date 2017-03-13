package com.test.controller;

import com.test.models.UsersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    @RequestMapping(value="showSubmitPhotoPage")
    public ModelAndView showSubmitPhotoPage() {
        return new ModelAndView("submitPhoto");
    }


    @RequestMapping("uploadPhoto")
    public String uploadResources( HttpServletRequest servletRequest,
                                   @ModelAttribute Product product,
                                   Model model) {

        //Get the uploaded files and store them
        List<MultipartFile> files = product.getImages();
        List<String> fileNames = new ArrayList<String>();
        if (null != files && files.size() > 0)
        {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);

                File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"), fileName);
                try
                {
                    multipartFile.transferTo(imageFile);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        // Here, you can save the product details in database

        model.addAttribute("product", product);
        return "test";
    }



    /*
    @RequestMapping(value="uploadPhoto")
    public ModelAndView uploadPhoto(HttpServlet servletRequest,
                                    @ModelAttribute Product product,
                                    Model model) {

            //Get the uploaded files and store them
            List<MultipartFile> files = product.getImages();
            List<String> fileNames = new ArrayList<String>();
            if (null != files && files.size() > 0)
            {
                for (MultipartFile multipartFile : files) {

                    String fileName = multipartFile.getOriginalFilename();
                    fileNames.add(fileName);

                    File imageFile = new File(servletRequest.getServletContext().getRealPath("/image"), fileName);
                    try
                    {
                        multipartFile.transferTo(imageFile);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            // Here, you can save the product details in database

            model.addAttribute("product", product);
            return "viewProductDetail";
        }

        @RequestMapping(value = "/product-input-form")
        public String inputProduct(Model model) {
            model.addAttribute("product", new Product());
            return "productForm";
        }
    }

        return null;
    }
    */

    /**
     * This method is only for testing database access using Hibernate
     * TODO: Delete this
     * @return ModelAndView of test.jsp
     */
    @RequestMapping(value="test")
    public ModelAndView test() {
        /*
        // Set up configuration to use hibernate
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        // Create a new SessionFactory object
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        */

        // Set up configuration (This is for using Hibernate 4.3.11)
        // TODO: SessionFactory has a large footprint, should only have one instance
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

        // Create session with session factory
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
        Session session = sessionFactory.openSession();

        // Begin the transaction
        session.getTransaction().begin();

        // Create a criteria
        Criteria criteria = session.createCriteria(UsersEntity.class);

        List<UsersEntity> list = criteria.list();

        // Close the session
        session.close();

        return new ModelAndView("test","list",list);
    }

}
