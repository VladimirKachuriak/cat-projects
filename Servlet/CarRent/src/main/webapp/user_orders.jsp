<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 24.05.2022
  Time: 18:03
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
    <title><fmt:message key="title.userOrder"/></title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <header class="masthead mb-auto">
        <div class="inner">
            <h3 class="masthead-brand"><fmt:message key="label.navigation" /></h3>
            <nav class="nav nav-masthead justify-content-center">
                <a class="nav-link" href="catalog">catalog</a>
                <a class="nav-link" href="logout">logout</a>
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
    <h1><fmt:message key="label.login"/>: ${profile.login}</h1>
    <h1><fmt:message key="label.account"/>: ${profile.account}</h1>
    <form action="userOrders" method="post" id="topUp" role="form">
        <input type="hidden" name="action" value="topUpMoney">
        <label for="money" class="control-label col-xs-4"><fmt:message key="label.order.topUp"/></label>
        <input type="number" min="0" name="money" id="money" class="form-control" oninput="validity.valid||(value='')"/>
        <button type="submit" class="btn btn-primary  btn-md"><fmt:message key="button.accept" /></button>
    </form>
    <form action="userOrders" method="post" id="ordersForm" role="form">
        <input type="hidden" id="idOrder" name="idOrder">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty orderList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="label.order.carId"/></td>
                        <td><fmt:message key="label.order.startDate"/></td>
                        <td><fmt:message key="label.order.endDate"/></td>
                        <td><fmt:message key="label.order.state"/></td>
                        <td><fmt:message key="label.order.account"/></td>
                        <td><fmt:message key="label.order.accountDamage"/></td>
                        <td><fmt:message key="label.order.message"/></td>
                    </tr>
                    </thead>
                    <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td>${order.id}</td>
                            <td>${order.idCar}</td>
                            <td>${order.start_date}</td>
                            <td>${order.end_date}</td>
                            <td><fmt:message key="${order.state}"/></td>
                            <td>${order.account}</td>
                            <td>${order.accountDamage}</td>
                            <td>${order.message}</td>
                            <td><a href="#" id="pay"
                                   onclick="document.getElementById('action').value = 'pay';
                                           document.getElementById('idOrder').value = '${order.id}';
                                           document.getElementById('ordersForm').submit();">
                                <span class="glyphicon glyphicon-usd"></span>
                            </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br></br>
                <div class="alert alert-info">
                    <fmt:message key="label.warning.noOrderMatching"/>
                </div>
            </c:otherwise>
        </c:choose>
    </form>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
