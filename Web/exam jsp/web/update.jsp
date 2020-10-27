<%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 6/23/2020
  Time: 4:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update post</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}stylesheet.css">
</head>
<body>
<form name="Update post" action="${pageContext.request.contextPath}/servlet/update" method="post">
    <div class="container">
        <div class="t1">
            <span>Post id:</span>
            <span>Topic id:</span>
            <span>Text:</span>
        </div>
        <div class="t2">
            <input type="number" name="post-id"/>
            <input type="number" name="topic-id"/>
            <input type="text" name="topic-text"/>
        </div>
    </div>
    <br>
    <input type="submit" value="Update"/>
</form>
</body>
</html>
