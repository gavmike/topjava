<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="javatime" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <title>Title</title>
  </head>
  <body>
  <h2>Meals</h2>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <thead>
        <th>Date</th>
        <th>Description</th>
        <th>Caloria</th>
        </thead>
    </tr>
  <c:forEach var="meals" items="${requestScope.meals}">
  <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${meals.dateTime}" var="date"/>
  <fmt:formatDate value="${date}" pattern="dd.MM.yyyy HH:mm" var="newDate"/>
      <tr>
          <td> <font color= ${meals.excess ? 'red': 'green' }> <c:out value="${(newDate)}"/></font> </td>
          <td> <font color= ${meals.excess ? 'red': 'green' }> <c:out value="${meals.description}"/></font></td>
          <td>  <font color= ${meals.excess ? 'red': 'green' }>  <c:out value="${meals.calories}"/></font></td>

     <form method="get" action="update">
          <input type="hidden"  name="id" value="${meals.id}" />
          <input type="hidden" name="type" value="update">
          <td><input type="submit" name="update" value="update" /><td>
     </form>
          <form method="post" action="">
              <input type="hidden"  name="id" value="${meals.id}" />
              <input type="hidden" name="type" value="delete">
              <td><input type="submit" name="delete" value="delete" /><td>
          </form>
      </tr>
  </c:forEach>
</table>
  <form method="get" action="create">

      <input type="hidden" name="type" value="create">
      <td><input type="submit" name="update" value="create" /><td>
  </form>

  </body>
</html>
