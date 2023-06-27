<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	EL 지원하는 내장객체 (581page)
	***1) requestScope => request.setAttribute()
	***2) sessionScope => session.setAttribute()
	3) param		   => request.getParameter()
	4) paramValues	   => request.getParameterValues()
 --%>
 <%
 	String name = "홍길동";
 
 //값설정
 	request.setAttribute("name","홍길동");
 	session.setAttribute("name","심청이");	//request,session 동시에 있다면 request가 우선순위
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 받아서 출력-->
이름: ${name }, ${requestScope.name } <%-- requestScope. 생략가능 --%>
<%= request.getAttribute("name") %>
<%-- 변수명 => key를 설정 --%>
</body>
</html>