<%--
  Created by IntelliJ IDEA.
  User: kamel
  Date: 1/12/2017
  Time: 3:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

  <title>Detroit BRG</title>

  <!-- TODO: link CSS -->
  <link href="../../resources/.css"/>

</head>

<body>

  <h1>Detroit BRG</h1>

  <div class="reglog">

    <a href="${facebookLogin}">Register with Facebook</a>

    <a href="showLogin">Log In</a>

  </div>

  <div class="submission">

    <a href="submit">Submit Photos</a>

  </div>

  <div class="homepagePhotos">

    <a href="browse?cat=old"><img src="<!--TODO: Old Detroit Photo-->" alt="Old Detroit"/></a>

    <a href="browse?cat=new"><img src="<!--TODO: Beauty Photo-->" alt="New Detroit"/></a>

    <a href="browse?cat=street"><img src="<!--TODO: Street Art Photo-->" alt="Street Art"/></a>

  </div>

  <div class="googleMaps">

    <h4>Google Maps placeholder</h4>

    <img src="https://maps.googleapis.com/maps/api/staticmap?center=Detroit+Michigan&zoom=13&size=500x400&maptype=roadmap">

  </div>

</body>

</html>
