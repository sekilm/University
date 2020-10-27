<%--
  Created by IntelliJ IDEA.
  User: Bogdan
  Date: 4/29/2020
  Time: 4:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}stylesheet.css">
    <title>Log in</title>
  </head>
  <body>

  <div class="container2">
      <form name="Login" action="${pageContext.request.contextPath}/servlet/login" method="post">
          <div class="container">
              <div class="t1">
                  <span>Username:</span>
                  <span>Password:</span>
              </div>
              <div class="t2">
                  <input type="text" name="username"/>
                  <input type="password" name="password"/>
              </div>
          </div>
          <br>
          <input type="submit" value="Log in"/>
      </form>

      <p>
          <%
            if(session.getAttribute("message") != null) {
                String msg = (String) session.getAttribute("message");
                out.println(msg);
                session.setAttribute("message", "");
            }
          %>
      </p>

      <form name="Register button" action="${pageContext.request.contextPath}/register.jsp" method="post">
          <button type="submit">Sign up</button>
      </form>
  </div>

  </body>
</html>
