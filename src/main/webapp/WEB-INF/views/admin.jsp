<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>

<body>
<jsp:include page="admin-header.jsp"></jsp:include>
<div class="films-grid-container">
    <table class="table text-center">
        <thead class="bg-light">
        <th> ID</th>
        <th>UserName</th>
        <th>Password</th>
        <th>Roles</th>
        </thead>
        <c:forEach items="${allUsers}" var="user">
            <tr>
                <th class="align-middle" scope="row">${user.id}</th>
                <td class="align-middle">${user.username}</td>
                <td class="align-middle">${user.password}</td>
                <td class="px-0">
                    <table class="table">
                        <tbody>
                        <c:forEach var="role" items="${user.roles}">
                            <tr>
                                <td class="w-50">${role.getName()}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
                <td class="align-middle col-1">
                    <form action="handle/page-edit" method="post">
                        <input type="hidden" name="user_id" value="${user.getId()}"/>
                        <input type="hidden" name="username" value="${user.getUsername()}"/>
                        <input type="hidden" name="password" value="${user.getPassword()}">
                        <input type="submit" class="btn btn-outline-dark" value="Edit"/>
                    </form>
                </td>
                <td class="align-middle col-1">
                    <form action="handle/delete/${user.getId()}" method="post">
                        <button class="btn btn-outline-dark" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>