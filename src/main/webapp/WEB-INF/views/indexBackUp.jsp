<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

  <title>DetroitABLE</title>

  <!-- TODO: link CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>

<body>

  <h1>DetroitABLE</h1>

  <nav class="navbar navbar-default">
      <div class="container">
          <div class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="/">Home</a>
          </div>
          <div class="collapse navbar-collapse" id="myNavbar">
              <ul class="nav navbar-nav navbar-right">
                  <li><a href="${facebookLogin}">Register with Facebook</a></li>
                  <li><a href="showLogin">Log In</a></li>
                  <li><a href="submit">Submit Photos</a></li>
              </ul>
          </div>
      </div>
  </nav>

  <h1 align="center">View Galleries</h1><br>


  <div class="homepagePhotos">

    <c:forEach items="${category}" var="category">

        <div class="row">
            <div class="col-md-4">
                <div class="thumbnail">

      <a href="browse?cat=${category}&page=1"><img src="<!--TODO: Category-->" alt="${category.toString()}"/></a>

                </div>
            </div>
        </div>

    </c:forEach>

  </div>

  <div class="googleMaps">

    <img src="${gMapTopPhotosLocationURL}">

  </div>

</body>

</html>
