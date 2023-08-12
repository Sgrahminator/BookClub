<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login and Registration</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style.css">
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1>Book Club</h1>
    <p>A place for friends to share thoughts on books</p>

    <!-- Display registration success message -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            ${successMessage}
        </div>
    </c:if>

    <!-- Display registration error messages -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            ${errorMessage}
        </div>
    </c:if>

    <div class="row">
        <!-- Registration Box -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">Registration</div>
                <div class="card-body">
                    <form:form action="/registration" modelAttribute="registrationUser" method="post">
                        <div class="form-group">
                            <form:label path="name">Name</form:label>
                            <form:input path="name" class="form-control"/>
                            <form:errors path="name" class="text-danger"/>
                            <c:if test="${not empty nameError}">
                                <span class="text-danger">${nameError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <form:label path="email">Email</form:label>
                            <form:input path="email" class="form-control"/>
                            <form:errors path="email" class="text-danger"/>
                            <c:if test="${not empty emailError}">
                                <span class="text-danger">${emailError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <form:label path="password">Password</form:label>
                            <form:input type="password" path="password" class="form-control"/>
                            <form:errors path="password" class="text-danger"/>
                            <c:if test="${not empty passwordError}">
                                <span class="text-danger">${passwordError}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <form:label path="confirm">Confirm Password</form:label>
                            <form:input type="password" path="confirm" class="form-control"/>
                            <form:errors path="confirm" class="text-danger"/>
                            <c:if test="${not empty confirmError}">
                                <span class="text-danger">${confirmError}</span>
                            </c:if>
                        </div>
                        <input type="submit" value="Register" class="btn btn-primary"/>
                    </form:form>
                </div>
            </div>
        </div>

        <!-- Login Box -->
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">Login</div>
                <div class="card-body">
                    <form:form action="/login" modelAttribute="loginUser" method="post">
                        <div class="form-group">
                            <form:label path="email">Email</form:label>
                            <form:input path="email" class="form-control"/>
                            <form:errors path="email" class="text-danger"/>
                            <c:if test="${not empty emailNotFound}">
                                <span class="text-danger">${emailNotFound}</span>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <form:label path="password">Password</form:label>
                            <form:input type="password" path="password" class="form-control"/>
                            <form:errors path="password" class="text-danger"/>
                            <c:if test="${not empty invalidCredentials}">
                                <span class="text-danger">${invalidCredentials}</span>
                            </c:if>
                        </div>
                        <input type="submit" value="Login" class="btn btn-success"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

