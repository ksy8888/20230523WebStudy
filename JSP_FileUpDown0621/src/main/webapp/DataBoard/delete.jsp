<%@page import="com.sist.vo.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.io.*"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"/>
<%
	String no = request.getParameter("no");
	String pwd = request.getParameter("pwd");
	
	DataBoardVO vo = dao.databoardFileInfo(Integer.parseInt(no));
	
	boolean bCheck = dao.databoardDelete(Integer.parseInt(no), pwd);
	
	if(bCheck==true) {
		if(vo.getFilesize()!=0) {	//파일존재한다면
			File file = new File("c:\\download\\"+vo.getFilename());
			file.delete();
		}
		response.sendRedirect("list.jsp");
	} else {
%>
	<script>
	alert("비밀번호가 틀립니다!");
	history.back();
	</script>
<%		
	}
%>
