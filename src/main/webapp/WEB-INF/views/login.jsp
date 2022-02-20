<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Signin </title>
    <%--    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">--%>
    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="<c:url value="http://localhost:8080/resources/css/signin.css" />" rel="stylesheet">

</head>
<body class="text-center">
    <main class="form-signin">
        <form method="post" action="/login">
            <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

            <div class="form-floating">
                <input name="username" type="text" class="form-control" id="floatingInput" placeholder="Username">
                <label for="floatingInput">User name</label>
            </div>
            <div class="form-floating">
                <input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
                <label for="floatingPassword">Password</label>
            </div>
            <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
            <button onclick="window.location.href = '/registration'" class="w-100 btn btn-lg btn-secondary" type="button">Registration</button>
        </form>
    </main>
</body>
</html>



