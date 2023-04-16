<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 20.05.2022
  Time: 19:14
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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title><fmt:message key="title.registration" /></title>
</head>
<body>
<div class="container">
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success">
            <fmt:message key="${sessionScope.message}"/>
            <c:remove var="message" scope="session"/>
        </div>
    </c:if>
<form action="registration" method="post" role="form" data-toggle="validator">

    <h2><fmt:message key="label.welcome" /></h2>
    <div class="form-group col-xs-4">
        <label for="login" class="control-label col-xs-4"><fmt:message key="label.login" />:</label>
        <input type="text" name="login" id="login" class="form-control" value="${user.login}" required="true"/>

        <label for="firstname" class="control-label col-xs-4"><fmt:message key="label.firstname" />:</label>
        <input type="text" name="firstname" id="firstname" class="form-control" value="${user.firstName}" required="true"/>

        <label for="lastname" class="control-label col-xs-4"><fmt:message key="label.lastname" />:</label>
        <input type="text" name="lastname" id="lastname" class="form-control" value="${user.lastName}" required="true"/>


        <label for="email" class="control-label col-xs-4"><fmt:message key="label.email" />:</label>
        <input type="text" name="email" id="email" class="form-control" value="${user.email}" placeholder="smith@aol.com"
               required="true"/>

        <label for="password" class="control-label col-xs-4"><fmt:message key="label.password" />:</label>
        <input type="text" name="password" id="password" class="form-control" value="${user.password}" required="true"/>

        <label for="phone" class="control-label col-xs-4"><fmt:message key="label.phonenumber" />:</label>
        <input type="text"  name="phone" id="phone" class="form-control" value="${user.phoneNumber}" placeholder="+123456" required="true"/>

        <br></br>
        <button type="submit" class="btn btn-primary  btn-md"><fmt:message key="button.accept" /></button>
    </div>
</form>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
