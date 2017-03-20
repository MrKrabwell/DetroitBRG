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
    <title>DetroitABLE</title>
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

</body>
</html>
