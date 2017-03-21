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

  </head>

  <body>

    <h1>This is your upload</h1>

    <a href="/">Home</a><br>

    <image src="${imageURL}" alt="Preview Photo" style="width:1000px;height:700px"/>



    <figure class="image-container target">
      <div class="darkroom-container">
        <div class="darkroom-toolbar">
          <div class="darkroom-button-group">
            <button type="button" class="darkroom-button darkroom-button-default" disabled="">
              <svg class="darkroom-icon">
                <use xlink:href="#undo"></use></svg></button><button type="button" class="darkroom-button darkroom-button-default" disabled=""><svg class="darkroom-icon"><use xlink:href="#redo"></use></svg></button></div><div class="darkroom-button-group"><button type="button" class="darkroom-button darkroom-button-default"><svg class="darkroom-icon"><use xlink:href="#rotate-left"></use></svg></button><button type="button" class="darkroom-button darkroom-button-default"><svg class="darkroom-icon"><use xlink:href="#rotate-right"></use></svg></button></div><div class="darkroom-button-group"><button type="button" class="darkroom-button darkroom-button-default darkroom-button-active"><svg class="darkroom-icon"><use xlink:href="#crop"></use></svg></button><button type="button" class="darkroom-button darkroom-button-success"><svg class="darkroom-icon"><use xlink:href="#done"></use></svg></button><button type="button" class="darkroom-button darkroom-button-danger"><svg class="darkroom-icon"><use xlink:href="#close"></use></svg></button></div><div class="darkroom-button-group"><button type="button" class="darkroom-button darkroom-button-default"><svg class="darkroom-icon"><use xlink:href="#save"></use></svg></button></div></div><div class="darkroom-image-container"><div class="canvas-container" style="width: 600px; height: 450px; position: relative; -moz-user-select: none;"><canvas class="lower-canvas" style="position: absolute; width: 600px; height: 450px; left: 0px; top: 0px; -moz-user-select: none;" width="600" height="450"></canvas><canvas class="upper-canvas " style="position: absolute; width: 600px; height: 450px; left: 0px; top: 0px; -moz-user-select: none; cursor: crosshair;" width="600" height="450"></canvas></div></div><div class="darkroom-source-container" style="display: none;"><div class="canvas-container" style="width: 1024px; height: 685px; position: relative; -moz-user-select: none;"><canvas class="lower-canvas" style="position: absolute; width: 1024px; height: 685px; left: 0px; top: 0px; -moz-user-select: none;" width="1024" height="685"></canvas><canvas class="upper-canvas " style="position: absolute; width: 1024px; height: 685px; left: 0px; top: 0px; -moz-user-select: none;" width="1024" height="685"></canvas></div></div><img src="./images/domokun-big.jpg" alt="DomoKun" id="target" style="display: none;" class="canvas-img"></div>
    </figure>




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

  </body>

</html>
