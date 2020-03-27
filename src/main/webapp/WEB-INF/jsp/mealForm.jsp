<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<html>

<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
     <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <c:if test="${meal.isNew()}"> <spring:message code="meal.create"></spring:message></c:if>
    <c:if test="${!meal.isNew()}"> <spring:message code="meal.edit"></spring:message></c:if>
<%--    <h2><spring:message code="${meal.isNew() ? 'meal.create' : 'meal.edit'}"/></h2>--%>
    <form method="post" action="/meals/save">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>

            <dt><spring:message code="meal.dateTime"/></dt>

            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd><input type="text" value="${meal.description}" size=40 name="description" required></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd><input type="number" value="${meal.calories}" name="calories" required></dd>
        </dl>
        <button type="submit"><spring:message code="button.save"/></button>
        <button onclick="window.history.back()" type="button"><spring:message code="button.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
