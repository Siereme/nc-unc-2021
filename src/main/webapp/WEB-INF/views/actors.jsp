<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Actors Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="<c:url value="/resources/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" />"></script>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="films-grid-container">
    <table class="table text-center">
        <thead class="bg-light">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Age</th>
            <th scope="col">
                <div class="d-flex justify-content-center">Films</div>
                <div class="row">
                    <div class="col">Title</div>
                    <div class="col">Date</div>
                </div>
            </th>
            <th scope="col" colspan="2">Handle</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="actor" items="${actors}" varStatus="loop">
            <tr>
                <th class="align-middle" scope="row">${actor.getId()}</th>
                <td class="align-middle">${actor.getName()}</td>
                <td class="align-middle">${actor.getYear()}</td>
                <td class="px-0">
                    <table class="table">
                        <tbody>
                        <c:forEach var="film" items="${films.get(loop.index)}">
                            <tr>
                                <td class="w-50">${film.getTittle()}</td>
                                <td class="w-50">${film.getDate()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>

                <td class="align-middle col-1">
                    <form action="handle/page-edit" method="post">
                        <input type="hidden" name="id" value="${actor.getId()}"/>
                        <input type="hidden" name="name" value="${actor.getName()}"/>
                        <input type="hidden" name="year" value="${actor.getYear()}">
                        <input type="submit" class="btn btn-outline-dark" value="Edit"/>
                    </form>
                </td>
                <sec:authorize access="hasRole('ADMIN')">
                    <td class="align-middle col-1">
                        <form action="handle/delete/${actor.getId()}" method="post">
                            <input type="submit" class="btn btn-outline-dark" value="Delete"/>
                        </form>
                    </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${errors != null && !errors.isEmpty()}">
    <c:set var="title" value="Errors" scope="request"/>
    <c:set var="messages" value="${errors}" scope="request"/>
    <jsp:include page="modal-messages.jsp" />
</c:if>
<c:if test="${success != null && !success.isEmpty()}">
    <c:set var="title" value="Success" scope="request"/>
    <c:set var="messages" value="${success}" scope="request"/>
    <jsp:include page="modal-messages.jsp" />
</c:if>
</body>
</html>