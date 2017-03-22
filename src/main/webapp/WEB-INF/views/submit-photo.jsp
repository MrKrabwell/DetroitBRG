<%--
  Created by IntelliJ IDEA.
  User: yosuk
  Date: 3/13/2017
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic" rel="stylesheet"
          type="text/css">
    <link href="/webresources/css/InputButton.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>


    <div class="well">
  <h1><!--Submit your Detroit Photo</h1>-->
      <span>Submit your photos of Detroit</span>
      <a href="/" class="btn btn-info pull-right" style="margin-right: 10%" role="button">Home</a>
  </h1>
    </div><br><br>



  <div align="center">

    <form action="preview" method="post" enctype="multipart/form-data">

        <h2 for="photo">Choose a Photo:</h2><br>

        <!--<input type="file"  name="file" id="photo"/>-->

    <span class="btn btn-default btn-file ">
    Browse <input type="file" name="file" id="photo"/>
    </span>

        <br>

        <h2>Select a category:</h2><br>


        <select class="form-control" name="category">

            <c:forEach items="${category}" var="category">

                <option value="${category}"><c:out value="${category.toString()}"></c:out></option>

            </c:forEach>

        </select><br><br><br><br>



    <!--<input type="submit" value="Upload"/>-->

        <span class="btn btn-default btn-submit">
    Upload <input type="submit"/>
    </span>

    </form>
    <br><br><br>

    <h3>

        All Photos are submitted Anonymously! <br>
        
        We check if your photo matches the criteria for a given category.<br><br>

        For Skyline, photos must be of downtown architecture and rebuilt structures.<br>
        Pictures of houses and mansions outside Detroit will not pass.<br><br>

        For Street Art, photos must contain images of colorful and creative images.<br>
        Pictures of crude graffiti tags containing single-line and/or single-color will not pass.<br><br>

        For Ruins photos must be inside abandoned structures or outside vacant buildings.<br>
        Pictures of ruined houses especially caved in roofs will not pass.<br><br>

    </h3>

</div>

</body>

</html>
