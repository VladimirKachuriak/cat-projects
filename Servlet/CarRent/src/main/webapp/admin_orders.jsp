<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 26.05.2022
  Time: 10:03
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
    <title><fmt:message key="title.adminOrder" /></title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <header class="masthead mb-auto">
        <div class="inner">
            <h3 class="masthead-brand"><fmt:message key="label.navigation"/></h3>
            <nav class="nav nav-masthead justify-content-center">
                <a class="nav-link" href="car"><fmt:message key="label.cars"/></a>
                <a class="nav-link" href="users"><fmt:message key="label.users"/></a>
                <a class="nav-link" href="logout"><fmt:message key="label.logout"/></a>
            </nav>
        </div>
    </header>
</div>
<div class="container">
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success">
            <fmt:message key="${sessionScope.message}"/>
            <c:remove var="message" scope="session"/>
        </div>
    </c:if>
    <form action="adminOrders" method="post" id="ordersForm" role="form">
        <input type="hidden" id="idOrder" name="idOrder">
        <input type="hidden" id="action" name="action">
        <div class="form-group col-xs-4">
            <label for="orderResponse" class="control-label col-xs-4"><fmt:message key="label.message.comment" />:</label>
            <input type="text" name="orderResponse" id="orderResponse" class="form-control"/>
            <label for="damageAccount" class="control-label col-xs-4"><fmt:message key="label.message.damageAccount" /></label>
            <input type="number" min="0" name="damageAccount" id="damageAccount" class="form-control" oninput="validity.valid||(value='')"/>
        </div>
        <c:choose>
            <c:when test="${not empty orderList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="label.order.carId" /></td>
                        <td><fmt:message key="label.order.userId" /></td>
                        <td><fmt:message key="label.order.passportSerial" /></td>
                        <td><fmt:message key="label.order.passportEndDate" /></td>
                        <td><fmt:message key="label.order.startDate" /></td>
                        <td><fmt:message key="label.order.endDate" /></td>
                        <td><fmt:message key="label.order.state" /></td>
                        <td><fmt:message key="label.order.account" /></td>
                        <td><fmt:message key="label.order.accountDamage" /></td>
                        <td><fmt:message key="label.order.message" /></td>
                    </tr>
                    </thead>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.idCar}</td>
                            <td>${order.idUser}</td>
                            <td>${order.passportSerial}</td>
                            <td>${order.passportExpireDate}</td>
                            <td>${order.start_date}</td>
                            <td>${order.end_date}</td>
                            <td><fmt:message key="${order.state}"/></td>
                            <td>${order.account}</td>
                            <td>${order.accountDamage}</td>
                            <td>${order.message}</td>
                            <c:if test="${order.state == 'SEND'}">
                                <td><a href="#" id="reject"
                                       onclick="document.getElementById('action').value = 'reject';
                                               document.getElementById('idOrder').value = '${order.id}';
                                               document.getElementById('ordersForm').submit();">
                                    <span class="glyphicon glyphicon-remove"></span>
                                </a>
                                </td>
                                <td><a href="#" id="accept"
                                       onclick="document.getElementById('action').value = 'accept';
                                               document.getElementById('idOrder').value = '${order.id}';
                                               document.getElementById('ordersForm').submit();">
                                    <span class="glyphicon glyphicon-ok"></span>
                                </a>
                                </td>
                            </c:if>
                            <c:if test="${order.state == 'PAID'}">
                                <td><a href="#" id="damage"
                                       onclick="document.getElementById('action').value = 'damage';
                                               document.getElementById('idOrder').value = '${order.id}';
                                               document.getElementById('ordersForm').submit();">
                                    <span class="glyphicon glyphicon-wrench"></span>
                                </a>
                                </td>
                                <td><a href="#" id="finish"
                                       onclick="document.getElementById('action').value = 'finish';
                                               document.getElementById('idOrder').value = '${order.id}';
                                               document.getElementById('ordersForm').submit();">
                                    <span class="glyphicon glyphicon-ok"></span>
                                </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br></br>
                <div class="alert alert-info">
                    No car matching
                </div>
            </c:otherwise>
        </c:choose>
    </form>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
