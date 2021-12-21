<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#list").click(function(){
				location.href="<c:url value='/list.do'/>";
			});
			$("#remove").click(function(){
				location.href="<c:url value='/remove.do'/>?bno=${board.idx}";
			});
		});
	</script>
<body>
	<div class="container">
		<h2>Board Modify Page</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Board Modify Page</div>
			<div class="panel-body">
				<form action="<c:url value='/modify.do'/>" method="post">
					<div class="form-group">
						<label for="idx">Bno</label> 
						<input type="text" class="form-control" id="idx" name="idx" value="${board.idx}" readonly="readonly">
					</div>
					<div class="form-group">
						<label for="title">Title</label> 
						<input type="text" class="form-control" id="title" name="title" value="${board.title}">
					</div>
					<div class="form-group">
						<label for="contents">Text area</label> 
						<textarea class="form-control" rows="3" name="contents">${board.contents}</textarea>
					</div>
					<div class="form-group">
						<label for="writer">Writer</label> 
						<input type="text" class="form-control" id="writer" name="writer" value="${board.writer}" readonly="readonly">
					</div>
					<button type="submit" class="btn btn-primary">Modify</button>
					<button id="remove" type="button" class="btn btn-danger">Remove</button>
					<button id="list" type="button" class="btn btn-info">List</button>
				</form>
			</div>
			<div class="panel-footer">All right reserved</div>
		</div>
	</div>

</body>
</html>
