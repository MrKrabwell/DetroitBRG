package com.test.controller;

import com.test.models.UsersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
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


    private static final String UPLOAD_DIRECTORY ="/image";

    @RequestMapping("uploadPhoto")
    public ModelAndView uploadResources( @RequestParam CommonsMultipartFile file, HttpSession session,
                                   Model model ) throws IOException {

        ServletContext context = session.getServletContext();
        String path = context.getRealPath(UPLOAD_DIRECTORY);
        String filename = file.getOriginalFilename();

        System.out.println(path+" "+filename);

        byte[] bytes = file.getBytes();
        BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(
                new File(path + File.separator + filename)));
        stream.write(bytes);
        stream.flush();
        stream.close();

        return new ModelAndView("uploadform","filesuccess","File successfully saved!");
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
