<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Handle Page</title>
    <link href="<c:url value="/resources/bootstrap-5.0.2-dist/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" />"></script>
    <link href="<c:url value="/resources/css/handle.css" />" rel="stylesheet">

</head>
<body>
    <div class="handle-container">
        <h2 class="card-header">${modalTitle}</h2>
        <form action="../${eventType}" method="post">
            <div class="row d-flex justify-content-between mt-2">
                <div class="col-7">
                    <span>Title</span>
                    <input type="hidden" name="id" value="${genre.getId()}"/>
                    <input class="form-control" name="tittle" value="${genre.getTittle()}"/>
                </div>
                <div class="col-4 d-flex justify-content-center align-items-end">
                    <div class="genres">
                      <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Films
                      </button>
                      <ul class="dropdown-menu p-0" aria-labelledby="dropdownMenuButton1">
                        <select class="form-select" multiple aria-label="multiple select example" name="films">
                            <c:forEach var="film" items="${filmGenreList}">
                                <option value="${film.getId()}" selected>${film.getTittle()}</option>
                            </c:forEach>
                            <c:forEach var="film" items="${filmList}">
                                <option value="${film.getId()}">${film.getTittle()}</option>
                            </c:forEach>
                        </select>
                      </ul>
                    </div>
                </div>
            </div>


            <div class="row">
                <input type="submit" class="submit-button btn btn-primary" value="${modalTitle}" />
            </div>
        </form>
    </div>

</body>
</html>