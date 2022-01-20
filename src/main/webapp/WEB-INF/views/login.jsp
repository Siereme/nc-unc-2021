<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <title>Login Page</title>
</head>
<body>
<sec:authorize access="isAuthenticated()">
<% response.sendRedirect("/main"); %>
</sec:authorize>
<div>
    <form method="POST" action="/login">
        <h2>Login</h2>
        <div>
            <input name="username" type="text" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" placeholder="Password"/>
            <button type="submit">Log In</button>
            <h4><a href="registration">CREATE AN ACCOUNT</a></h4>
        </div>
    </form>
</div>
</body>
