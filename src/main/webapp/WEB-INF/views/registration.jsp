<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Create an account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="<c:url value="/resources/css/signin.css" />" rel="stylesheet">
</head>

<style>
    .primer1{
        color: #FF0000;
    }
</style>


<body class="text-center">
<main class="form-signin">
    <form:form cssClass="form-control" method="POST" modelAttribute="userForm">
        <h1 class="h3 mb-3 fw-normal">Registration</h1>
        <div class="primer1">
            <span aria-colcount="red">
                       <form:errors  cssClass="form-control text-danger" path="username"></form:errors>
                ${usernameError}
            </span>
        </div>
        <div class="form-floating">
            <form:input cssClass="form-control" type="text" path="username" placeholder="Username"
                        autofocus="true"></form:input>
        </div>
        <div class="primer1">
            <form:errors cssClass="form-control" path="password"></form:errors>
                ${passwordError}
        </div>
        <div class="form-floating">
            <form:input cssClass="form-control" type="password" path="password" placeholder="Password"></form:input>
        </div>
        <div class="form-floating">
            <form:input cssClass="form-control" type="password" path="passwordConfirm"
                        placeholder="Confirm your password"></form:input>
        </div>
        <button class="btn btn-lg btn-primary" type="submit">Create an account!</button>
        <button onclick="window.location.href = '/films'" class="btn btn-lg btn-secondary" type="button">Back</button>
    </form:form>
</main>
</body>
</html>