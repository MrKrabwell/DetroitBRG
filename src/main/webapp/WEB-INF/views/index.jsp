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
  <link href="../../resources/.css"/>

</head>

<body>

  <h1>DetroitABLE</h1>

  <div class="reglog">

    <a href="${facebookLogin}">Register with Facebook</a>

    <a href="showLogin">Log In</a>

  </div>

  <div class="submission">

    <a href="submit">Submit Photos</a>

  </div>

  <div class="homepagePhotos">

    <c:forEach items="${category}" var="category">

      <a href="browse?cat=${category}&page=1"><img src="<!--TODO: Category-->" alt="${category.toString()}"/></a>

    </c:forEach>

  </div>

  <div class="googleMaps">

    <img src="${gMapTopPhotosLocationURL}">

  </div>

</body>

</html>
