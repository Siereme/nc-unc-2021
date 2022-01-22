<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Films Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/films.css" />" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp" />
    <div class="films-grid-container">
        <h2>Genres</h2>
        <table class="table">
          <thead class="bg-dark text-light">
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Title</th>
              <th scope="col">Date</th>
              <th scope="col">Genres</th>
              <th scope="col">Actors</th>
              <th scope="col">Directors</th>
              <th scope="col" colspan="2">Handle</th>
            </tr>
          </thead>
          <tbody>
              <c:forEach var="film" items="${films}">
                <tr>
                  <th scope="row">${film.getId()}</th>
                  <td class="simple-data">${film.getTittle()}</td>
                  <td class="simple-data">${film.getDate()}</td>
                  <td class="table-data">
                    <table class="table">
                        <tbody>
                            <c:forEach var="genre" items="${genres.get(film.getId())}">
                                <tr>
                                    <td>${genre.getTittle()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                  </td>
                  <td class="table-data">
                    <table class="table">
                        <tbody>
                            <c:forEach var="actor" items="${actors.get(film.getId())}">
                                <tr>
                                    <td>${actor.getName()}</td>
                                    <td>${actor.getYear()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                  </td>
                  <td class="table-data">
                    <table class="table">
                        <tbody>
                            <c:forEach var="director" items="${directors.get(film.getId())}">
                                <tr>
                                    <td>${director.getName()}</td>
                                    <td>${director.getYear()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                  </td>
                  <td class="simple-data">
                      <form action="handle/page-edit" method="post">
                          <input type="hidden" name="id" value="${film.getId()}" />
                          <input type="hidden" name="tittle" value="${film.getTittle()}" />
                          <input type="submit" value="Edit" />
                      </form>
                  </td>
                  <td class="simple-data">
                      <form action="handle/delete/${film.getId()}" method="post">
                          <input type="submit" value="Delete" />
                      </form>
                  </td>
                </tr>
              </c:forEach>
          </tbody>
        </table>
    </div>
</body>
</html>