<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Approximation</title>
    <style><%@include file="/WEB-INF/css/main-page.css"%></style>
</head>
<body>
    <div class="center">
        <h1>Approximation service</h1>
        <form class="main-form" action="${pageContext.request.contextPath}/app" method="post">
            <div class="inputbox">
                <input type="text" name="leftBorder" id="leftBorderId">
                <span>Left border</span>
            </div>
            <div class="inputbox">
                <input type="text" name="rightBorder" id="rightBorderId">
                <span>Right border</span>
            </div>
            <div class="inputbox">
                <input type="text" name="n" id="nId">
                <span>Number of parts</span>
            </div>
            <div class="inputbox">
                <input type="text" name="interpolationPoint" id="interpolationPointId">
                <span>Interpolation point</span>
            </div>
            <div class="inputbox">
                <input type="text" name="function" id="functionId">
                <span>Function</span>
            </div>
            <div class="inputbox">
                <input type="submit" value="submit">
            </div>
        </form>
    </div>
</body>
</html>
