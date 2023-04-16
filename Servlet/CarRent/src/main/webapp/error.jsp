<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 29.05.2022
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <script src="js/lang.js"></script>
    <title><fmt:message key="title.error"/></title>
</head>
<body>
<p1><fmt:message key="label.error"/></p1>
<a class="nav-link" href="catalog"><fmt:message key="title.catalog"/></a>
<%@include file="footer.jsp" %>
</body>
</html>
