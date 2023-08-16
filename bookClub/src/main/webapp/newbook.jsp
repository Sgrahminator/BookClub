<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add a New Book</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/style.css">
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="header">
    	<a class="navbar-brand" href="#">Book Club</a>
    	<ul class="navbar-nav ml-auto">
        	<li class="nav-item">
            	<a class="nav-link" href="/books">Back to the shelves</a>
        	</li>
    	</ul>
	</div>

	<div class="row mt-3">
        <div class="col-md-12">
            <h2>Add a Book to Your Shelf!</h2>
        </div>
    </div>
    <c:if test="${result.hasErrors()}">
    	<div class="alert alert-danger">
        	There were errors while processing the form. Please check and correct the inputs below.
    	</div>
	</c:if>

    <div class="row">
        <div class="col-md-12">
            <form:form action="/books/new" method="POST" modelAttribute="book">

                <div class="form-group">
                    <label for="title">Title:</label>
                    <form:input path="title" id="title" class="form-control" />
                    <form:errors path="title" cssClass="text-danger" />
                </div>

                <div class="form-group">
                    <label for="authorName">Author Name:</label>
                    <form:input path="authorName" id="authorName" class="form-control" />
                    <form:errors path="authorName" cssClass="text-danger" />
                </div>

                <div class="form-group">
                    <label for="myThoughts">My Thoughts:</label>
                    <form:textarea path="myThoughts" id="myThoughts" class="form-control" rows="4" />
                    <form:errors path="myThoughts" cssClass="text-danger" />
                </div>

                <form:hidden path="postedBy" />

                <button type="submit" class="btn btn-primary">Submit</button>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>

