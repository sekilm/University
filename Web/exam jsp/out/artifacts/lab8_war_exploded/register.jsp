<%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 5/9/2020
  Time: 6:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}stylesheet.css">
    <script type="text/javascript" src="scripts.js"></script>
    <title>Register</title>
</head>
<body>

<div class="container2">
    <form name="Sign up" action="${pageContext.request.contextPath}/servlet/register" method="post">
        <div class="container">
            <div class="t1">
                <span>Username:</span>
                <span>Password:</span>
                <span>Confirm password:</span>
            </div>
            <div class="t2">
                <input type="text" name="username" onchange="checkLogin()"/>
                <input type="password" name="password" onchange="checkLogin()"/>
                <input type="password" name="confirm-password" onchange="checkLogin()"/>
            </div>
        </div>
        <br>
        <input type="submit" value="Sign up"/>
    </form>

    <p id="error-box"></p>

    <p>
        <%
            if(session.getAttribute("message") != null) {
                String msg = (String) session.getAttribute("message");
                out.println(msg);
                session.setAttribute("message", "");
            }
        %>
    </p>

    <form name="Go back" action="${pageContext.request.contextPath}/index.jsp" method="post">
        <button type="submit">Go back</button>
    </form>
</div>

</body>
</html>
