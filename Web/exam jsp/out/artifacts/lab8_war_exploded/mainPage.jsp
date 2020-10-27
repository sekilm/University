<%@ page import="java.util.List" %>
<%@ page import="exam.UserRepository" %>
<%@ page import="exam.Topic" %><%--
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


    <form name="Update" action="${pageContext.request.contextPath}update.jsp" method="post">
        <button type="submit">Update post</button>
    </form>

    <form name="Add post" action="${pageContext.request.contextPath}add.jsp" method="post">
        <button type="submit">Add post</button>
    </form>

    <form name="Logout" action="${pageContext.request.contextPath}/servlet/logout" method="post">
        <button type="submit">Log out</button>
    </form>
</div>

</body>
</html>
