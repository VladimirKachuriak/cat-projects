<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 21.05.2022
  Time: 09:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <script src="js/lang.js"></script>
    <title><fmt:message key="title.authorization"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <c:if test="${not empty requestScope.message}">
        <div class="alert alert-success">
            <fmt:message key="${requestScope.message}"/>
        </div>
    </c:if>
    <form class="form-signin" method="post" action="login">
        <h1 class="h3 mb-3 font-weight-normal">Rent car service</h1>
        <label for="login" class="sr-only"><fmt:message key="label.login"/> </label>
        <input type="text" id="login" name="login" class="form-control" value='${requestScope.login}'
               placeholder='<fmt:message key="placeholder.enterLogin" />' required autofocus>
        <label for="password" class="sr-only"><fmt:message key="label.password" /></label>
        <input type="password" id="password" name="password" class="form-control" value='${requestScope.password}'
               placeholder="<fmt:message key="placeholder.enterPassword" />" required>

        <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="button.enter" /></button>
        <p class="mt-5 mb-3 text-muted">&copy; 2022</p>
    </form>
</div>
<a class="nav-link" href="registration"><fmt:message key="title.registration"/></a>
<a class="nav-link" href="catalog"><fmt:message key="title.catalog"/></a>
<%@include file="footer.jsp" %>
</body>
</html>
