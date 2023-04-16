<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 22.05.2022
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="language"/>
<html>
<head lang="${cookie['lang'].value}">
    <script src="js/lang.js"></script>
    <title><fmt:message key="label.userList" /></title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <header class="masthead mb-auto">
        <div class="inner">
            <h3 class="masthead-brand"><fmt:message key="label.navigation"/></h3>
            <nav class="nav nav-masthead justify-content-center">
                <a class="nav-link" href="car"><fmt:message key="label.cars"/></a>
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
            <c:remove var="message" scope="session"/>
        </div>
    </c:if>
    <form action="users" method="post" id="userForm" role="form">
        <input type="hidden" id="idUser" name="idUser">
        <input type="hidden" id="action" name="action">
        <c:choose>
            <c:when test="${not empty userList}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td><fmt:message key="label.login" /></td>
                        <td><fmt:message key="label.firstname" /></td>
                        <td><fmt:message key="label.lastname" /></td>
                        <td><fmt:message key="label.phonenumber" /></td>
                        <td><fmt:message key="label.email" /></td>
                        <td><fmt:message key="label.userRole" /></td>
                        <td><fmt:message key="label.userStatus" /></td>
                    </tr>
                    </thead>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.login}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.phoneNumber}</td>
                            <td>${user.email}</td>
                            <td>${user.role}</td>
                            <td>${user.status}</td>

                                <td><a href="#" id="changeStatus"
                                       onclick="document.getElementById('action').value = 'changeStatus';
                                               document.getElementById('idUser').value = '${user.id}';
                                               document.getElementById('userForm').submit();">
                                    <span class="glyphicon glyphicon-off"></span>
                                </a>
                                </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <br></br>
                <div class="alert alert-info">
                    <fmt:message key="label.warning.noUserMatching" />
                </div>
            </c:otherwise>
        </c:choose>
    </form>
    <form action="new_admin.jsp" method="post">
        <br/>
        <button type="submit" class="btn btn-primary  btn-md"><fmt:message key="title.createAdmin" /></button>
    </form>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
