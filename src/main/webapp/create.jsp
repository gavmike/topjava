
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2> создание новой еды</h2>
<form method="post" action="meals" >
    <label> <input type="datetime-local" value="${meal.getDateTime()}" name="dateTime" ></label>Date<br>
    <label> <input type="text" name="description"></label>description<br>
    <label> <input type="number" name="calories"></label>calories<br>
    <input type="hidden" name="type" value="add">
    <input type="submit" value="ok" name="add1" > <br>
</form>
</body>
</html>
