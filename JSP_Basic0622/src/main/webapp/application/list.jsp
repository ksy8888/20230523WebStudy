<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//upload.jsp에서 파일명 받아옴
	String fn= request.getParameter("fn");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<img src="../image/<%=fn %>" style="width:100%"/>
</body>
</html>