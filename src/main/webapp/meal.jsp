
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="javatime" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <title>Title</title>
      <style>
          excess {
              color: brown;
          }
          normal {
              color: black;
          }
      </style>
  </head>
  <body>
  <h2>Meals</h2>



  <c:forEach var="meals" items="${requestScope.meals}">
  <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${meals.dateTime}" var="date"/>
  <fmt:formatDate value="${date}" pattern="dd.MM.yyyy HH:mm" var="newDate"/>

 <font color= ${meals.excess ? 'red': 'green' }>

    <c:out value="${(newDate)}"/>
      <c:out value="${meals.description}"/>
      <c:out value="${meals.calories}"/>
        <form method="get" action="update">
          <input type="hidden"  name="id" value="${meals.id}" />
          <input type="hidden" name="type" value="update">
          <input type="submit" name="update" value="update" />

        </form>
              <form method="post" action="">
                  <input type="hidden"  name="id" value="${meals.id}" />
                  <input type="hidden" name="type" value="delete">
                  <input type="submit" name="delete" value="delete" />
              </form>

    <li></li>
  </c:forEach>

              <h2> создание новой еды</h2>
              <form method="post" action="" >
                  <label> <input type="datetime-local" value="${meal.getDateTime()}" name="dateTime" ></label>Date<br>
                  <label> <input type="text" name="description"></label>description<br>
                  <label> <input type="number" name="calories"></label>calories<br>
                  <input type="hidden" name="type" value="add">
                  <input type="submit" value="ok1" name="add1" > <br>
              </form>
  </body>
</html>
