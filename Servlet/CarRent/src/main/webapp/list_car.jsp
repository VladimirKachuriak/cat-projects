<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 22.05.2022
  Time: 12:59
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
    <title><fmt:message key="label.carList" /></title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <header class="masthead mb-auto">
        <div class="inner">
            <h3 class="masthead-brand"><fmt:message key="label.navigation"/></h3>
            <nav class="nav nav-masthead justify-content-center">
                <a class="nav-link" href="users"><fmt:message key="label.users"/></a>
                <a class="nav-link" href="adminOrders"><fmt:message key="label.orders"/></a>
                <a class="nav-link" href="logout"><fmt:message key="label.logout"/></a>
            </nav>
        </div>
    </header>
</div>
<div class="container">
<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-success">
        <fmt:message key="${sessionScope.message}"/>
                <c:remove var="message" scope="session" />
    </div>
</c:if>
<form action="car" method="post" id="carForm" role="form">
    <input type="hidden" id="idCar" name="idCar">
    <input type="hidden" id="action" name="action">
    <c:choose>
        <c:when test="${not empty carList}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <td>#</td>
                    <td><fmt:message key="label.brand" /></td>
                    <td><fmt:message key="label.model" /></td>
                    <td><fmt:message key="label.releaseDate" /></td>
                    <td><fmt:message key="label.carState" /></td>
                    <td><fmt:message key="label.carClass" /></td>
                    <td><fmt:message key="label.carPrice" /></td>
                </tr>
                </thead>
                <c:forEach var="car" items="${carList}">
                    <tr>
                        <td>
                            <a href="car?idCar=${car.id}&action=editCarById">${car.id}</a>
                        </td>
                        <td>${car.brand}</td>
                        <td>${car.model}</td>
                        <td>${car.releaseDate}</td>
                        <td>${car.state}</td>
                        <td>${car.autoClass}</td>
                        <td>${car.price}</td>
                        <td><a href="#" id="remove"
                               onclick="document.getElementById('action').value = 'remove';
                                       document.getElementById('idCar').value = '${car.id}';
                                       document.getElementById('carForm').submit();">
                            <span class="glyphicon glyphicon-trash"></span>
                        </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:when>
        <c:otherwise>
            <br></br>
            <div class="alert alert-info">
                <fmt:message key="label.warning.noCarMatching" />
            </div>
        </c:otherwise>
    </c:choose>
</form>
<form action="new_car.jsp" method="post">
    <br/>
    <button type="submit" class="btn btn-primary  btn-md"><fmt:message key="label.createCar" /></button>
</form>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
