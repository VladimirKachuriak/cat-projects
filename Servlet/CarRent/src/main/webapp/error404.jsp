<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 21.05.2022
  Time: 18:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="language"/>
<html lang="${cookie['lang'].value}">
<head>
    <script src="js/lang.js"></script>
    <title><fmt:message key="title.notFound"/></title>
</head>
<body>
<div class="container">
    <header class="masthead mb-auto">
        <div class="inner">
            <h3 class="masthead-brand"><fmt:message key="label.navigation"/></h3>
            <nav class="nav nav-masthead justify-content-center">
                <a class="nav-link" href="catalog"><fmt:message key="title.catalog"/></a>
                <a class="nav-link" href="login"><fmt:message key="label.login"/></a>
            </nav>
        </div>
    </header>
</div>
<h2><fmt:message key="label.warning.pathDoesNotExist"/>!</h2>
<%@include file="footer.jsp" %>
</body>
</html>
