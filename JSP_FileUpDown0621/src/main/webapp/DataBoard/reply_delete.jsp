<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,com.sist.vo.*" %>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"/>useBean>    
<%
	String no = request.getParameter("no");
	String bno = request.getParameter("bno"); //삭제하고 게시물보기로 이동할 게시글 번호
	
	
	dao.replyDelete(Integer.parseInt(no));
	//이동 
	response.sendRedirect("detail.jsp?no="+bno);
%>
