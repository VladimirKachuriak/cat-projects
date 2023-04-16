<%--
  Created by IntelliJ IDEA.
  User: vova
  Date: 26.05.2022
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <span class="text-center text-muted">Copyright © Car Rent 2022</span><br>
    <c:choose>
        <c:when test="${cookie['lang'].value == 'uk'}">
            <a href="javascript:setLang('eu')" class="nav-link text-secondary"><span class="text-center text-muted">English version</span></a>
        </c:when>
        <c:otherwise>
            <a href="javascript:setLang('uk')" class="nav-link text-secondary"><span class="text-center text-muted">Українська версія</span></a>
        </c:otherwise>
    </c:choose>
</div>
