<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function add2(){
		if($("#file").val()!=''){//파일이 존재하면
				var formData = new FormData();
				formData.append("file",$("input[name=file]")[0].files[0]);//name : file으로 사용자가 첨부한 file을 formData에 append
				$.ajax({
					url : "<c:url value='/fileAdd.do'/>",
					type : "post",
					data : formData,
					processData : false,
					contentType : false,
					success : function(data){
						$("#filename").val(data);
						document.form1.action="<c:url value='/memberInsert.do'/>?mode=fadd";
						document.form1.submit();
					},
					error : function() {alert("error");}
				});
		}else{//파일이 존재하지않으면
			document.form1.action="<c:url value='/memberInsert.do'/>?mode=add";
			document.form1.submit();
		}
	}
</script>
</head>
<body>
	<form id="form1" method="post">
		<input type="file" name="file" id="file">첨부
		 <input type="hidden" name="filename" id="filename" value="">
	</form>
	<input type="button" value="가입" onclick="add2()"> 
</body>
</html>