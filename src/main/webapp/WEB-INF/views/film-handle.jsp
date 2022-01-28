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
        <form action="${eventType}" method="post">
            <div class="row">
                <div class="col-6">
                    <span>Title</span>
                    <input type="hidden" name="id" value="${film.getId()}"/>
                    <input class="form-control" name="tittle" value="${film.getTittle()}"/>
                </div>
                <div class="col-6">
                    <label for="startDate">Date</label>
                    <input id="startDate" class="form-control" name="date" type="date" value="${film.getDate()}" />
                </div>
            </div>

            <div class="row">
                <div class="col-4">
                    <div class="genres">
                      <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Genres
                      </button>
                      <ul class="dropdown-menu p-0" aria-labelledby="dropdownMenuButton1">
                        <select class="form-select" multiple aria-label="multiple select example" name="genres">
                            <c:forEach var="genre" items="${genreFilmList}">
                                <option value="${genre.getId()}" selected>${genre.getTittle()}</option>
                            </c:forEach>
                            <c:forEach var="genre" items="${genreList}">
                                <option value="${genre.getId()}">${genre.getTittle()}</option>
                            </c:forEach>
                        </select>
                      </ul>
                    </div>
                </div>

                <div class="col-4">
                    <div class="actors">
                      <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Actors
                      </button>
                      <ul class="dropdown-menu p-0" aria-labelledby="dropdownMenuButton1">
                        <select class="form-select" multiple aria-label="multiple select example" name="actors">
                            <c:forEach var="actor" items="${actorFilmList}">
                                <option value="${actor.getId()}" selected>${actor.getName()}</option>
                            </c:forEach>
                            <c:forEach var="actor" items="${actorList}">
                                <option value="${actor.getId()}">${actor.getName()}</option>
                            </c:forEach>
                        </select>
                      </ul>
                    </div>
                </div>

                <div class="col-4">
                    <div class="directors">
                      <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Directors
                      </button>
                      <ul class="dropdown-menu p-0" aria-labelledby="dropdownMenuButton1">
                        <select class="form-select" multiple aria-label="multiple select example" name="directors">
                            <c:forEach var="director" items="${directorFilmList}">
                                <option value="${director.getId()}" selected>${director.getName()}</option>
                            </c:forEach>
                            <c:forEach var="director" items="${directorList}">
                                <option value="${director.getId()}">${director.getName()}</option>
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