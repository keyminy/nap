<%@page import="model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	MemberVO vo = (MemberVO)request.getAttribute("vo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		Controller에서 받은 데이터를 출력<br>
		이름 : <%=vo.getName() %><br>
		나이 : <%=vo.getAge() %><br>
		이메일 : <%=vo.getEmail() %><br>
</body>
</html>