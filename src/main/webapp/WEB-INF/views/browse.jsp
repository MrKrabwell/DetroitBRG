<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yosuk
  Date: 3/14/2017
  Time: 3:42 PM
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

    <link rel="stylesheet" href="https://www.w3schools.com/w3css/3/w3.css">

</head>

<body>

    <!-- !PAGE CONTENT! -->
    <div class="w3-content" style="max-width:1500px">

        <!-- Header -->
        <div class="well">
            <h1><!--Submit your Detroit Photo</h1>-->
                <span>${category.toString()}</span>
                <a href="/" class="btn btn-info pull-right" style="margin-right: 10%" role="button">Home</a>
            </h1>
        </div>

        <!-- Photo Grid -->
        <div class="">
            <c:forEach var="photo" items="${photos}">
                <a href="photo?id=${photo.photoId}&prev=${currentPage}">
                    <img src="${imageURL}${photo.fileName}" alt="${photo.fileName}" style="width: 500px;">
                </a>
            </c:forEach>
        </div>

        <!-- End Page Content -->
    </div>

    <br>

    <footer class="w3-light-grey w3-padding-32 w3-center" id="about">
        <h2>
            <a href="browse?cat=${category}&page=${currentPage-1}" class="btn btn-info" role="button">Back</a>
                <c:forEach begin="1" end="${numPages}" var="page">
                    <a href="browse?cat=${category}&page=${page}">${page}</a>
                </c:forEach>
            <a href="browse?cat=${category}&page=${currentPage+1}" class="btn btn-info" role="button">Forward</a>
        </h2>
    </footer>

</body>

</html>
