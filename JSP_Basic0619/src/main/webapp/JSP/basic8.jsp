<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
    <%
    	int a=10/0;
    	/*
    	***500 : 자바 문법 오류
    	***404 : 파일을 못찾는 경우
    	400 : bad request	(값 보내고 받는 데이터형 틀린경우 ..)
    		  ?no=10   ===> boolean no (X)
    	412 : 한글 변환 코드 문제
    	200 : 정상 수행
    	403 : 접근 거부
    	*/
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%= a %>
</body>
</html>