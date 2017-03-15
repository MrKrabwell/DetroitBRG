package com.test.external;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.input.image.ClarifaiURLImage;
import clarifai2.dto.input.image.Crop;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.model.output_info.ConceptOutputInfo;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Controller
public class ClarifaiController {
    public static ClarifaiClient client = new ClarifaiBuilder("zVFg39Fi---sN1IcbsSsG13I7Ldc1Xdb2adszB5A",
            "1KBAt4KfnY6gG094Okne07fpNI0aXn0drfVBAZ5U").buildSync();



    @RequestMapping("test2")
    public static String beautyModelClarifai() throws InterruptedException {
        // Refresh the Model, by deleteing it
        client.deleteModel("Beauty").executeSync();

        // Add images to concept
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.gannett-cdn.com%2F-mm-%2Fcba46142da8fafe94960c089b9b502fb8d63f761%2Fc%3D136-0-2264-1600%26r%3Dx513%26c%3D680x510%2Flocal%2F-%2Fmedia%2F2017%2F01%2F23%2FDetroitFreePress%2FDetroitFreePress%2F636207780765061365-Old-Freep-Building-EC024.jpg&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fmedia.timeout.com%2Fimages%2F101657505%2Fimage.jpg&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs3.favim.com%2Forig%2F47%2Farchitecture-baroque-barrock-beautiful-building-Favim.com-433866.jpg"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs2.favim.com%2Forig%2F34%2Farchitecture-awesome-beautiful-building-buildings-Favim.com-272240.jpg"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://duckduckgo.com/?q=beautiful+architectural+&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fs3.favim.com%2Forig%2F47%2Farchitecture-beautiful-beauty-buildings-city-Favim.com-438159.jpg"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://duckduckgo.com/?q=detroit+river+walk&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fwww.portraitsbyrod.com%2Fwp-content%2Fuploads%2F2014%2F01%2FDetroit-Riverwalk-Final.jpg"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F736x%2Ff4%2F99%2F4d%2Ff4994d5acf68818ca5461dfe3d553d4a.jpg&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.detroityes.com%2Fwebisodes%2F2005%2F06-DetroitRises%2F01-Rise-006.jpg&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-qlPnvCg00h0%2FT-UuQDTp3lI%2FAAAAAAAABco%2FCrAeR7EnQqc%2Fs1600%2FPicture%2B002.jpg&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.freshwatercleveland.com%2Fgalleries%2FFeatures%2F2014%2FSeptember_2014%2FIssue_179%2Fmost_anticipated%2Fpublic_square_rendering.jpg&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fcbsdetroit.files.wordpress.com%2F2015%2F03%2Ffisher-building-e1434814315291.jpg%3Fw%3D1024&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fupload.wikimedia.org%2Fwikipedia%2Fcommons%2Fb%2Fbf%2FEighth_Precinct_Police_Station_Detroit.jpg&f=1"))
                                .withConcepts(Concept.forID("BeautyDetroit"))
                )
                .executeSync();

