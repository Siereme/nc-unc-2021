<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Films Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="<c:url value="/resources/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" />"></script>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="json" value="${json}"/>
</jsp:include>

<div class="films-grid-container">
    <table class="table text-center">
        <thead class="bg-light">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Title</th>
            <th scope="col">Date</th>
            <th scope="col">Genres</th>
            <th scope="col">
                <div class="d-flex justify-content-center">Actors</div>
                <div class="row">
                    <div class="col">Name</div>
                    <div class="col">Year</div>
                </div>
            </th>
            <th scope="d-flex justify-content-center">
                <div class="d-flex justify-content-center">Directors</div>
                <div class="row">
                    <div class="col">Name</div>
                    <div class="col">Year</div>
                </div>
            </th>
            <th scope="col" colspan="2">Handle</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="film" items="${films}" varStatus="loop">
            <tr>
                <th class="align-middle" scope="row">${film.getId()}</th>
                <td class="align-middle">${film.getTittle()}</td>
                <td class="align-middle">${film.getDate()}</td>
                <td class="px-0">
                    <table class="table m-0">
                        <tbody>
                        <c:forEach var="genre" items="${genres.get(loop.index)}">
                            <tr>
                                <td>${genre.getTittle()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
                <td class="px-0">
                    <table class="table">
                        <tbody>
                        <c:forEach var="actor" items="${actors.get(loop.index)}">
                            <tr>
                                <td class="w-50">${actor.getName()}</td>
                                <td class="w-50">${actor.getYear()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
                <td class="px-0">
                    <table class="table">
                        <tbody>
                        <c:forEach var="director" items="${directors.get(loop.index)}">
                            <tr>
                                <td class="w-50">${director.getName()}</td>
                                <td class="w-50">${director.getYear()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
                <td class="align-middle col-1">
                    <form action="handle/page-edit" method="post">
                        <input type="hidden" name="id" value="${film.getId()}"/>
                        <input type="hidden" name="tittle" value="${film.getTittle()}"/>
                        <input type="hidden" name="data" value="${film.getDate()}">
                        <input type="submit" class="btn btn-outline-dark" value="Edit"/>
                    </form>
                </td>
                <sec:authorize access="hasRole('ADMIN')">
                    <td class="align-middle col-1">
                        <form action="handle/delete/${film.getId()}" method="post">
                            <input type="submit" class="btn btn-outline-dark" value="Delete"/>
                        </form>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${serializeErrors}">
    <jsp:include page="errors-modal.jsp">
        <jsp:param name="errors" value="${serializeErrors}"/>
    </jsp:include>
</c:if>

</body>
</html>