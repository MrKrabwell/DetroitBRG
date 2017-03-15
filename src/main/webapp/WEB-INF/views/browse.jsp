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

    <a href="imageDetails"><img src="${imageURL}${photos.get(0).fileName}" alt="${photos.get(0).fileName}"></a>

    <a href="imageDetails"><img src="${imageURL}${photos.get(1).fileName}" alt="${photos.get(1).fileName}"></a>

    <a href="imageDetails"><img src="${imageURL}${photos.get(2).fileName}" alt="${photos.get(2).fileName}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

    <a href="imageDetails"><img src="${picture}" alt="${picture.getName()}"></a>

</div>

<div class="PageControl">

    <a href="back">Back</a>

    <a href="forward">Forward</a>

</div>

</body>

</html>
