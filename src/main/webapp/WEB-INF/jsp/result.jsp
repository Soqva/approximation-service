<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Approximation result</title>
    <style>
        .element {
            display: grid;
            grid-gap: 1rem;
            grid-template-columns: repeat(${requestScope.n}, 3rem);
        }
    </style>
    <style><%@include file="/WEB-INF/css/result-page.css"%></style>
</head>
<body>
<c:if test="${requestScope.lagrangianResult != null and requestScope.cubicResult != null}">
    <div>
        <div class="center">
            <div class="values">
                <div class="element">
                    <span>X:</span>
                    <c:forEach var="xValue" items="${requestScope.xValues}">
                        <span>${xValue}</span>
                    </c:forEach>
                </div>
                <br>
                <div class="element">
                    <span>Y:</span>
                    <c:forEach var="yValue" items="${requestScope.yValues}">
                        <span>${yValue}</span>
                    </c:forEach>
                </div>
            </div>
            <div class="result-elements">
                <div>
                    <span><strong>Lagrangian approximation result</strong> = ${requestScope.lagrangianResult} |</span>
                    <span><b>Absolute fault</b> = ${requestScope.lagrangianAbsoluteFault}</span>
                </div>
                <div>
                    <span><strong>Cubic spline approximation result</strong> = ${requestScope.cubicResult} |</span>
                    <span><b>Absolute fault</b> = ${requestScope.cubicAbsoluteFault}</span>
                </div>
            </div>
        </div>
    </div>
</c:if>
</body>
</html>
