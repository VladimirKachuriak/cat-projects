<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 23.05.2022
  Time: 18:27
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
    <title><fmt:message key="label.makeOrder"/></title>
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
    <form action="catalog" method="post" role="form" data-toggle="validator">
        <input type="hidden" id="action" name="action" value="createOrder">
        <input type="hidden" id="idCar" name="idCar" value="${idCar}">
        <h2><fmt:message key="label.welcome"/></h2>
        <div class="form-group col-xs-4">
            <label for="serial" class="control-label col-xs-4"><fmt:message key="label.order.passportSerial"/>:</label>
            <input type="text" name="serial" id="serial" class="form-control" placeholder="AA1234" value="${serialNumber}" required="true"/>

            <label for="expire" class="control-label col-xs-4"><fmt:message key="label.order.passportEndDate"/></label>
            <input type="text" pattern="^\d{4}-\d{2}-\d{2}$" name="expire" id="expire" class="form-control"
                   value="${expireDate}" maxlength="10" placeholder="yyyy-MM-dd" required="true"/>

            <label for="startDate" class="control-label col-xs-4"><fmt:message key="label.order.startDate"/></label>
            <input type="text" pattern="^\d{4}-\d{2}-\d{2}$" name="startDate" id="startDate" class="form-control"
                   value="${startDate}" maxlength="10" placeholder="yyyy-MM-dd" required="true"/>

            <label for="endDate" class="control-label col-xs-4"><fmt:message key="label.order.endDate"/></label>
            <input type="text" pattern="^\d{4}-\d{2}-\d{2}$" name="endDate" id="endDate" class="form-control"
                   value="${endDate}" maxlength="10" placeholder="yyyy-MM-dd" required="true"/>

            <label for="check" class="control-label col-xs-4"><fmt:message key="label.order.withDriver"/>?</label>
            <input type="checkbox" name="check" id="check" value="true" checked/>

            <br></br>
            <button type="submit" class="btn btn-primary  btn-md"><fmt:message key="button.accept"/></button>
        </div>
    </form>
    <%@include file="footer.jsp" %>
</body>
</html>
