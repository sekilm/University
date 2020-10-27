<%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 5/12/2020
  Time: 7:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload picture</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}stylesheet.css">
</head>
<body>

<div class="container2">
    <form name="Upload pic" action="${pageContext.request.contextPath}/servlet/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="image" accept="image/*">
        <button type="submit">Upload picture</button>
    </form>

    <form name="Go back" action="${pageContext.request.contextPath}/mainPage.jsp" method="post">
        <button type="submit">Go back</button>
    </form>
</div>

</body>
</html>
