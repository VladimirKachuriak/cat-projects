<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 21.05.2022
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<script src="js/lang.js"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="language"/>
<%@ taglib prefix="tag" uri="myTag" %>
<html>
<head lang="${cookie['lang'].value}">
    <script src="js/lang.js"></script>
    <title><fmt:message key="title.catalog"/></title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="cover-container d-flex h-100 p-3 mx-auto flex-column">
    <header class="masthead mb-auto">
        <div class="inner">
            <h3 class="masthead-brand"><fmt:message key="label.navigation"/></h3>
            <c:choose>
                <c:when test="${sessionScope.role == 'USER'}">
                    <nav class="nav nav-masthead justify-content-center">
                        <a class="nav-link" href="userOrders"><fmt:message key="label.orders"/></a>
                        <a class="nav-link" href="logout"><fmt:message key="label.logout"/></a>
                    </nav>
                </c:when>
                <c:when test="${sessionScope.role == 'ADMIN'}">
                    <nav class="nav nav-masthead justify-content-center">
                        <a class="nav-link" href="adminOrders"><fmt:message key="label.orders"/></a>
                        <a class="nav-link" href="logout"><fmt:message key="label.logout"/></a>
                        <a class="nav-link" href="users"><fmt:message key="label.users"/></a>
                        <a class="nav-link" href="car"><fmt:message key="label.cars"/></a>
                    </nav>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="login"><fmt:message key="label.login"/></a>
                </c:otherwise>
            </c:choose>
        </div>
    </header>
</div>
<h2><fmt:message key="title.catalog"/>!</h2>
<div class="container">
<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-success">
        <fmt:message key="${sessionScope.message}"/>
        <c:remove var="message" scope="session"/>
    </div>
</c:if>
</div>
<div class="container">
<form action="catalog" method="get" id="searchForm" role="form">
    <label for="brand" class="control-label col-xs-4"><fmt:message key="label.autoClass"/>:</label>
    <select id="brand" name="brand" class="form-control" required="false">
        <option value="all"><fmt:message key="label.allCars"/></option>
        <c:forEach var="brand" items="${brands}">
            <option>${brand}</option>
        </c:forEach>
    </select>
    <br><br>
    <fmt:message key="label.sort.sortBy"/>: <input type="radio" name="sort" value="model" /><fmt:message key="label.sort.model"/>
    <input type="radio" name="sort" value="price" /><fmt:message key="label.sort.price"/>

    <label for="order" class="control-label col-xs-4"><fmt:message key="label.sort.order"/>:</label>
    <select id="order" name="order" class="form-control" required="false">
        <option value="DESC"><fmt:message key="label.sort.DESC"/></option>
        <option value="ASC"><fmt:message key="label.sort.ASC"/></option>
    </select>

    <label for="rate" class="control-label col-xs-4"><fmt:message key="label.sort.rate"/>:</label>
    <select id="rate" name="rate" class="form-control"  required="false">
        <option value="all"><fmt:message key="label.allCars"/></option>
        <option value="A">A</option>
        <option value="B">B</option>
        <option value="C">C</option>
    </select>
    <br><br>
    <button type="submit" class="btn btn-primary  btn-md"><fmt:message key="label.search"/></button>
</form>
</div>
<div class="container">
    <form action="catalog" method="post" id="carForm" role="form">
        <input type="hidden" id="idCar" name="idCar">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty carList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="label.brand"/></td>
                        <td><fmt:message key="label.model"/></td>
                        <td><fmt:message key="label.releaseDate"/></td>
                        <td><fmt:message key="label.carState"/></td>
                        <td><fmt:message key="label.carClass"/></td>
                        <td><fmt:message key="label.carPrice"/></td>
                        <td><fmt:message key="label.makeOrder"/></td>
                    </tr>
                    </thead>
                    <c:forEach var="car" items="${carList}">
                        <tr>
                            <td>${car.id}</td>
                            <td>${car.brand}</td>
                            <td>${car.model}</td>
                            <td>${car.releaseDate}</td>
                            <td><fmt:message key="${car.state}"/></td>
                            <td>${car.autoClass}</td>
                          <%--  <td>${car.price}</td>--%>
                            <td> <tag:myownTag lang="${cookie['lang'].value}" price="${car.price}"/></td>
                            <c:if test= "${car.state == 'AVAIL'}">
                            <td><a href="#" id="makeOrder"
                                   onclick="document.getElementById('action').value = 'makeOrder';
                                           document.getElementById('idCar').value = '${car.id}';
                                           document.getElementById('carForm').submit();">
                                <span class="glyphicon glyphicon-plus"></span>
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
                    <fmt:message key="label.warning.noCarMatching"/>
                </div>
            </c:otherwise>
        </c:choose>
    </form>

<c:if test="${currentPage != 1}">
    <td><a href="?${pageContext.request.queryString.split("&page")[0]}&page=${currentPage - 1}"><fmt:message key="label.prev"/></a></td>
</c:if>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="?${pageContext.request.queryString.split("&page")[0]}&page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

<c:if test="${currentPage lt noOfPages}">
    <td><a href="?${pageContext.request.queryString.split("&page")[0]}&page=${currentPage + 1}"><fmt:message key="label.next"/></a></td>
</c:if>
<%@include file="footer.jsp" %>
</div>
</body>
</html>
