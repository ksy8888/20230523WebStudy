<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	let pwd=$('#pwd').attr('data-no');
	let dbpwd=$('#dbpwd').attr('data-no');
	if(pwd!=dbpwd)
	{
		alert('비밀번호가 틀립니다')
	}
		
})
</script>
</head>
<body>
<div class="text-center">
	<h1 >비밀번호를 입력하세요</h1>
		<form method=post action="../my/pwdcheck.do" >
			<input type="password" name=pwd>
			<button class="btn btn-sm btn-danger" >입력</button>
		</form>
		<input type="hidden" id=pwd data-no="${pwd }">
		<input type="hidden" id=dbpwd data-no=${dbpwd }>
</div>
</body>
</html>