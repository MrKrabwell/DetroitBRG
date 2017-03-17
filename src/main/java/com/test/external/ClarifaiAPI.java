package com.test.external;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.ClarifaiPaginatedRequest;
import clarifai2.api.request.ClarifaiRequest;
import clarifai2.api.request.input.AddInputsRequest;
import clarifai2.api.request.model.CreateModelRequest;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.ClarifaiURLImage;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Controller

public class ClarifaiAPI {

    // Class Fields
    final static double DETERMINE_THRESHOLD = 0.65;
    private static final String CLARIFAI_APP_ID = "zVFg39Fi---sN1IcbsSsG13I7Ldc1Xdb2adszB5A"; // Coleman's APP ID for Clarifai
    private static final String CLARIFAI_API_KEY = "1KBAt4KfnY6gG094Okne07fpNI0aXn0drfVBAZ5U"; // Coleman's API Key for Clarifai

    static {
        ClarifaiClient initialClient = new ClarifaiBuilder(CLARIFAI_APP_ID, CLARIFAI_API_KEY).buildSync();
        client = initialClient;


        /*******************************
        *-New Detroit Model and train-*
        *******************************/
        // Refresh the Model
        initialClient.deleteModel("Beauty").executeSync();

        //Create Array of urls and pass into for loop for training model.
        String[] trainingBeautyPhotoURL = {
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.gannett-cdn.com%2F-mm-%2Fcba46142da8fafe94960c089b9b502fb8d63f761%2Fc%3D136-0-2264-1600%26r%3Dx513%26c%3D680x510%2Flocal%2F-%2Fmedia%2F2017%2F01%2F23%2FDetroitFreePress%2FDetroitFreePress%2F636207780765061365-Old-Freep-Building-EC024.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fmedia.timeout.com%2Fimages%2F101657505%2Fimage.jpg&f=1",
                "https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs3.favim.com%2Forig%2F47%2Farchitecture-baroque-barrock-beautiful-building-Favim.com-433866.jpg",
                "https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs2.favim.com%2Forig%2F34%2Farchitecture-awesome-beautiful-building-buildings-Favim.com-272240.jpg",
                "https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs3.favim.com%2Forig%2F47%2Farchitecture-beautiful-beauty-buildings-city-Favim.com-438159.jpg",
                "https://duckduckgo.com/?q=detroit+river+walk&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fwww.portraitsbyrod.com%2Fwp-content%2Fuploads%2F2014%2F01%2FDetroit-Riverwalk-Final.jpg",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F736x%2Ff4%2F99%2F4d%2Ff4994d5acf68818ca5461dfe3d553d4a.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.detroityes.com%2Fwebisodes%2F2005%2F06-DetroitRises%2F01-Rise-006.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-qlPnvCg00h0%2FT-UuQDTp3lI%2FAAAAAAAABco%2FCrAeR7EnQqc%2Fs1600%2FPicture%2B002.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.freshwatercleveland.com%2Fgalleries%2FFeatures%2F2014%2FSeptember_2014%2FIssue_179%2Fmost_anticipated%2Fpublic_square_rendering.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fcbsdetroit.files.wordpress.com%2F2015%2F03%2Ffisher-building-e1434814315291.jpg%3Fw%3D1024&f=1",
                };

        String[] trainingCategoryOne = {
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.gannett-cdn.com%2F-mm-%2Fcba46142da8fafe94960c089b9b502fb8d63f761%2Fc%3D136-0-2264-1600%26r%3Dx513%26c%3D680x510%2Flocal%2F-%2Fmedia%2F2017%2F01%2F23%2FDetroitFreePress%2FDetroitFreePress%2F636207780765061365-Old-Freep-Building-EC024.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fmedia.timeout.com%2Fimages%2F101657505%2Fimage.jpg&f=1",
                "https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs3.favim.com%2Forig%2F47%2Farchitecture-baroque-barrock-beautiful-building-Favim.com-433866.jpg"
        };

        String[] trainingCategoryTwo = {
                "https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs2.favim.com%2Forig%2F34%2Farchitecture-awesome-beautiful-building-buildings-Favim.com-272240.jpg",
                "https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs3.favim.com%2Forig%2F47%2Farchitecture-beautiful-beauty-buildings-city-Favim.com-438159.jpg",
                "https://duckduckgo.com/?q=detroit+river+walk&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fwww.portraitsbyrod.com%2Fwp-content%2Fuploads%2F2014%2F01%2FDetroit-Riverwalk-Final.jpg",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F736x%2Ff4%2F99%2F4d%2Ff4994d5acf68818ca5461dfe3d553d4a.jpg&f=1"
        };

        String[] trainingCategoryThree = {
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.detroityes.com%2Fwebisodes%2F2005%2F06-DetroitRises%2F01-Rise-006.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-qlPnvCg00h0%2FT-UuQDTp3lI%2FAAAAAAAABco%2FCrAeR7EnQqc%2Fs1600%2FPicture%2B002.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.freshwatercleveland.com%2Fgalleries%2FFeatures%2F2014%2FSeptember_2014%2FIssue_179%2Fmost_anticipated%2Fpublic_square_rendering.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fcbsdetroit.files.wordpress.com%2F2015%2F03%2Ffisher-building-e1434814315291.jpg%3Fw%3D1024&f=1"
        };


        AddInputsRequest air = initialClient.addInputs();
        // Adding concepts for category one
        for (int i = 0; i < trainingCategoryOne.length; i++) {
            air
                    .plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(trainingCategoryOne[i]))
                                    .withConcepts(Concept.forID("trainingOne")) // ID
                    );
        }

        /*
        ClarifaiPaginatedRequest.Builder thingy =   initialClient.getModelVersions("{model_id}");
        ClarifaiRequest thingy2 = thingy.getPage(1);
        thingy2.executeSync();
        */

        // Adding concepts for category two
        for (int i = 0; i < trainingCategoryTwo.length; i++) {
            air.plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(trainingCategoryTwo[i]))
                                    .withConcepts(Concept.forID("trainingTwo")) // ID
                    );
        }


        // Adding concepts for category three
        for (int i = 0; i < trainingCategoryThree.length; i++) {
            air.plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(trainingCategoryThree[i]))
                                    .withConcepts(Concept.forID("trainingThree")) // ID
                    );
        }

        air.executeSync();

        // Creating Model
        // Model, ID
        CreateModelRequest cmr = initialClient.createModel("Beauty");

        cmr.withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("trainingOne")
                ));

        cmr.withOutputInfo(ConceptOutputInfo.forConcepts(
                Concept.forID("trainingTwo")
        ));

        cmr.withOutputInfo(ConceptOutputInfo.forConcepts(
                Concept.forID("trainingThree")
        ));


        cmr.executeSync();


        /*
        // Model, ID
        initialClient.createModel("Beauty")
                .withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("trainingTwo")
                ))
                .executeSync();


        // Model, ID
        initialClient.createModel("Beauty")
                .withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("trainingThree")
                ))
                .executeSync();
        */

        //Train Model
        // Model
        initialClient.trainModel("Beauty").executeSync();



        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++





