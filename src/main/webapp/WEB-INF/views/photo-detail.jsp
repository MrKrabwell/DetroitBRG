<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yosuk
  Date: 3/17/2017
  Time: 10:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

</head>
<body>

  <img src="${imageURL}${photo.fileName}" alt="${photo.fileName}" style="width:500px;height:350px">

  <div class="googleMaps">

    <img src="${gMapPhotoLocationURL}">

  </div>

  <div>

    <a href="vote?type=true&photoId=${photo.photoId}"> Upvote </a>

    Votes: ${photo.votes}

    <a href="vote?type=false&photoId=${photo.photoId}"> Downvote </a>

  </div>
<!--
  <c:if test="${loggedIn == false}">

    <script>

      alert("You must be logged in to do that!");

    </script>

  </c:if>
-->
  <c:if test="${message != null}">

    <script>

      alert("${message}");

    </script>

  </c:if>

</body>
</html>
