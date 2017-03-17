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

    <title>Detroit BRG</title>

</head>

<body>

<h2>${category.toString()}</h2>

<div class="BrowseImages">

    <c:forEach var="photo" items="${photos}">

        <a href="photo?id=${photo.photoId}">

            <img src="${imageURL}${photo.fileName}" alt="${photo.fileName}" style="width:500px;height:350px">

        </a>
        
    </c:forEach>

</div>

<div class="PageControl">

    <a href="browse?cat=${category}&page=${currentPage-1}">Back</a>

        <c:forEach begin="1" end="${numPages}" var="page">

            <a href="browse?cat=${category}&page=${page}">${page}</a>

        </c:forEach>

    <a href="browse?cat=${category}&page=${currentPage+1}">Forward</a>

</div>

<br>

<a href="/">Home</a>

</body>

</html>