/*******************************
 *-Old Detroit Model and Train-*
 *******************************/
        //Refresh Model
        initialClient.deleteModel("Old");

        //Build Array of URLs and pass into for loop for training model.
        String[] trainingOldDetroitURL = {
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F2.bp.blogspot.com%2F-JBu3f7__OHg%2FT_M2Dx3TUuI%2FAAAAAAAACiE%2FhycGn-javT4%2Fw1200-h630-p-k-nu%2Fs_w15_RTR2KWTW.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-cxZtOhkisnI%2FTX79A2kr15I%2FAAAAAAAAAqM%2FAIiLRQr323k%2Fs1600%2Freliques_05.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.UTJKS_hMh91X-cbXfSvSHwEsDe%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.angryboar.com%2Fwp-content%2Fuploads%2F2013%2F11%2Fabandoned_buildings_in_Detroit_14-600x600.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F-bJOuqLSQu00%2FUeX29fvdKwI%2FAAAAAAAAQCI%2FJBwmN7dY7-A%2Fs1600%2FDETFarwellBuildingAtrium.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fimg.weburbanist.com%2Fwp-content%2Fuploads%2F2012%2F06%2Fdetroit-abandoned-buildings.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fimages.fineartamerica.com%2Fimages-medium%2Fabandoned-building-in-detroit-joe-gee.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.slate.com%2Fcontent%2Fdam%2Fslate%2Fblogs%2Fbrowbeat%2F2012%2F01%2F25%2Fdetropia_documentary_paints_a_bleak_picture_of_former_city_of_the_future%2F53383565.jpg.CROP.rectangle3-large.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.angryboar.com%2Fwp-content%2Fuploads%2F2013%2F11%2Fabandoned_buildings_in_Detroit_7-600x600.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.angryboar.com%2Fwp-content%2Fuploads%2F2013%2F11%2Fabandoned_buildings_in_Detroit_3-600x600.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fphotos2.meetupstatic.com%2Fphotos%2Fevent%2Fc%2Fa%2Fb%2F4%2Fhighres_121911892.jpeg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fstatic.guim.co.uk%2Fsys-images%2FGuardian%2FPix%2Fpictures%2F2012%2F2%2F15%2F1329316146544%2FPhotograph-of-dilapidated-007.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fplanphilly.com%2Fuploads%2Fmedia_items%2Fhttp-planphilly-com-eyesonthestreet-wp-content-uploads-2012-01-stagefright_edisonhs-jpg.639.384.s.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F736x%2F45%2F5c%2F67%2F455c677ad2133a42d96e05d6a987c962.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.5IGgabHvACE82MxsIVFm2wEsDh%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fstreetsdept.files.wordpress.com%2F2013%2F06%2Fortliebs29.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fstreetsdept.files.wordpress.com%2F2013%2F04%2Fboner18.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm2.static.flickr.com%2F1411%2F584801753_4776209507.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fl7.alamy.com%2Fzooms%2Fdf43d44b08884b6087d00b273f72150d%2Fnewly-opened-detroit-interchange-rosa-parks-transit-center-for-detroit-c2m3n5.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ftenthdimension.files.wordpress.com%2F2009%2F09%2Fseedetroitmcs111.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fstanleybeamansears.com%2Fwp-content%2Fuploads%2F2013%2F08%2FDetroit-6-1024x585.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.-W9a3ypdlwL__h4-dJZ9MQEsDu%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fad009cdnb.archdaily.net%2Fwp-content%2Fuploads%2F2013%2F08%2F52055e07e8e44e3e3000002c_it-s-time-to-envision-a-better-built-detroit-are-architects-ready-_shutterstock_94905997.jpg&f=1",};
        for (int i = 0; i < trainingOldDetroitURL.length; i++) {
            initialClient.addInputs()
                    .plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(trainingOldDetroitURL[i]))
                                    .withConcepts(Concept.forID("oldDetroit")) // ID
                    )
                    .executeSync();
        }
        //False Values
        //Create Array of URLs pass to for loop
        String[] trainingOldDetroitURLNegative = {
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm9.staticflickr.com%2F8170%2F8009382140_e9e40fafb9_z.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fi.dailymail.co.uk%2Fi%2Fpix%2F2013%2F07%2F24%2Farticle-0-1AF54269000005DC-401_964x639.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fmediad.publicbroadcasting.net%2Fp%2Fmichigan%2Ffiles%2Fstyles%2Frelated%2Fpublic%2F201208%2Fdelray-01.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm3.staticflickr.com%2F2429%2F3629881203_a9ee1049bb_z.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.japantimes.co.jp%2Fwp-content%2Fuploads%2F2013%2F01%2Fnn20111130f3b.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F2.bp.blogspot.com%2F-4DreqdF8-Pw%2FVVqG56IBX_I%2FAAAAAAAAKmc%2Fu9ouSkD5Sdw%2Fs1600%2FIMG_1941.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F200x150%2Ffd%2F58%2F72%2Ffd587200d60d32d8d98a98159e2d68dd.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fthepigyear.files.wordpress.com%2F2013%2F01%2Fdsc_0140.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fonthecommons.org%2Fsites%2Fdefault%2Ffiles%2Fimagecache%2F300w%2FBlightBuster.JPG&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Factnowdetroit.files.wordpress.com%2F2012%2F04%2Fimg_4331.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fvsphere-land.com%2Fwp-content%2Fuploads%2Fruined-building-hurricane-katrina.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.ooqpe9WWLRR2q8keTYpdVQEsDt%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm8.staticflickr.com%2F7275%2F7746231932_29399f2755_z.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fpunditfromanotherplanet.files.wordpress.com%2F2013%2F12%2Fmichigan-decay-flickr.jpg%3Fw%3D590&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fc2.staticflickr.com%2F8%2F7270%2F7745946140_309a26a83f_b.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffireplacechats.files.wordpress.com%2F2013%2F06%2Fdetroit-house-and-red-parked-car-roof-final-artcat.jpg%3Fw%3D560&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Factnowdetroit.files.wordpress.com%2F2012%2F04%2Fimg_4338.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F-Wkbv1tunNWg%2FUv_nBR8RjWI%2FAAAAAAAABGg%2FnsRLYe9mirg%2Fs1600%2Fusa-detroit.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm4.static.flickr.com%2F3636%2F3387120555_cdc7feb259_b.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffellowshipofminds.files.wordpress.com%2F2011%2F05%2Fdetroit.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fratedsworld.files.wordpress.com%2F2010%2F06%2Flouis-vuitton-house.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fi.dailymail.co.uk%2Fi%2Fpix%2F2012%2F08%2F06%2Farticle-2184246-1467B95A000005DC-982_964x569.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fthumb7.shutterstock.com%2Fdisplay_pic_with_logo%2F73694%2F73694%2C1288704180%2C1%2Fstock-photo-black-and-white-abandoned-house-against-colorful-blue-cloudy-sky-64234009.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fmedia.gettyimages.com%2Fvideos%2Fexterior-shots-abandoned-run-down-houses-in-detroit-suburbs-with-video-id186770513%3Fs%3D640x640&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.GUbNkOaM5eFSlZTE4hbImQEsDH%26pid%3D15.1&f=1"};

        // Add input images to concepts
        for (int i = 0; i < trainingOldDetroitURLNegative.length; i++) {
            initialClient.addInputs()
                    .plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(trainingOldDetroitURLNegative[i]))
                                    .withConcepts(Concept.forID("oldDetroit").withValue(false))  // ID
                    )
                    .executeSync();
        }


        //Create Model
        // Model, ID
        initialClient.createModel("oldDetroit")
                .withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("oldDetroit")
                ))
                .executeSync();


        //Train Model oldDetroit Model with postive and false values
        //Model
        initialClient.trainModel("oldDetroit").executeSync();


