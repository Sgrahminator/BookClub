<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Book</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style.css">
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1>Edit Book</h1>
    <a href="/books">Back to the shelves</a>

    <form:form action="/books/${book.id}/edit" method="post" modelAttribute="book">
        <div class="form-group">
            <label for="title">Title</label>
            <form:input path="title" id="title" class="form-control" required="true"/>
        </div>
        <div class="form-group">
            <label for="authorName">Author Name</label>
            <form:input path="authorName" id="authorName" class="form-control" required="true"/>
        </div>
        <div class="form-group">
            <label for="myThoughts">My Thoughts</label>
            <form:textarea path="myThoughts" id="myThoughts" class="form-control" required="true"/>
        </div>
        <input type="submit" value="Save Changes" class="btn btn-primary">
    </form:form>
</div>
</body>
</html>
