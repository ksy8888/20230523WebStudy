<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	<!-- 없으면 for문 못씀 -->
<%
	String name = "홍길동";
	
	//request에 저장 (데이터 추가)
	//request.setAttribute("name", name);
%>

<%-- request.setAttribute("name", name); 를 태그형으로 만듬 --%>
<%-- EL에서 출력이 가능하게 변수 설정 --%>
<c:set var="name" value="심청이"/>	<%-- var="name" >> name변수를 $안에 사용 가능 --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>자바에서 출력 </h1>
	이름1: <%=name %><br>
	이름2: <%=request.getAttribute("name") %>
	
	<h1>EL</h1>
	이름3: ${name } <%-- request.getAttribute("name")  --%>
	<br>
	<%--c: Jquery와 충돌 방지
		출력 => $
		VueJS= > {{}}
		React => {}
	 --%>
	이름4:<c:out value="${name }"/> <br>
	이름5:<c:out value="<%=name %>"/>
	<%-- 자바스크립트에서 JSTL 사용이 가능 --%>
</body>
</html>