        //Creating Model
         ClarifaiResponse<ConceptModel> BeautyModelResponse = client.createModel("Beauty")
                .withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("BeautyDetroit")
                ))
                .executeSync();

        //Train Model
        client.trainModel("Beauty").executeSync();

        //predict with beauty model
        Thread.sleep(5000);
        final ClarifaiResponse predictionResults =
                client.predict("Beauty").withInputs(ClarifaiInput.forImage(
                        ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fimages4.fanpop.com%2Fimage%2Fphotos%2F15300000%2FBeagle-puppy-dog-hound-dogs-15363092-1600-1200.jpg&f=1")))
                .executeSync();
        return "index";

    }

    @RequestMapping("oldDetroit")
    public static String oldDetroitModelClarifai() throws InterruptedException {
      client.deleteModel("Old");

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2F2.bp.blogspot.com%2F-JBu3f7__OHg%2FT_M2Dx3TUuI%2FAAAAAAAACiE%2FhycGn-javT4%2Fw1200-h630-p-k-nu%2Fs_w15_RTR2KWTW.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-cxZtOhkisnI%2FTX79A2kr15I%2FAAAAAAAAAqM%2FAIiLRQr323k%2Fs1600%2Freliques_05.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.UTJKS_hMh91X-cbXfSvSHwEsDe%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.angryboar.com%2Fwp-content%2Fuploads%2F2013%2F11%2Fabandoned_buildings_in_Detroit_14-600x600.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2F4.bp.blogspot.com%2F-bJOuqLSQu00%2FUeX29fvdKwI%2FAAAAAAAAQCI%2FJBwmN7dY7-A%2Fs1600%2FDETFarwellBuildingAtrium.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fimg.weburbanist.com%2Fwp-content%2Fuploads%2F2012%2F06%2Fdetroit-abandoned-buildings.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fimages.fineartamerica.com%2Fimages-medium%2Fabandoned-building-in-detroit-joe-gee.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.slate.com%2Fcontent%2Fdam%2Fslate%2Fblogs%2Fbrowbeat%2F2012%2F01%2F25%2Fdetropia_documentary_paints_a_bleak_picture_of_former_city_of_the_future%2F53383565.jpg.CROP.rectangle3-large.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.angryboar.com%2Fwp-content%2Fuploads%2F2013%2F11%2Fabandoned_buildings_in_Detroit_7-600x600.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.angryboar.com%2Fwp-content%2Fuploads%2F2013%2F11%2Fabandoned_buildings_in_Detroit_3-600x600.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fphotos2.meetupstatic.com%2Fphotos%2Fevent%2Fc%2Fa%2Fb%2F4%2Fhighres_121911892.jpeg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit"))
                )
                .executeSync();
        client.createModel("Old")
                .withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("oldDetroit")
                ))
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm9.staticflickr.com%2F8170%2F8009382140_e9e40fafb9_z.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm9.staticflickr.com%2F8170%2F8009382140_e9e40fafb9_z.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of(" https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fi.dailymail.co.uk%2Fi%2Fpix%2F2013%2F07%2F24%2Farticle-0-1AF54269000005DC-401_964x639.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fmediad.publicbroadcasting.net%2Fp%2Fmichigan%2Ffiles%2Fstyles%2Frelated%2Fpublic%2F201208%2Fdelray-01.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm3.staticflickr.com%2F2429%2F3629881203_a9ee1049bb_z.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.japantimes.co.jp%2Fwp-content%2Fuploads%2F2013%2F01%2Fnn20111130f3b.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2F2.bp.blogspot.com%2F-4DreqdF8-Pw%2FVVqG56IBX_I%2FAAAAAAAAKmc%2Fu9ouSkD5Sdw%2Fs1600%2FIMG_1941.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F200x150%2Ffd%2F58%2F72%2Ffd587200d60d32d8d98a98159e2d68dd.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fthepigyear.files.wordpress.com%2F2013%2F01%2Fdsc_0140.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Factnowdetroit.files.wordpress.com%2F2012%2F04%2Fimg_4331.jpg&f=1"))
                                .withConcepts(Concept.forID("oldDetroit").withValue(false))
                )
                .executeSync();

        client.trainModel("oldDetroit").executeSync();

        Thread.sleep(5000);
        final ClarifaiResponse predictionResults =
                client.predict("Old").withInputs(ClarifaiInput.forImage(
                        ClarifaiImage.of("")))
                        .executeSync();
        return "index";
    }
    @RequestMapping("streetTest")
    public static String streetArtModelClarifai() throws InterruptedException {
        client.deleteModel("StreetArt");
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fonthecommons.org%2Fsites%2Fdefault%2Ffiles%2Fimagecache%2F300w%2FBlightBuster.JPG&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fs1.ibtimes.com%2Fsites%2Fwww.ibtimes.com%2Ffiles%2Fstyles%2Flg%2Fpublic%2F2014%2F01%2F10%2Fdetroit_0.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fblog.thedetroithub.com%2Fwp-content%2Fuploads%2F2012%2F10%2Fbeforeafter.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-ev2fEy7Ejc4%2FT6uhvLxOA4I%2FAAAAAAAAADs%2F_eRc1B-bwOc%2Fs1600%2Fdavid_walker_street_art_3_london1.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Faddcolortoyourlife.com%2Fwp-content%2Fuploads%2F2016%2F06%2Fperth-australia.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fgazduncan.files.wordpress.com%2F2012%2F09%2Ftumblr_ma8smyo3p51r6wmavo1_1280.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://duckduckgo.com/?q=street+art&t=ffab&iar=images&iax=1&ia=images&iai=http%3A%2F%2Fartist.com%2Fart-recognition-and-education%2Fwp-content%2Fthemes%2Fartist-blog%2Fmedia-files%2F2016%2F03%2Fstreet-art-4.jpg"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Filovedetroitmichigan.com%2Fwp-content%2Fuploads%2F2011%2F10%2F2340-Russell-St-Alley-Graffiti-2.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Filovedetroitmichigan.com%2Fwp-content%2Fuploads%2F2011%2F10%2F2340-Russell-St-Alley-Graffiti-5.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Filovedetroitmichigan.com%2Fwp-content%2Fuploads%2F2011%2F10%2F2340-Russell-St-Alley-Graffiti-5.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftwistedpreservation.files.wordpress.com%2F2014%2F09%2Fimg_7741.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fs-media-cache-ak0.pinimg.com%2F736x%2F59%2F5c%2F44%2F595c442137fa8dc16bb529697b2d1773.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2F37.media.tumblr.com%2Ftumblr_m9imcfxley1qaskyao1_500.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.l74A-fUDr_Peb-w9r3RK-wEQEs%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.graffitiactionhero.org%2Fuploads%2F9%2F5%2F1%2F3%2F9513440%2F4101507_orig.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.R2Ti_nYirnlDGIa1bbF0aQEsDc%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.expats.cz%2Fresources%2Fstreet-art-part-II-tags4.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn.vectorstock.com%2Fi%2Fcomposite%2F52%2C07%2Fgraffiti-tags-street-art-background-vector-1255207.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.psst.ph%2Fwp-content%2Fuploads%2F2016%2F07%2F3035115-slide-graffiti-park-002.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.artofthestate.co.uk%2Fphotos%2Fleake_street_walls.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://duckduckgo.com/?q=street+tag&t=ffab&iax=1&ia=images&iai=http%3A%2F%2Fwww.stickersmania.fr%2F1220-1625-thickbox%2Fsticker-tag-graffiti-130x205-cm-ref-8832-.jpg"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.pBTmW3kO99mguHVREoHS6AEsDi%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("StreetArt"))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.fatcap.org%2Fuploads%2Fmsc%2F2009-11-10%2Frglr_8592de6b9579129a981092f4b3870dfc84910ba3.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.brooklynstreetart.com%2Ftheblog%2Fwp-content%2Fuploads%2F2014%2F03%2Fbrooklyn-street-art-meer-sau-Salzburg-Austria-art-isnot-crime-web.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.fZIz79HBqM1HgYK8GrGc6AEsDh%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.69pjQxRs8poR-NGcwn6IzQEsDz%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.B6OJxJh_uZEaESgNLbvx-wEsDI%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffarm8.staticflickr.com%2F7008%2F6668555095_1961c082eb_b.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Fdenswca.files.wordpress.com%2F2012%2F06%2Fstayhigh-149.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.TmjMbyu-3hXQ-oXxDSZR1gEsDH%26pid%3D15.1&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();

        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.usaoncanvas.com%2Fimages%2Farticles%2Fgraffiti_tag_street_art.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.addInputs()
                .plus(
                        ClarifaiInput.forImage(ClarifaiImage.of("https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fmedia-cache-ak0.pinimg.com%2F736x%2F82%2Fee%2Fc4%2F82eec49e1dae41c81b0659905cf18978.jpg&f=1"))
                                .withConcepts(Concept.forID("StreetArt").withValue(false))
                )
                .executeSync();
        client.createModel("StreetArt")
                .withOutputInfo(ConceptOutputInfo.forConcepts(
                        Concept.forID("StreetArt")
                ))
                .executeSync();
        client.trainModel("StreetArt").executeSync();

        Thread.sleep(10000);
        final ClarifaiResponse predictionResults =
                client.predict("StreetArt").withInputs(ClarifaiInput.forImage(
                        ClarifaiImage.of("https://duckduckgo.com/?q=street+tag&t=ffab&iax=1&ia=images&iai=http%3A%2F%2Fmattweberphotos.files.wordpress.com%2F2010%2F03%2Ffutura2000-72.jpg")))
                        .executeSync();
        return "index";
    }

}
