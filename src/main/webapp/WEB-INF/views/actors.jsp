<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Actors Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="<c:url value="/src/main/webapp/resources/css/style.css" />" rel="stylesheet">
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
            <th scope="col">Films</th>
            <th scope="col" colspan="2">Handle</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="actor" items="${actors}" varStatus="loop">
            <tr>
                <th class="align-middle" scope="row">${actor.getId()}</th>
                <th class="align-middle" scope="row">${actor.getName()}</th>
                <th class="align-middle" scope="row">${actor.getYear()}</th>
                <th class="px-0">
                    <table class="table m-0">
                        <tbody>
                        <c:forEach var="film" items="${films.get(loop.index)}">
                            <tr>
                                <td>${film.getTittle()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </th>
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

</body>