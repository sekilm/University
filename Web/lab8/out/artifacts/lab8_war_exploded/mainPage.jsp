<%@ page import="java.util.List" %>
<%@ page import="lab8.UserRepository" %>
<%@ page import="lab8.Image" %><%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 4/29/2020
  Time: 5:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}stylesheet.css">
    <title>Main page</title>
</head>
<body>

<div class="container2">
    <p>Welcome,
        <%
            String user = (String)session.getAttribute("username");
            out.println(user);
        %>
    </p>

    <div class="images">
        <%
            UserRepository repo = new UserRepository();
            List<Image> images = repo.getImages(user);
            request.setAttribute("images", images);
        %>
        <ul>
            <c:forEach items="${images}" var="item">
                <img src="<c:out value="images/${item.path}"/>" alt="image" style="max-width: 20%">
                <p><c:out value="Votes: ${item.votes}"/></p>
                <form name="Upload pic" action="${pageContext.request.contextPath}/servlet/vote" method="post">
                    <input type="hidden" name="image-name" value="<c:out value="${item.path}"/>"/>
                    <button type="submit">Vote</button>
                </form>
            </c:forEach>
        </ul>
    </div>
    <br>

    <form name="Upload pic" action="${pageContext.request.contextPath}/upload.jsp" method="post">
        <button type="submit">Upload picture</button>
    </form>

    <form name="Top pics" action="${pageContext.request.contextPath}/top.jsp" method="post">
        <input type="number" name="pic-number"/>
        <button type="submit">See top pictures</button>
    </form>

    <form name="Logout" action="${pageContext.request.contextPath}/servlet/logout" method="post">
        <button type="submit">Log out</button>
    </form>
</div>

</body>
</html>
