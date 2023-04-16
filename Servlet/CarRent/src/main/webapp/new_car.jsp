<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 22.05.2022
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="language"/>
<html lang="${cookie['lang'].value}">
<head >
    <title><fmt:message key="title.newCar" /></title>
    <script src="js/lang.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <form action="car" method="post"  role="form" data-toggle="validator" >
        <c:if test ="${empty action}">
            <c:set var="action" value="add"/>
        </c:if>
        <input type="hidden" id="action" name="action" value="${action}">
        <input type="hidden" id="idCar" name="idCar" value="${car.id}">
        <h2><fmt:message key="title.newCar"/></h2>
        <div class="form-group col-xs-4">
            <label for="brand" class="control-label col-xs-4"><fmt:message key="label.brand" />:</label>
            <input type="text" name="brand" id="brand" class="form-control" value="${car.brand}" required="true"/>

            <label for="model" class="control-label col-xs-4"><fmt:message key="label.model" />:</label>
            <input type="text" name="model" id="model" class="form-control" value="${car.model}" required="true"/>

            <label for="date" class="control-label col-xs-4"><fmt:message key="label.releaseDate" /></label>
            <input type="text"  pattern="^\d{4}-\d{2}-\d{2}$" name="date" id="date" class="form-control" value="${car.releaseDate}" maxlength="10" placeholder="yyyy-MM-dd" required="true"/>

            <label for="price" class="control-label col-xs-4"><fmt:message key="label.carPrice" />:</label>
            <input type="number" name="price" id="price" class="form-control" value="${car.price}" min="0" required="true" oninput="validity.valid||(value='')"/>

            <label for="auto_class" class="control-label col-xs-4"><fmt:message key="label.carClass" />:</label>
            <select id="auto_class" name = "auto_class" class="form-control" required="true" value="${car.autoClass}">
            <option>A</option>
            <option>B</option>
            <option>C</option>
            </select>

            <br></br>
            <button type="submit" class="btn btn-primary  btn-md"><fmt:message key="button.accept" /></button>
        </div>
    </form>
</div>
<%@include file="footer.jsp" %>
</body>
</html>