<%--
  Created by IntelliJ IDEA.
  User: yosuk
  Date: 3/10/2017
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Detroit ABLE</title>

    <!-- Bootstrap Core CSS -->
    <link href="/webresources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/webresources/css/landing-page.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/webresources/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style>
      #map {
        width: 100%;
        height: 400px;
        background-color: grey;
      }
    </style>

    <!-- For Darkroom.js -->
    <link rel='stylesheet prefetch' href='https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.1/css/materialize.min.css'>
    <link rel='stylesheet prefetch' href='http://rawgit.com/MattKetmo/darkroomjs/master/build/darkroom.css'>

  </head>

  <body>

    <h1>This is your upload</h1>

    <a href="/">Home</a><br><br><br>

    <form action="upload" method="post" enctype="multipart/form-data" >

      <input type="hidden" name="file" value="${originalImage}" id="photo"/><br>

      <select name="category">

        <c:forEach items="${category}" var="category">

          <option value="${category}"><c:out value="${category.toString()}"></c:out></option>

        </c:forEach>

      </select>

      <input type="submit" value="Upload"/>

    </form>




    <br><br><br><br>
    <div class="row">
      <div class="col s12 m7">
        <img src="${image}" id="target">
      </div>
    </div>

    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/fabric.js/1.5.0/fabric.min.js'></script>
    <script src='http://rawgit.com/MattKetmo/darkroomjs/master/build/darkroom.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/camanjs/4.1.2/caman.full.js'></script>

    <div align="center">
    <script>
        var dkrm = new Darkroom('#target', {
            // Size options
            minWidth: 100,
            minHeight: 100,
            maxWidth: 600,
            maxHeight: 500,
            ratio: 4/3,
            backgroundColor: '#000',
            // Plugins options
            plugins: {
                //save: false,
                crop: {
                    quickCropKey: 67, //key "c"
                }
            },
            // Post initialize script
            initialize: function() {
//		var cropPlugin = this.plugins['crop'];
                // cropPlugin.selectZone(170, 25, 300, 300);
//		cropPlugin.requireFocus();
            }
        });
    </script>

    <div id="map"></div>

    <script>

      function initMap() {

        var uluru = {lat: ${lat}, lng: ${lng}}

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 10,
          center: uluru
        });

        var marker = new google.maps.Marker({
          position: uluru,
          map: map
        });

      }

    </script>

    <script async defer

        src="https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap">

    </script>
    </div>

  </body>

</html>
