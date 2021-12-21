<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>스프링 게시판</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
	<script>
		$(document).ready(function(){
			$("#regBtn").click(function(){
				location.href="<c:url value='/register.do'/>"
			});
		});
	</script>
<body>
<div class="container">
  <h2>메인화면</h2>
  <div class="panel panel-default">
    <div class="panel-heading">Spring web mvc 게시판
    	<button id="regBtn" type="button" class="btn btn-xs pull-right btn-primary">게시물쓰기</button>
    </div>
    <div class="panel-body">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>게시물번호</th>
								<th>제목</th>
								<th>조회수</th>
								<th>등록자</th>
								<th>등록일</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="vo" items="${list}">
							<tr>
								<td>${vo.idx}</td>
								<td><a href="<c:url value='/get.do?bno=${vo.idx}'/>">${vo.title}</a></td>
								<td>${vo.count}</td>
								<td>${vo.writer}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd" value="${vo.indate}"/></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
    </div>
    <div class="panel-footer">All right reserved</div>
  </div>
</div>

</body>
</html>
