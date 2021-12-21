<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function add(){
			//form의 데이터 유효성 체크 부분 필요
			document.form1.action="<c:url value='/memberInsert.do'/>"
			document.form1.submit();
		}
		function frmreset(){
			documenet1.form1.reset();
		}
	</script>
</head>
<body>

	<div class="container">
		<h2>회원가입화면</h2>
		<div class="panel panel-default">
			<div class="panel-heading">
				<c:if test="${sessionScope.userId!=null && sessionScope.userId!=''}">
					<label>${sessionScope.userName}님이 로그인 하셨습니다.</label>
				</c:if>
				<c:if test="${sessionScope.userId==null || sessionScope.userId==''}">
					<label>안녕하세요(로그인안함)</label>
				</c:if>
			</div>
			<div class="panel-body">
				<form id="form1" name="form1" class="form-horizontal" method="post">
					<div class="form-group">
						<label class="control-label col-sm-2" for="id">아이디:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="id" placeholder="아이디를 입력하세요" name="id" style="width:30%;">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="pass">비밀번호:</label>
						<div class="col-sm-10">
								<input type="text" class="form-control" id="pass" placeholder="비밀번호를 입력하세요" name="pass" style="width:30%;">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="name">이름:</label>
						<div class="col-sm-10">
								<input type="text" class="form-control" id="name" placeholder="이름을 입력하세요" name="name" style="width:30%;">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="age">나이:</label>
						<div class="col-sm-10">
								<input type="text" class="form-control" id="age" placeholder="나이입력" name="age" style="width:10%;">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">이메일:</label>
						<div class="col-sm-10">
								<input type="text" class="form-control" id="email" placeholder="이메일을 입력하세요" name="email" style="width:30%;">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="phone">전화번호:</label>
						<div class="col-sm-10">
								<input type="text" class="form-control" id="phone" placeholder="전화번호를 입력하세요" name="phone" style="width:30%;">
						</div>
					</div>
				</form>
			</div>
			<div class="panel-footer" style="text-align:center;">
				<c:if test="${sessionScope.userId == null || sessionScope.userId==''}"> <!-- 로그인 안함 -->
					<input type="button" value="등록" class="btn btn-primary" onclick="add()"/>
				</c:if>
				<c:if test="${sessionScope.userId != null && sessionScope.userId!=''}"> <!-- 로그인 함 -->
					<input type="button" value="등록" class="btn btn-primary" onclick="add()" disabled="disabled"/>
				</c:if>
				<input type="button" value="취소" class="btn btn-warning" onclick="frmreset()" />
				<input type="button" value="리스트" onclick="location.href='${ctx}/memberList.do'" class='btn btn-success'/>
			</div>
		</div>
	</div>
</body>
</html>
