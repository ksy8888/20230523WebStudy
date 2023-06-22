<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.oreilly.servlet.*"%>
<%@ page import="com.oreilly.servlet.multipart.*" %>
<%
	request.setCharacterEncoding("UTF-8");	//POST방식 한글변환은 setChar~
	//String path="C:\\webDev\\webStudy\\JSP_Basic0622\\src\\main\\webapp\\image";	//img넣을 경로선택
	
	//C:\webDev\webStudy\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\JSP_Basic0622\image
	String path=application.getRealPath("/image");
	int max=1024*1024*100;
	String enctype="UTF-8";
	
	//업로드
	MultipartRequest mr = new MultipartRequest(request,path,max,enctype,new DefaultFileRenamePolicy());
	
	String name=mr.getOriginalFileName("upload");
	//파일 이동
	response.sendRedirect("list.jsp?fn="+name);

	
%>
