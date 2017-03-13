<%--
  Created by IntelliJ IDEA.
  User: yosuk
  Date: 3/10/2017
  Time: 11:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

  <head>

    <title>Detroit BRG</title>

  </head>

  <body>

  <h1>Testing DB Access!!</h1>


    <!--
    <table>

      <tr>

        <td>User ID</td><td>First Name</td><td>Last Name</td>

      </tr>

      <c:forEach var="element" items="${list}">

        <tr>

          <td>${element.getUserId()}</td>
          <td>${element.getFirstName()}</td>
          <td>${element.getLastName()}</td>

        </tr>

      </c:forEach>

    </table>
    -->

  <div id="global">
    <h4>The product has been saved.</h4>
    <h5>Details:</h5>
    Product Name: ${product.name}<br/>
    Description: ${product.description}<br/>
    <p>Following files are uploaded successfully.</p>
    <ol>
      <c:forEach items="${product.images}" var="image">
        <li>${image.originalFilename}
        <li>${image.images}
          <img width="100" src="<c:url value="/resources/photos/"/>${image.originalFilename}"/>

        </li>
      </c:forEach>
    </ol>
  </div>

  </body>

</html>
