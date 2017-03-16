
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
    <!--TODO: disclaimer-->Disclaimer: blah blah blah blah blah
  </p>

</body>

</html>
