<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	//response.sendRedirect("response_2.jsp");	// sendRedirect() >> 새로운 파일로 이동
	RequestDispatcher rd = request.getRequestDispatcher("response_2.jsp");
	rd.forward(request,response);	// forward() >> 파일은 유지, 화면만 바꿔줌
	System.out.println(request);
%>

   