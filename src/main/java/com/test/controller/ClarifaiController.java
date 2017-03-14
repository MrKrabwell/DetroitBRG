package com.test.controller;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.ClarifaiURLImage;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Controller
public class ClarifaiController {

    @RequestMapping ("test")
    public void testClarifai(HttpServletRequest request) throws UnknownHostException {
        ClarifaiClient client = new ClarifaiBuilder("zVFg39Fi---sN1IcbsSsG13I7Ldc1Xdb2adszB5A",
          "1KBAt4KfnY6gG094Okne07fpNI0aXn0drfVBAZ5U").buildSync();

        String imageURL = request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort() + "/images/ScavengerHuntPic.jpg";

        final List<ClarifaiOutput<Concept>> predictionResults =
                client.getDefaultModels().generalModel() // You can also do Clarifai.getModelByID("id") to get custom models
                        .predict()
                        .withInputs(
                                ClarifaiInput.forImage(ClarifaiImage.of(new File(imageURL))
                        ))
                        .executeSync() // optionally, pass a ClarifaiClient parameter to override the default client instance with another one
                        .get();

        System.out.println("");


    }
}