/****************************
 *Street Art Model and Train*
 ****************************/
//Refresh Model
        initialClient.deleteModel("StreetArt");

        //Build Array of URLs and pass into for loop for training model.
        String[] trainingStreetArtURL = {
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fs1.ibtimes.com%2Fsites%2Fwww.ibtimes.com%2Ffiles%2Fstyles%2Flg%2Fpublic%2F2014%2F01%2F10%2Fdetroit_0.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fblog.thedetroithub.com%2Fwp-content%2Fuploads%2F2012%2F10%2Fbeforeafter.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-ev2fEy7Ejc4%2FT6uhvLxOA4I%2FAAAAAAAAADs%2F_eRc1B-bwOc%2Fs1600%2Fdavid_walker_street_art_3_london1.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Faddcolortoyourlife.com%2Fwp-content%2Fuploads%2F2016%2F06%2Fperth-australia.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fgazduncan.files.wordpress.com%2F2012%2F09%2Ftumblr_ma8smyo3p51r6wmavo1_1280.jpg&f=1",
                "https://duckduckgo.com/?q=street+art&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fartist.com%2Fart-recognition-and-education%2Fwp-content%2Fthemes%2Fartist-blog%2Fmedia-files%2F2016%2F03%2Fstreet-art-4.jpg",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Filovedetroitmichigan.com%2Fwp-content%2Fuploads%2F2011%2F10%2F2340-Russell-St-Alley-Graffiti-2.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Filovedetroitmichigan.com%2Fwp-content%2Fuploads%2F2011%2F10%2F2340-Russell-St-Alley-Graffiti-5.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftwistedpreservation.files.wordpress.com%2F2014%2F09%2Fimg_7741.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F736x%2F59%2F5c%2F44%2F595c442137fa8dc16bb529697b2d1773.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F37.media.tumblr.com%2Ftumblr_m9imcfxley1qaskyao1_500.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.graffitiactionhero.org%2Fuploads%2F9%2F5%2F1%2F3%2F9513440%2F4101507_orig.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.R2Ti_nYirnlDGIa1bbF0aQEsDc%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.expats.cz%2Fresources%2Fstreet-art-part-II-tags4.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.vectorstock.com%2Fi%2Fcomposite%2F52%2C07%2Fgraffiti-tags-street-art-background-vector-1255207.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.psst.ph%2Fwp-content%2Fuploads%2F2016%2F07%2F3035115-slide-graffiti-park-002.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.artofthestate.co.uk%2Fphotos%2Fleake_street_walls.jpg&f=1",
                "https://duckduckgo.com/?q=street+tag&t=ffab&iax=1&ia=images&iai=http%3A%2F%2Fwww.stickersmania.fr%2F1220-1625-thickbox%2Fsticker-tag-graffiti-130x205-cm-ref-8832-.jpg",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.pBTmW3kO99mguHVREoHS6AEsDi%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F_36SFFFDlygA%2FTTDmlvTDQmI%2FAAAAAAAALGo%2F2gDdhnT2GxA%2Fs1600%2Fgraffiti-tags-3.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F_i3h7VNlc6kY%2FSxC1k11ZS2I%2FAAAAAAAACxA%2FMdFtuA3dz-c%2Fs1600%2FGraffiti%2BTagging%2Bblue%2Bcolor.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.graffitiactionhero.org%2Fuploads%2F9%2F5%2F1%2F3%2F9513440%2F1203555_orig.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.SoiivpIhXcWX7FYOVnoiIwEsDg%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F_LYNVGEXliZ4%2FTUWHAZyoYsI%2FAAAAAAAABoI%2Fu71et0_U03g%2Fs1600%2Fgraffiti-skate-park.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fgraffiticollector.files.wordpress.com%2F2012%2F07%2Fmodacalle-moda-graffiti-022.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.marcofolio.net%2Fimages%2Fstories%2Fart%2Fphotoshop%2Fgraffiti%2F16_lapd_pig.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F2.bp.blogspot.com%2F-SZey1TCKOVU%2FT12TNlY1u2I%2FAAAAAAAACUA%2F_6AkX6BE2mk%2Fs640%2Fstreetart_6.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.all2need.com%2Fwp-content%2Fuploads%2F2013%2F02%2F3D-Street-Art-16.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fblog.gaborit-d.com%2Fwp-content%2Fuploads%2F2013%2F04%2Fstreetart-vol14-63.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.all2need.com%2Fwp-content%2Fuploads%2F2013%2F02%2F3D-Street-Art-20.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-sMcOYLr02AQ%2FUIFE6fhAGsI%2FAAAAAAAAbks%2FC79nccocOvU%2Fs1600%2Fstreetartnews_roa_puertorico.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.drodd.com%2Fimages10%2Fgraffiti-drawings8.jpg&f=1"};
        for (int i = 0; i < trainingStreetArtURL.length; i++) {
            initialClient.addInputs()
                    .plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(trainingStreetArtURL[i]))
                                    .withConcepts(Concept.forID("StreetArt")) // ID
                    )
                    .executeSync();
        }
        String[] trainingStreetArtURLNegative = {
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.fatcap.org%2Fuploads%2Fmsc%2F2009-11-10%2Frglr_8592de6b9579129a981092f4b3870dfc84910ba3.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.brooklynstreetart.com%2Ftheblog%2Fwp-content%2Fuploads%2F2014%2F03%2Fbrooklyn-street-art-meer-sau-Salzburg-Austria-art-isnot-crime-web.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.fZIz79HBqM1HgYK8GrGc6AEsDh%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.69pjQxRs8poR-NGcwn6IzQEsDz%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.B6OJxJh_uZEaESgNLbvx-wEsDI%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm8.staticflickr.com%2F7008%2F6668555095_1961c082eb_b.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fdenswca.files.wordpress.com%2F2012%2F06%2Fstayhigh-149.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.TmjMbyu-3hXQ-oXxDSZR1gEsDH%26pid%3D15.1&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.usaoncanvas.com%2Fimages%2Farticles%2Fgraffiti_tag_street_art.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fmedia-cache-ak0.pinimg.com%2F736x%2F82%2Fee%2Fc4%2F82eec49e1dae41c81b0659905cf18978.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F_LYNVGEXliZ4%2FTUWHGPA6yyI%2FAAAAAAAABoM%2FQOiDxpbqw3c%2Fs1600%2Flondon%2Bgraffiti%2Btags%2Balphabet.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2F2.bp.blogspot.com%2F-PXQ6YIdzL_U%2FTY-35JQoc-I%2FAAAAAAAAADY%2FFZXwjbk7KBw%2Fs1600%2FHiphop%2BVs%2BGraffiti.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.doc.gold.ac.uk%2Fautograff%2Fwp%2Fwp-content%2Fuploads%2F2015%2F05%2Fautograff.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F236x%2F47%2F29%2F4f%2F47294f6b1d340f7617e88efe92f8c47d.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.graffitizen.com%2Fimages%2Fsniper-oner-handstyles-by-sniper-one-graffiti-tags-and-tagging-7086.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F564x%2F82%2F9b%2F5e%2F829b5e8d38c16e7c74f9eea4adec4f3c.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fimage.shutterstock.com%2Fz%2Fstock-vector-vector-graffiti-tags-writing-149500124.jpg&f=1",
                "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2F8%2F8d%2FGraffiti_tags.JPG&f=1"};

        for (int i = 0; i < trainingStreetArtURLNegative.length; i++) {
            initialClient.addInputs()
                    .plus(
                            ClarifaiInput.forImage(ClarifaiImage.of(trainingStreetArtURLNegative[i]))
                                    .withConcepts(Concept.forID("StreetArt").withValue(false)) // ID
                    )
                    .executeSync();
        }
        //Create Street Art Model
        initialClient.createModel("StreetArt")
                .withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("StreetArt")
                ))
                .executeSync();
        //Train Street Art Model
        initialClient.trainModel("StreetArt").executeSync();
    }

    private static ClarifaiClient client;

    //Beauty Model Predictor
    public static boolean determineBeautyClarifai(byte[] photoBytes) {

        ClarifaiResponse<List<ModelVersion>> status = client.getModelVersions("Beauty").getPage(1).executeSync();

        try {
            final List<ClarifaiOutput<Prediction>> predictionResults = client.predict("Beauty").withInputs(ClarifaiInput.forImage(
                    ClarifaiImage.of(photoBytes)))
                    .executeSync().get();
            String beautyPrediction = predictionResults.get(0).data().get(0).toString();
            beautyPrediction = beautyPrediction.substring(7);
            //beautyPrediction = "[" + beautyPrediction + "]";
            beautyPrediction = beautyPrediction.replaceAll("=", ":");
            JSONObject beautyJson = new JSONObject(beautyPrediction);
            double determine = beautyJson.getDouble("value");
            System.out.println("Beauty score: " + determine);
            if (determine >= DETERMINE_THRESHOLD) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Error!  Exception in determineBeautyClarifai!");
            e.printStackTrace();
            return false;
        }

    }

    //Old Detroit Predictor
    public static boolean oldDetroitModelClarifai(byte[] photoBytes) {

        try {
            final List<ClarifaiOutput<Prediction>> predictionResults = client.predict("oldDetroit").withInputs(ClarifaiInput.forImage(
                    ClarifaiImage.of(photoBytes)))
                    .executeSync().get();
            String oldDetroitPrediction = predictionResults.get(0).data().get(0).toString();
            oldDetroitPrediction = oldDetroitPrediction.substring(7);
            oldDetroitPrediction = oldDetroitPrediction.replaceAll("=", ":");
            JSONObject oldDetroitJson = new JSONObject(oldDetroitPrediction);
            double determine = oldDetroitJson.getDouble("value");
            System.out.println("Remains score: " + determine);
            if (determine >= DETERMINE_THRESHOLD) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Error!  Exception in oldDetroitModelClarifai!");
            e.printStackTrace();
            return false;
        }

    }

    //Street Art Predictor
    public static boolean streetArtModelClarifai(byte[] photoBytes) {

        try {
            final List<ClarifaiOutput<Prediction>> predictionResults = client.predict("StreetArt").withInputs(ClarifaiInput.forImage(
                    ClarifaiImage.of(photoBytes)))
                    .executeSync().get();
            String streetArtPrediction = predictionResults.get(0).data().get(0).toString();
            streetArtPrediction = streetArtPrediction.substring(7);
            streetArtPrediction = streetArtPrediction.replaceAll("=", ":");
            JSONObject streetArtJson = new JSONObject(streetArtPrediction);
            double determine = streetArtJson.getDouble("value");
            System.out.println("Art score: " + determine);
            if (determine >= DETERMINE_THRESHOLD) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Error!  Exception in streetArtModelClarifai!");
            e.printStackTrace();
            return false;
        }

    }
}