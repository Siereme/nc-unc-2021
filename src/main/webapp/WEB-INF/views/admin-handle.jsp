<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Handle Page</title>
    <link href="<c:url value="/resources/bootstrap-5.0.2-dist/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/bootstrap-5.0.2-dist/js/bootstrap.bundle.min.js" />"></script>
    <link href="<c:url value="/resources/css/handle.css" />" rel="stylesheet">

</head>
<body>
<div class="error-block">
    <c:forEach var="error" items="${result.getAllErrors()}">
        <p class="text-danger m-0">${error.getDefaultMessage()}</p>
    </c:forEach>
</div>
<div class="handle-container">
    <h2 class="card-header">${modalTitle}</h2>
    <form action="../${eventType}" method="post">
        <div class="row">
            <div class="col-6">
                <span>Name</span>
                <input type="hidden" name="id" value="${user.getId()}"/>
                <input class="form-control" name="name" value="${user.getUsername()}">
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                <div class="roles">
                    <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                        Roles
                    </button>
                    <ul class="dropdown-menu p-0" aria-labelledby="dropdownMenuButton1">
                        <select class="form-select" multiple aria-label="multiple select example" name="roles">
                            <c:forEach var="role" items="${userRoles}">
                                <option value="${role.getId()}" selected>${role.getName()}</option>
                            </c:forEach>
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.getId()}">${role.getName()}</option>
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