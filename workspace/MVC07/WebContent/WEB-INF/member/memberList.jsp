<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.bit.model.*" %>    
<%@ page import="java.util.*" %>
<%
     // ArrayList<MemberVO> list=(ArrayList<MemberVO>)request.getAttribute("list");
%>    
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
<style>
	table td{
		vertical-align: middle !important;	
	}
</style>
<script type="text/javascript"> 
  $(document).ready(function(){
	 <c:if test="${!empty msg}">
	    alert("${msg}");
	    <c:remove var="msg" scope="session"/>
	 </c:if>
  });
  function deleteFn(num){
	  location.href="${ctx}/memberDelete.do?num="+num; // ?num=12
  }
  function check(){
	  if($('#user_id').val()==''){
		  alert("아이디를 입력하세요");
		  return false;
	  }
	  if($('#password').val()==''){
		  alert("비밀번호를 입력하세요");
		  return false;
	  }
	  return true;
  } 
  function logout(){
	  location.href="<c:url value='/memberLogout.do'/>";  // /MVC06/memberList.do
  }
  function memberList(){
	 // var html=$("#collapse1 .panel-body").html();
	  //alert(html); //결과 : Panel Body (html 안의 값을 가져왓음)
	  //서버와 통신하는 부분
	  $.ajax({
		  url:"<c:url value='/memberAjaxList.do'/>", //memberAjaxList.do로 서버에 요청
		  type:"get", //넘기는 데이터가 없어서 get
		  dataType : "json", //서버에서 데이터를 받을때 json타입 받기
		  success : resultHtml, //서버에서 데이터를 받을떄 resultHtml 콜백함수 사용
		  // <------- 회원 리스트 [{  },{   },{  }] JSON배열 형태로 받음
		  error : function() {error("error");}
	  });
	 
  }
  //회원 리스트로 받기([{회원1},{회원2},...{회원n}] : JSON 배열형태로 resultHtml의 매개변수 data가받음)
  function resultHtml(data){
	  var html = "<table class='table table-hover'>";
     html+="<tr>";
     html+="<th>번호</th>";
     html+="<th>아이디</th>";  
     html+="<th>비밀번호</th>";  
     html+="<th>이름</th>";  
     html+="<th>나이</th>";   
     html+="<th>이메일</th>";    
     html+="<th>전화번호</th>";   
     html+="<th>삭제</th>";    
     html+="</tr>"; 
	  //날라온 데이터 반복문 처리 $.each()함수 $.each(data,function(index,obj){});
	  //resultHtml함수에서 서버에서 넘어온 값을 회원 리스트로 받기 ([{	},{		},{		}])
	  $.each(data,function(index,obj){
		  html+="<tr>";
		  html+= "<td>"+obj.num+"</td>";
		  html+= "<td>"+obj.id+"</td>";
		  html+= "<td>"+obj.pass+"</td>"; 
		  html+= "<td>"+obj.name+"</td>";
		  html+= "<td>"+obj.name+"</td>";
		  html+="<td>"+obj.age+"</td>";   
		  html+="<td>"+obj.email+"</td>";
		  html+="<td>"+obj.phone+"</td>";
		  html+="<td><input type='button' value='삭제' class='btn btn-warning' onclick='delFn("+obj.num+")'></td>";
		  html+= "</tr>";
	  });
	  html+="</table>";
	  $("#collapse1 .panel-body").html(html);
  }
  function delFn(num){
	  $.ajax({
		  url : "<c:url value='/memberAjaxDelete.do'/>",
		  type : "get",
		  data : {"num" : num},//넘기는 data
		  success : memberList, //삭제성공하면 목록보여주기
			error : function() {alert("error");}
	  });
  }
</script>
</head>
<body>
<div class="container">
  <h2>회원관리 시스템</h2>
  <div class="panel panel-default">
    <div class="panel-heading">
	<c:if test="${sessionScope.userId==null || sessionScope.userId==''}">
	  <form class="form-inline" action="${ctx}/memberLogin.do" method="post">
		  <div class="form-group">
		    <label for="user_id">ID:</label>
		    <input type="text" class="form-control" id="user_id" name="user_id">
		  </div>
		  <div class="form-group">
		    <label for="pwd">Password:</label>
		    <input type="password" class="form-control" id="password" name="password">
		  </div>
		  <button type="submit" class="btn btn-default" onclick="return check()">로그인</button>
	  </form> 
	 </c:if> 
	 <c:if test="${sessionScope.userId!=null && sessionScope.userId!=''}">
		 ${sessionScope.userName}님 환영합니다.
		 <button type="button" class="btn btn-warning" onclick="logout()">로그아웃</button>
	 </c:if>
     </div>
    <div class="panel-body">
	  <div class="table-responsive">          
	  <table class="table table-hover">
	    <thead>
	      <tr>
	        <th>번호</th>
			    <th>아이디</th>
			    <th>비밀번호</th>
			    <th>이름</th>
			    <th>나이</th>
			    <th>이메일</th>
			    <th>전화번호</th>
			    <th>이미지</th>
			    <th>삭제</th>
	      </tr>
	     </thead>
	     <tbody>
	     <c:forEach var="vo" items="${list}">
    	  <tr>
    	    <td>${vo.num}</td>
    	    <td><a href="${ctx}/memberContent.do?num=${vo.num}">${vo.id}</a></td>
    	    <td>${vo.pass}</td>
    	    <td>${vo.name}</td>
    	    <td>${vo.age}</td>
    	    <td>${vo.email}</td>
    	    <td>${vo.phone}</td>       	     	
    	    <td>
    	    	<c:if test="${vo.filename!=null && vo.filename!=''}">
    	    		<img src="<c:out value='file_repo/${vo.filename}'/>" width="60px"; height="60px";/>
    	    	</c:if>
    	    </td>       	     	
    	    <c:if test="${sessionScope.userId==vo.id}"> 	
    	      <td><input type="button" value="삭제" class="btn btn-warning" onclick="deleteFn(${vo.num})"></td>
    	    </c:if>
    	    <c:if test="${sessionScope.userId!=vo.id}"> 
    	      <td><input type="button" value="삭제" class="btn btn-warning" onclick="deleteFn(${vo.num})" disabled="disabled"></td>
    	    </c:if>
    	  </tr>    	  
         </c:forEach>
         <tr>
          <td colspan="8"><input type="button" value="회원가입" class="btn btn-primary" onclick="location.href='${ctx}/memberRegister.do'"/></td>
         </tr>
	     </tbody>
	    </table>
	   </div>
    </div>
    <div class="panel-footer">
      회원관리 ERP System(admin@bit.com)
    </div>
    <div class="panel-group">
		  <div class="panel panel-default">
		    <div class="panel-heading">
	      	<h4 class="panel-title">
	        <a data-toggle="collapse" href="#collapse1" onclick="memberList()">회원리스트보기</a>
      		</h4>
   		 </div>
		    <div id="collapse1" class="panel-collapse collapse">
		      <div class="panel-body">Panel Body</div>
		      <div class="panel-footer">Panel Footer</div>
		    </div>
		  </div>
</div>
  </div>
</div>
</body>
</html>