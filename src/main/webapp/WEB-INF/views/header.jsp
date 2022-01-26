<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<form id="find" action="find" method="post"></form>
<form id="findAll" action="all" method="get"></form>
<form id="add" action="handle/page-add" method="post"></form>
<%
    String classList = "";
    String path = "";
%>
<header>
    <div class="px-3 py-2 bg-dark text-white">
      <div class="container mw-100 p-0">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-end">
          <ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
            <li>
                <%
                   classList = "nav-link";
                   path = (String) request.getAttribute("javax.servlet.forward.request_uri");
                   if (path.equals("/films/all")) {
                     classList += " text-secondary";
                   }
                   else{
                     classList += " text-white";
                   }
                %>
              <a href="http://localhost:8080/films/all" class="<%=classList%>">
                Films
              </a>
            </li>
            <li>
                <%
                   classList = "nav-link";
                   path = (String) request.getAttribute("javax.servlet.forward.request_uri");
                   if (path.equals("/genres/all")) {
                     classList += " text-secondary";
                   }
                   else{
                     classList += " text-white";
                   }
                %>
              <a href="http://localhost:8080/genres/all" class="<%=classList%>">
                Genres
              </a>
            </li>
            <li>
                <%
                   classList = "nav-link";
                   path = (String) request.getAttribute("javax.servlet.forward.request_uri");
                   if (path.equals("/actors/all")) {
                     classList += " text-secondary";
                   }
                   else{
                     classList += " text-white";
                   }
                %>
              <a href="http://localhost:8080/actors/all" class="<%=classList%>">
                Actors
              </a>
            </li>
            <li>
                <%
                   classList = "nav-link";
                   path = (String) request.getAttribute("javax.servlet.forward.request_uri");
                   if (path.equals("/directors/all")) {
                     classList += " text-secondary";
                   }
                   else{
                     classList += " text-white";
                   }
                %>
              <a href="http://localhost:8080/directors/all" class="<%=classList%>">
                Directors
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
    <div class="px-3 py-2 border-bottom mb-3">
      <div class="container d-flex justify-content-between mw-100 w-100">
        <form class="d-flex col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
            <input class="form-control col-1" name="tittle" form="find" placeholder="Search..."/>
            <input type="submit" class="btn btn-outline-dark  ms-2" name="find" value="Search" form="find"  />
            <input type="submit" class="btn btn-outline-dark  ms-2" name="find" value="Search All" form="findAll"  />
            <input type="submit" class="btn btn-outline-dark  ms-2" value="Add" form="add" />
        </form>

        <div class="text-end">
            <sec:authorize access="!isAuthenticated()">
                <button onclick="window.location.href = '/login'" type="button" class="btn btn-light text-dark me-2">Log in</button>
                <button onclick="window.location.href = '/registration'" type="button" class="btn btn-primary">Sign-up</button>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <button onclick="window.location.href = '/logout'" type="button" class="btn btn-light text-dark-me-2">Log out</button>
                <sec:authorize access="hasRole('ADMIN')">
                    <button onclick="window.location.href = '/admin'" type="button" class="btn btn-light text-dark-me-2">Users</button>
                </sec:authorize>
            </sec:authorize>
        </div>
      </div>
    </div>
  </header>