<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name='viewport' content='width=device-width, initial-scale=1'>
<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
<script type="text/javascript">
	function getfile(filename){
		location.href="<c:url value='/download.do'/>?filename="+filename;
	}
</script>
</head>
<body>
<div class="container">
  <h2>업로드가 완료 되었습니다.</h2>
  <div class="panel panel-default">
    <div class="panel-heading">스프링을 이용한 다중 파일 업로드 구현</div>
    <div class="panel-body">
    	<table class='table table-bordered table-hover'>
    		<tr>
    			<td>아이디</td>
    			<td>${map.id}</td>	
    		</tr>
    		<tr>
    			<td>이름</td>
    			<td>${map.name}</td>	
    		</tr>
    		<c:forEach var="fName" items="${map.fileList}">
	    		<tr>	
	    			<td>${fName}</td>
	    			<td><a href="javascript:getfile('${fName}')"><span class="glyphicon glyphicon-file"></span></a></td> <!--파일 이름을 넘겨주는 함수선언 -->
    			</tr>
    		</c:forEach>
    		<tr>
    			<td colspan='2' align='center'>
    				<a href="<c:url value='form.do'/>">다시 업로드하기</a>
    			</td>
    		</tr>
    	</table>
    </div>
    <div class="panel-footer">All right reserved</div>
  </div>
</div>
</body>
</html>