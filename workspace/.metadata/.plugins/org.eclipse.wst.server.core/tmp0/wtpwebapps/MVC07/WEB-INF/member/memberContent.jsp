<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.bit.model.*" %>    
<%
  // MemberVO vo=(MemberVO)request.getAttribute("vo");
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
<script type="text/javascript">
   function update(){
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
    				 $('#filename').val(data);//파일 이름 hidden tag인 filename에 을 설정해줌
						 document.form1.action="<c:url value='/memberUpdate.do'/>?mode=fupdate"; //text데이터를 저장하는 부분
						 document.form1.submit();//form에있는 age,email,phone + hidden태그의 num,filename도 넘어감!!
    			 },//업로드가 성공한 담에 업로드 된 실제 파일의 이름 받기
    			 error : function() {alert("error");}
    		 });
    	 }else{	//file이 첨부 되지 않은경우
    		document.form1.action="<c:url value='/memberUpdate.do'/>?mode=update";
			 	document.form1.submit();//form에있는 id,pass,name,age,email,phone 넘어감
    	 }
   }
   function frmreset(){
	  document.form1.reset();
   }
   function getFile(filename){
	   location.href="<c:url value='/fileGet.do'/>?filename="+filename;
   }
   function delFile(num,filename){
	   location.href="<c:url value='/fileDel.do'/>?num="+num+"&filename="+filename;
   }
</script>
</head>
<body>
<div class="container">
  <h2>상세화면</h2>
  <div class="panel panel-default">
    <div class="panel-heading">
     <c:if test="${sessionScope.userId!=null && sessionScope.userId!='' && sessionScope.userId==vo.id}">
       <label>
       		<img src="<c:out value='file_repo/${vo.filename}'/>" width="60px"; height="60px";/>
       		${sessionScope.userName}님이 로그인 하셨습니다.
       	</label>
     </c:if>
     <c:if test="${sessionScope.userId==null || sessionScope.userId==''}">
      <label>안녕하세요</label>
     </c:if>   
    </div>
    <div class="panel-body">
    <form id="form1" name="form1" class="form-horizontal" method="post">
      <input type="hidden" name="num" value="${vo.num}"/>
      <input type="hidden" name="filename" id="filename" value=""/>
      <div class="form-group">
         <label class="control-label col-sm-2">번호:</label>
         <div class="col-sm-10">
           <c:out value="${vo.num}"/>
         </div>
      </div>   
      <div class="form-group">
         <label class="control-label col-sm-2">아이디:</label>
         <div class="col-sm-10">
           <c:out value="${vo.id}"/>
         </div>
      </div> 
       <div class="form-group">
         <label class="control-label col-sm-2">비밀번호:</label>
         <div class="col-sm-10">
           <c:out value="${vo.pass}"/>
         </div>
      </div>  
       <div class="form-group">
         <label class="control-label col-sm-2">이름:</label>
         <div class="col-sm-10">
           <c:out value="${vo.name}"/>
         </div>
      </div> 
       <div class="form-group">
         <label class="control-label col-sm-2">나이:</label>
         <div class="col-sm-10">
           <input type="text" class="form-control" id="age" name="age" value="${vo.age}" style="width: 10%">
         </div> 
      </div> 
       <div class="form-group">
         <label class="control-label col-sm-2">이메일:</label>
         <div class="col-sm-10">
           <input type="text" class="form-control" id="email" name="email" value="${vo.email}" style="width: 30%">
         </div>
      </div> 
       <div class="form-group">
         <label class="control-label col-sm-2">전화번호:</label>
         <div class="col-sm-10">
           <input type="text" class="form-control" id="phone" name="phone" value="${vo.phone}" style="width: 30%">
         </div>
      </div> 
       <div class="form-group">
         <label class="control-label col-sm-2">첨부파일:</label>
         <div class="col-sm-10">
           <input type="file" id="file" name="file">
           <c:if test="${vo.filename!=null && vo.filename !=''}">
           		<a href="javascript:getFile('${vo.filename}')"><c:out value='${vo.filename}'/></a>
           </c:if>
           <c:if test="${sessionScope.userId!=null && sessionScope.userId==vo.id && vo.filename!=null && vo.filename!=''}">
           	<a href="javascript:delFile('${vo.num}','${vo.filename}')"><span class="glyphicon glyphicon-remove"></span></a>
           </c:if>
         </div>
      </div> 
      
     </form>
    </div>
    <div class="panel-footer" style="text-align: center;"> 
       <c:if test="${!empty sessionScope.userId}">
         <c:if test="${sessionScope.userId==vo.id}">
          <input type="button" value="수정하기" class='btn btn-primary' onclick="update()"/>
         </c:if>
       
         <c:if test="${sessionScope.userId!=vo.id}">
          <input type="button" value="수정하기" class='btn btn-primary' onclick="update()" disabled="disabled"/>
         </c:if> 
       </c:if> 
       <input type="button" value="취소" class='btn btn-warning' onclick="frmreset()"/>
       <input type="button" value="리스트" onclick="location.href='${ctx}/memberList.do'" class='btn btn-success'/>
    </div>
  </div>
</div>
</body>
</html>