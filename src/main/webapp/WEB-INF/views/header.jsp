<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<form id="find" action="find" method="post"></form>
<form id="findAll" action="all" method="get"></form>
<form id="add" action="handle/page-add" method="post"></form>
<form id="import" action="${json}/import" method="post" enctype="multipart/form-data"></form>

<header>
    <div class="px-3 pe-4 py-2 bg-dark text-white">
        <div class="container mw-100 p-0">
            <div class="d-flex flex-wrap align-items-center justify-content-between">
                <ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
                    <li>
                        <a href="/films/all" class="nav-link ${currentPage.contains('/films/all') ? ' text-secondary' : ' text-white'}">
                            Films
                        </a>
                    </li>
                    <li>
                        <a href="/genres/all" class="nav-link ${currentPage.contains('/genres/all') ? 'text-secondary' : 'text-white'}">
                            Genres
                        </a>
                    </li>
                    <li>
                        <a href="/actors/all" class="nav-link ${currentPage.contains('/actors/all') ? ' text-secondary' : ' text-white'}">
                            Actors
                        </a>
                    </li>
                    <li>
                        <a href="/directors/all"
                           class="nav-link ${currentPage.contains('/directors/all') ? ' text-secondary' : ' text-white'}">
                            Directors
                        </a>
                    </li>
                </ul>
                <ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
                    <sec:authorize access="!isAuthenticated()">
                        <li>
                            <button onclick="window.location.href = '/login'" type="button" class="btn btn-primary text-dark-me-2 me-2">Log
                                in
                            </button>
                        </li>
                        <li>
                            <button onclick="window.location.href = '/registration'" type="button" class="btn btn-primary">Sign-up</button>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <li>
                            <button onclick="window.location.href = '/logout'" type="button" class="btn btn-primary text-dark-me-2 me-2">Log
                                out
                            </button>
                        </li>
                        <sec:authorize access="hasRole('ADMIN')">
                            <li>
                                <button onclick="window.location.href = '/admin/all'" type="button" class="btn btn-light text-dark-me-2">Users
                                </button>
                            </li>
                        </sec:authorize>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </div>
    <div class="px-3 py-2 border-bottom mb-3">
        <div class="container d-flex justify-content-between mw-100 w-100">
            <form class="d-flex col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto">
                <input class="form-control col-1" name="tittle" form="find" placeholder="Search..."/>
                <input type="submit" class="btn btn-outline-dark  ms-2" name="find" value="Search" form="find"/>
                <input type="submit" class="btn btn-outline-dark  ms-2" name="find" value="Search All" form="findAll"/>
                <input type="submit" class="btn btn-outline-dark  ms-2" value="Add" form="add"/>
            </form>

            <div class="text-end d-flex align-items-center">
                <sec:authorize access="isAuthenticated()">
                    <sec:authorize access="hasRole('ADMIN')">
                        <a href="${json}/export" download class="btn btn-outline-dark text-decoration-none">Export json file</a>
                        <div class="d-flex ms-3">
                            <input class="btn btn-outline-dark me-2" value="Import json file" type="submit" form="import"/>
                            <input class="form-control" type="file" id="formFile" name="file" form="import">
                        </div>
                    </sec:authorize>
                </sec:authorize>
            </div>
        </div>
    </div>
</header>