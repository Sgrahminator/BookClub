<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Details</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style.css">
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h1><c:choose><c:when test="${book.postedBy == sessionScope.name}">You</c:when><c:otherwise>${book.postedBy}</c:otherwise></c:choose> read "${book.title}" by "${book.authorName}"</h1>
    <a href="/books">Back to the shelves</a>

    <c:choose>
        <c:when test="${book.postedBy == sessionScope.name}">
            <h2>Here are your thoughts:</h2>
            <form:form action="/books/${book.id}/edit" method="post" modelAttribute="book">
                <div class="form-group">
                    <label for="myThoughts">My Thoughts</label>
                    <form:textarea path="myThoughts" id="myThoughts" class="form-control" required="true"/>
                </div>
                <input type="submit" value="Save Changes" class="btn btn-primary">
            </form:form>
            <form action="/books/${book.id}/delete" method="post">
                <input type="submit" value="Delete" class="btn btn-danger">
            </form>
        </c:when>
        <c:otherwise>
            <h2><c:choose><c:when test="${book.postedBy == sessionScope.name}">Your</c:when><c:otherwise>${book.postedBy}'s</c:otherwise></c:choose> thoughts on "${book.title}" by "${book.authorName}"</h2>
            <p>${book.myThoughts}</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
