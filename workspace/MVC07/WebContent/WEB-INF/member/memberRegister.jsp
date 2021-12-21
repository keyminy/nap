<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
<c:set var="ctx" value="${pageContext.request.contextPath}"/>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
     function add(){
    	// form의 데이터 유효성 체크..
    	document.form1.action="<c:url value='/memberInsert.do'/>"; 
    	document.form1.submit();
     }
     function frmreset(){
   	  document.form1.reset();
     }
     function doublecheck(){
    	 if($("#id").val()==''){
    		 alert("아이디를 입력하세요.")
    		 $("#id").focus(); //focus를 위치시킴
    		 return;
    	 }
    	 var id = $("#id").val();
    	 $.ajax({
    		 url : "<c:url value='/memberDbcheck.do'/>",
    		 type : "POST",
    		 data : {"id" : id}, //넘기는 데이터
    		 success : dbCheck,
    		 error : function() {alert("error");}
    	 });
     }
     function dbCheck(data){
    	 if(data!="NO"){
    		 alert("중복된 아이디 입니다.");
    		 $("#id").focus();
    	 }else{
    		 alert("사용가능한 아이디 입니다.");
    		 $("#id").focus();
    	 }
     }
     //아이디 중복체크 영환
//   var id_length_error = "아이디는 3자 이상이여야 합니다";
//   $("#id").keyup(function(){
//   	//alert("아이디 중복체크");
//   	// 결과 디자인 - warning : 잘안됨, success : 잘됨
//   	$("checkId").removeClass("alert-warning alert-success"); // 초기에는 지우고
//   	var id = $("#id").val();//input의 값 가져오기
//   	if(!id || id.length<3){
//   		$("#checkId").text(id_length_error).addClass("alert-warning");
//   		return false;
//   	}else{ //아이디가 3글자 이상: 서버의 DB에가서 중복확인후 div에 넣는다
//   		//ajax Controller 서버에 넘기기
//   		$("#checkId").load("/memberAjax/checkId.do?id="+id);
//   	}
//   });
     function add2(){
    	 if($("#file").val()!=''){
    		 //file이 첨부가 된 경우, 그 파일을 formData로 만들어줌
    		 var formData = new FormData();
    		 formData.append("file",$("input[name=file]")[0].files[0]); //name : file으로 사용자가 첨부한 file을 formData에 append
    		 $.ajax({ //업로드를 위해 서버로 요청부분
    			 url : "<c:url value='/fileAdd.do'/>", //서버에 업로드 하는 컨트롤러
    			 type : "post",
    			 data : formData, //formData를 전송함
    			 processData : false, //formData가 넘어갈떄는 여기 false
    			 contentType : false, //파일 업로드 후 return 받을 type이 없으므로 false 
    			 //이거 2개는 ajax로 file업로드시 false 암기..
    			 success : function(data){	//원래 서버에서 업로드된 파일 이름 data를 받음.
    				// alert(data);
    				 $('#filename').val(data);//업로드된 파일 이름을 hidden tag인 filename에 설정해줌
						 document.form1.action="<c:url value='/memberInsert.do'/>?mode=fadd"; //text데이터를 저장하는 부분
						 document.form1.submit();//form에있는 id,pass,name,age,email,phone + hidden태그의 filename도 넘어감
    			 },//업로드가 성공한 담에 업로드 된 실제 파일의 이름 받기
    			 error : function() {alert("error");}
    		 });
    	 }else{	//file이 첨부 되지 않은경우
    		document.form1.action="<c:url value='/memberInsert.do'/>?mode=add";
			 	document.form1.submit();//form에있는 id,pass,name,age,email,phone 넘어감
    	 }
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
      <label>안녕하세요</label>
      </c:if> 
    </div>
    <div class="panel-body">
     <form id="form1" name="form1" class="form-horizontal" method="post">
	  <div class="form-group">
	    <label class="control-label col-sm-2" for="id">아이디:</label>
	    <div class="col-sm-10">
	      <table>
	      	<tr>
	      		<td><input type="text" class="form-control" id="id" name="id" placeholder="아이디를 입력하세요"></td>
	      		<td><input type="button" value="중복체크" onclick="doublecheck()" class="btn btn-warning" style="margin-left:30px;"/></td>
	      	</tr>
	      </table>
	    </div>
	  </div>
	  <div class="form-group">
	    <label class="control-label col-sm-2" for="pass">비밀번호:</label>
	    <div class="col-sm-10">
	      <input type="password" class="form-control" id="pass" name="pass" placeholder="비밀번호를 입력하세요" style="width: 30%">
	    </div>
	  </div>
	   <div class="form-group">
	    <label class="control-label col-sm-2" for="name">이름:</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="name" name="name" placeholder="이름을 입력하세요" style="width: 30%">
	    </div>
	  </div>
	    <div class="form-group">
	    <label class="control-label col-sm-2" for="age">나이:</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="age" name="age" placeholder="나이입력" style="width: 10%">
	    </div>
	  </div>
	    <div class="form-group">
	    <label class="control-label col-sm-2" for="email">이메일:</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="email" name="email" placeholder="이메일을 입력하세요" style="width: 30%">
	    </div>
	  </div>
	    <div class="form-group">
	    <label class="control-label col-sm-2" for="pass">전화번호:</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="phone" name="phone" placeholder="전화번호를 입력하세요" style="width: 30%">
	    </div>
	    <div class="form-group">
	    <label class="control-label col-sm-2" for="">첨부파일:</label>
	    <div class="col-sm-10">
	      <input type="file" class="control-label" id="file" name="file">
	    </div>
	  </div>	
	  <input type="hidden" name="filename" id="filename" value="">
	 </form>
	</div>
    <div class="panel-footer" style="text-align: center;">
       <c:if test="${sessionScope.userId==null || sessionScope.userId==''}"> 
         <input type="button" value="등록" class='btn btn-primary' onclick="add2()"/>
       </c:if>
       <c:if test="${sessionScope.userId!=null && sessionScope.userId!=''}"> 
          <input type="button" value="등록" class='btn btn-primary' onclick="add()" disabled="disabled"/>
       </c:if>
       <input type="button" value="취소" class='btn btn-warning' onclick="frmreset()"/>
       <input type="button" value="리스트" onclick="location.href='${ctx}/memberList.do'" class='btn btn-success'/>
    </div>
  </div>
</div>
</body>
</html>