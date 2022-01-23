<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Films Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="films-grid-container">
        <table class="table text-center">
          <thead class="bg-light">
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Title</th>
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
              <c:forEach var="genre" items="${genres}" varStatus="loop">
                <tr>
                  <th class="align-middle" scope="row">${genre.getId()}</th>
                  <td class="align-middle">${genre.getTittle()}</td>
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
                          <input type="hidden" name="id" value="${genre.getId()}" />
                          <input type="hidden" name="tittle" value="${genre.getTittle()}" />
                          <input type="submit" class="btn btn-outline-dark" value="Edit" />
                      </form>
                  </td>
                  <td class="align-middle col-1">
                      <form action="handle/delete/${genre.getId()}" method="post">
                          <input type="submit" class="btn btn-outline-dark" value="Delete" />
                      </form>
                  </td>
                </tr>
              </c:forEach>
          </tbody>
        </table>
    </div>
</body>
</html>