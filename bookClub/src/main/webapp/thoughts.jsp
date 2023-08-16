<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Club - Thoughts</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style.css">
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <a href="/books" class="btn btn-secondary mb-3">Back to the Shelves</a>
            <h2 class="mb-3">
                <c:choose>
                    <c:when test="${isCurrentUser}">
                        You read "<c:out value="${requestScope.bookTitle}" />" by "<c:out value="${requestScope.bookAuthorName}" />"
                    </c:when>
                    <c:otherwise>
                        <c:out value="${requestScope.bookPostedBy}" /> read "<c:out value="${requestScope.bookTitle}" />" by "<c:out value="${requestScope.bookAuthorName}" />"
                    </c:otherwise>
                </c:choose>
            </h2>
            
            <c:choose>
                <c:when test="${isCurrentUser}">
                    <p>Here are your thoughts:</p>
                </c:when>
                <c:otherwise>
                    <p>Here are <c:out value="${requestScope.bookPostedBy}" />'s thoughts:</p>
                </c:otherwise>
            </c:choose>
            
            <div class="thoughts-section">
    			<p class="thoughts-text"><c:out value="${requestScope.bookMyThoughts}" /></p>
			</div>

            <div class="row mt-3">
                <div class="col-md-6">
                    <c:choose>
                        <c:when test="${isCurrentUser}">
                            <a href="/books/${requestScope.bookId}/edit" class="btn btn-primary">Edit</a>
                        </c:when>
                    </c:choose>
                </div>
                <div class="col-md-6 text-right">
                    <c:choose>
                        <c:when test="${isCurrentUser}">
                            <form action="/books/${requestScope.bookId}/delete" method="post">
                                <input type="hidden" name="_method" value="post">
                                <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this book?')">Delete</button>
                            </form>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>

