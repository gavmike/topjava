<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="javatime" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2> update meals</h2>

<div> ID : <c:out value="${requestScope.meal.id}" /> </div>
<div> Date: <c:out value="${requestScope.meal.dateTime}" /> </div>
<div> calories : <c:out value="${requestScope.meal.calories}" /> </div>

<form method="post" action="meals" >
    <input type="hidden" name="type" value="edit">
    <label> Дата  <input type="datetime-local" value="${meal.dateTime}" name="dateTime" ></label>
    <label> Описание <input type="text" name="description" value="${meal.description}"></label>
    <label> Калории <input type="number" name="calories" value="${meal.calories}"></label>calories<br>
    <input type="number" hidden name="id" value="${meal.id}" > <br>
    <input type="submit" value="ok" name="add" > <br>
</form>
</body>
</html>
