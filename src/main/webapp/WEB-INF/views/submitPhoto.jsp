
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

  <h1>Submit your photo here!</h1>


  <div id="global">
    <form:form commandName="product" action="save-product" method="post" enctype="multipart/form-data">
      <fieldset>
        <legend>Add a product</legend>
        <p>
          <label for="name">Product Name: </label>
          <form:input id="name" path="name" cssErrorClass="error" />
          <form:errors path="name" cssClass="error" />
        </p>
        <p>
          <label for="description">Description: </label>
          <form:input id="description" path="description" />
        </p>
        <p>
          <label for="image">Product Images: </label>
          <input type="file" name="images" multiple="multiple" id="image"/>
        </p>
        <p id="buttons">
          <input id="reset" type="reset" tabindex="4">
          <input id="submit" type="submit" tabindex="5" value="Add Product">
        </p>
      </fieldset>
    </form:form>
  </div>



</body>

</html>
