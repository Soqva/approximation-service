<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Approximation result</title>
    <style>
        .element {
            display: grid;
            grid-gap: 1rem;
            grid-template-columns: repeat(${requestScope.numberOfPoints}, 3rem);
        }
    </style>
    <style>
        <%@include file="/WEB-INF/css/result-page-styles.css" %>
    </style>
</head>
<body>
<div>
    <div class="center">
        <div class="values">
                <div class="element">
                    <span>X:</span>
                    <c:forEach var="argumentValue" items="${requestScope.lagrangianResultDto.argumentValues}">
                        <span>${argumentValue}</span>
                    </c:forEach>
                </div>
                <br>
                <div class="element">
                    <span>Y:</span>
                    <c:forEach var="functionValue" items="${requestScope.lagrangianResultDto.functionValues}">
                        <span>${functionValue}</span>
                    </c:forEach>
                </div>
        </div>
        <div class="result-elements">
            <span><strong>Lagrangian approximation result</strong> = ${requestScope.lagrangianResultDto.result}</span>
            <span>|</span>
            <span><b>Absolute fault</b> = ${requestScope.lagrangianResultDto.absoluteFault}</span>
            <span><strong>Cubic spline approximation result</strong> = ${requestScope.cubicResultDto.result}</span>
            <span>|</span>
            <span><b>Absolute fault</b> = ${requestScope.cubicResultDto.absoluteFault}</span>
        </div>
        <form class="main-form" action="${pageContext.request.contextPath}/" method="get">
            <div class="back-button">
                <input type="submit" value="back">
            </div>
        </form>
    </div>
</div>
</body>
</html>
