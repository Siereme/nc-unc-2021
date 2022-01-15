<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Films Page</title>
    <link href="<c:url value="/resources/css/films.css" />" rel="stylesheet">
</head>
<body>
    <h2>${title}</h2>

    <div class="search-container">
        <form id="find" action="find" method="post"></form>
        <form id="findAll" action="all" method="get"></form>
        <form id="handle" action="/films/film-handle" method="get"></form>
        <input name="tittle" form="find"/>
        <p>
            <input type="submit" name="find" value="Find" form="find"  />
            <input type="submit" name="find" value="Find All" form="findAll"  />
            <input type="submit" value="Add" form="handle" />
            <input type="submit" value="Edit" form="handle" />
        </p>
    </div>

    <div class="films-grid-container">
        <ul class="films-grid">
            <li class="header">
                    <div>Id</div>
                    <div>Title</div>
                    <div>Date</div>
            </li>
            <c:forEach var="film" items="${films}">
                    <li class="body">
                        <div>${film.getId()}</div>
                        <div>${film.getTittle()}</div>
                        <div>${film.getDate()}</div>
                    </li>
            </c:forEach>
        </ul>
    </div>


</body>
</html>