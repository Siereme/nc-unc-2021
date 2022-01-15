<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Handle Page</title>
    <link href="<c:url value="/resources/css/film-handle.css" />" rel="stylesheet">
</head>
<body>
    <h2>Handle</h2>
    <div class="modal">
        <h2>${modalTitle}<h2>
        <form action="add" method="post">
            <p>
                <span>Title<span>
                <input name="tittle" />
            </p>
        </form>
    </div>
</body>
</html>