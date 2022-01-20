<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Handle Page</title>
    <link href="<c:url value="/src/main/webapp/resources/css/film-handle.css" />" rel="stylesheet">
</head>
<body>
    <div class="modal">
        <h2 class="header-title">${modalTitle}</h2>
        <div class="form-container">
            <form action="../${eventType}" method="post">
                <p>
                    <span>Title</span>
                    <input type="hidden" name="id" value="${film.getId()}"/>
                    <input name="tittle" value="${film.getTittle()}"/>
                </p>

                <input type="submit" class="submit-button" value="${modalTitle}" />
            </form>
        </div>
    </div>
</body>
</html>