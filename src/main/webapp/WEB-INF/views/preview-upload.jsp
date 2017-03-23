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

    <title>Detroit Perspective</title>

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
        width: 45%;
        height: 45%;
        background-color: grey;
        padding-right: 30px;
      }

      { display: inline-block; }

      img {
        display: block;
        max-width: 100%;
        max-height: 70%;
        width: auto;
        height: auto;
        padding-left: 30px;
      }
    </style>

    <!-- For Croppie -->
    <link rel="stylesheet" href="/webresources/css/croppie/croppie.css" />
    <script src="/webresources/js/croppie/croppie.js"></script>

  </head>

  <body>

    <h1>Upload Preview</h1>

    <a href="/">Home</a><br><br><br>

    <form action="upload" method="post" align="center">

      <input name="category" type="hidden" value="${category}" />

      <input type="hidden" id="lat" name="lat" value="">
      <input type="hidden" id="lng" name="lng" value="">
      <input type="submit" value="Upload" />

    </form>

    <img class="visible-lg-inline-block" src="${imageURL}" alt="Preview" align="left"/>

    <!-- Google Maps API -->

    <div align="right">

      <div align="top">
    ${geoMessage}<br>
    <div id="map" class="visible-lg-inline-block"></div>

    <script type="text/javascript">

      var latLng = {lat: ${lat}, lng: ${lng}};
      document.getElementById("lat").value = ${lat};
      document.getElementById("lng").value = ${lng};

      function initMap() {


        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: latLng
        });

        map.setOptions({disableDoubleClickZoom: true });

        var marker = new google.maps.Marker({
          position: latLng,
          map: map
        });

        map.addListener('dblclick', function(event) {

          marker.setPosition(event.latLng);

          document.getElementById("lat").value = marker.getPosition().lat();
          document.getElementById("lng").value = marker.getPosition().lng();

        });
      }

    </script>

    <script async defer

        src="https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap">

    </script>

      </div>

    </div>


  </body>

</html>
