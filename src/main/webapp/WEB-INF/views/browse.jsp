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

<div class="BrowseImages">

    <c:forEach var="photo" items="${photos}">

        <a href="imageDetails"><img src="${imageURL}${photo.fileName}" alt="${photo.fileName}"></a>

    </c:forEach>

</div>

<div class="PageControl">

    <a href="back">Back</a>

    <a href="forward">Forward</a>

</div>

</body>

</html>
