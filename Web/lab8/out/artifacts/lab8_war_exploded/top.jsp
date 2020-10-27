<%@ page import="lab8.UserRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="lab8.Image" %><%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 5/15/2020
  Time: 5:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}stylesheet.css">
    <title>Top pictures</title>
</head>
<body>

<div class="container2">
    <div class="images">
        <%
            UserRepository repo = new UserRepository();
            int numberOfImages = Integer.parseInt(request.getParameter("pic-number"));

            List<Image> images = repo.getTopImages(numberOfImages);
            request.setAttribute("images", images);
        %>
        <ul>
            <c:forEach items="${images}" var="item">
                <img src="<c:out value="images/${item.path}"/>" alt="image" style="max-width: 20%">
                <p><c:out value="Author: ${item.owner}"/></p>
                <p><c:out value="Votes: ${item.votes}"/></p>
            </c:forEach>
        </ul>
    </div>

    <form name="Go back" action="${pageContext.request.contextPath}/mainPage.jsp" method="post">
        <button type="submit">Go back</button>
    </form>
</div>

</body>
</html>
