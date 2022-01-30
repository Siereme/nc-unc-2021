<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Directors Page</title>
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
        <c:forEach var="director" items="${directors}" varStatus="loop">
            <tr>
                <th class="align-middle" scope="row">${director.getId()}</th>
                <th class="align-middle" scope="row">${director.getName()}</th>
                <th class="align-middle" scope="row">${director.getYear()}</th>
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
                <td class="align-middle col-1">
                    <form action="handle/page-edit" method="post">
                        <input type="hidden" name="id" value="${director.getId()}"/>
                        <input type="hidden" name="name" value="${director.getName()}"/>
                        <input type="hidden" name="year" value="${director.getYear()}">
                        <input type="submit" class="btn btn-outline-dark" value="Edit"/>
                    </form>
                </td>
                <sec:authorize access="hasRole('ADMIN')">
                    <td class="align-middle col-1">
                        <form action="handle/delete/${director.getId()}" method="post">
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