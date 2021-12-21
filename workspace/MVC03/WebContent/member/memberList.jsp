<%@page import="model.MemberVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//ArrayList<MemberVO> list = (ArrayList<MemberVO>)request.getAttribute("list");
	//jstl로하면 위에거 필요없슴
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<style>
		#wrap{
		margin : 30px;
		}
	</style>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
MVC03 예제 - Controller + View 연동부분(+JSTL과 EL)
	<div id="wrap">
		<table class="table-dark">
			<tr>
				<td>번호</td>
				<td>아이디</td>
				<td>비밀번호</td>
				<td>이름</td>
				<td>나이</td>
				<td>이메일</td>
				<td>전화번호</td>
				<td>삭제</td>
			</tr>
			<c:forEach var="vo" items="${list}">
				<tr>
					<td>${vo.num}</td>
					<td><a href="/MVC03/memberContent.do?num=${vo.num}">${vo.id}</a></td>
					<td>${vo.pass}</td>
					<td>${vo.name}</td>
					<td>${vo.age}</td>
					<td>${vo.email}</td>
					<td>${vo.phone}</td>
					<td><input type="button" value="삭제" class="btn btn-primary" onclick="deleteFn(${vo.num})"/></td>
				</tr>
			</c:forEach>
			<%-- <%for(MemberVO vo : list){ %>
			<tr>
				<td><%=vo.getNum()%></td>
				<td><a href='/MVC03/memberContent.do?num=<%=vo.getNum()%>'><%=vo.getId()%></a></td>
				<td><%=vo.getPass()%></td>
				<td><%=vo.getName()%></td>
				<td><%=vo.getAge()%></td>
				<td><%=vo.getEmail()%></td>
				<td><%=vo.getPhone()%></td>
				<td><input type="button" value="삭제" class="btn btn-primary"
						onclick="deleteFn(<%=vo.getNum()%>)"/></td>
			</tr>
			<%} %> --%>
			<tr>
				<td colspan='8' align='right'>
					<input type="button" value="회원가입" class="btn btn-success" onclick="location.href='member/memberRegister.html'"/>
				</td>
			</tr>
		</table>
	</div>
	<script>
		function deleteFn(num){
			location.href="memberDelete.do?num="+num;
		}
	</script>
</body>
</html>