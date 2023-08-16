<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Club - Books</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style.css">
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="header">
    <div class="brand">
        <h1>Book Club</h1>
    </div>
    <div class="logout">
        <a href="/logout">Logout</a>
    </div>
</div>

    <div class="row">
        <div class="col-md-12">
            <h2>Welcome, <c:out value="${user.name}" /></h2>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <a href="/books/new" class="btn btn-primary">+ Add a book to my shelf</a>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-md-12">
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author Name</th>
                        <th>Posted By</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="book" items="${allBooks}">
                        <tr>
                            <td><c:out value="${book.id}" /></td>
                            <td><a href="/books/<c:out value="${book.id}" />"><c:out value="${book.title}" /></a></td>
                            <td><c:out value="${book.authorName}" /></td>
                            <td><c:out value="${book.postedBy}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>


