
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

  <title>Detroit BRG</title>

</head>

<body>

  <h1>Submit your Detroit Photo</h1>

  <form action="uploadPhoto" method="post" enctype="multipart/form-data" >

    <label for="photo">Choose a Photo:</label>

    <input type="file" name="file" id="photo"/>

    <select name="category">

      <c:forEach items="${category}" var="category">

        <option value="${category}"><c:out value="${category.toString()}"></c:out></option>

      </c:forEach>

    </select>

    <input type="submit" value="Upload"/>

  </form>

  <p>
    We check if your photo matches the criteria for a given category.

    For Beauty, photos must be of downtown architecture and rebuilt structures.
    Pictures of houses and mansions outside Detroit will not pass.

    For Old Detroit, photos must be inside abandoned structures or outside vacant buildings.
    Pictures of ruined houses especially caved in roofs will not pass.

    For Art, photos must contain images of colorful and creative images.
    Pictures of crude graffiti tags containing single-line and/or single-color will not pass.

    Any pictures outside of the city limits of Detroit will not pass.


  </p>

</body>

</html>
