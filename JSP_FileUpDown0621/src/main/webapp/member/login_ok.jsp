<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<%@ page import= "com.sist.vo.MemberVO" %>    

<%
	String id=request.getParameter("id"); //name=id
	String pwd=request.getParameter("pwd");
	
	MemberDAO dao = MemberDAO.newInstance();
	MemberVO vo = dao.isLogin(id, pwd); //NOID , NOPWD , OK
	
	if(vo.getMsg().equals("OK")) {
		session.setAttribute("id", vo.getId());	//세션에 id 저장
		session.setAttribute("name", vo.getName());
		session.setAttribute("sex", vo.getSex());
		session.setMaxInactiveInterval(3600); //세션 유지 1시간
	}
	
%>    

<%= vo.getMsg() %>