<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<title>Films Page</title>
<link href="<c:url value="/resources/css/films.css" />" rel="stylesheet">
<c:url var="testAction" value="/find"/>
</head>
<body>
    <h2>${title}</h2>

    <div class="search-container">
        <form method="post">
            <input name="tittle" />
            <p><input type="submit" value="Find"  /></p>
        </form>
    </div>

    <div class="films-grid-container">
        <ul class="films-grid">
            <li class="header">
                    <div class="header">Id</div>
                    <div class="header">Title</div>
                    <div class="header">Date</div>
            </li>
            <c:forEach var="film" items="${films}">
                    <li class="body">
                        <div class="cell">${film.getId()}</div>
                        <div class="cell">${film.getTittle()}</div>
                        <div class="cell">${film.getDate()}</div>
                    </li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>