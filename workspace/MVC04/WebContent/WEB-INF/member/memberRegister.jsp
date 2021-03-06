<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;}
.tg td{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;
  overflow:hidden;padding:10px 5px;word-break:normal;}
.tg th{border-color:black;border-style:solid;border-width:1px;font-family:Arial, sans-serif;font-size:14px;
  font-weight:normal;overflow:hidden;padding:10px 5px;word-break:normal;}
.tg .tg-baqh{text-align:center;vertical-align:top}
</style>
 <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>
회원 가입 화면
<form action="${ctx}/memberInsert.do" method="post">
<table class="tg" style="undefined;table-layout: fixed; width: 545px">
<colgroup>
<col style="width: 160px">
<col style="width: 385px">
</colgroup>
<thead>
  <tr>
    <th class="tg-baqh">아이디</th>
    <th class="tg-baqh">
    	<input type="text" name="id"/>
    </th>
  </tr>
</thead>
<tbody>
  <tr>
    <td class="tg-baqh">패스워드</td>
    <td class="tg-baqh"><input type="password" name="pass"/></td>
  </tr>
  <tr>
    <td class="tg-baqh">이름</td>
    <td class="tg-baqh"><input type="text" name="name"/></td>
  </tr>
  <tr>
    <td class="tg-baqh">나이</td>
    <td class="tg-baqh"><input type="text" name="age"/></td>
  </tr>
  <tr>
    <td class="tg-baqh">이메일</td>
    <td class="tg-baqh"><input type="text" name="email"/></td>
  </tr>
  <tr>
    <td class="tg-baqh">전화번호</td>
    <td class="tg-baqh"><input type="text" name="phone"/></td>
  </tr>
  <tr>
    <td class="tg-baqh" colspan="2" align='center'>
    	<input type="submit" class="btn btn-primary" value="가입"/>
				<input type="reset" class="btn btn-warning" value="취소"/>
    </td>
  </tr>
</tbody>
</table>
</form>
</body>
</html>