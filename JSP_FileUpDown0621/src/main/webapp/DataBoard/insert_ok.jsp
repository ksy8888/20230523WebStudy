<%@page import="com.sist.vo.DataBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*,java.io.*"%>
<%@ page import="com.oreilly.servlet.*" %>
<%@ page import="com.oreilly.servlet.multipart.*" %> 
<%
	// _ok.jsp: 기능처리 (member_ok , update_ok , ....) >> 처리하고 이동만
	//데이터베이스 처리 -> list.jsp
	//1. 한글 처리
	request.setCharacterEncoding("UTF-8");
	//1-1. 파일 업로드 클래스 생성
	String path="c:\\download";
	int size=1024*1024*100;
	String enctype="UTF-8";	//한글이름명 파일 안깨지게
	MultipartRequest mr = new MultipartRequest(request,path,size,enctype,new DefaultFileRenamePolicy());	//업로드
	//new DefaultFileRenamePolicy() 같은 명의 파일 들어오면 파일명1 파일명2
	
	//2. 요청데이터 받기 
	String name = mr.getParameter("name");
	String subject = mr.getParameter("subject");
	String content = mr.getParameter("content");
	String pwd = mr.getParameter("pwd");
	
	//3. VO에 묶는다
	DataBoardVO vo = new DataBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	//<input type=file name=upload class="input-sm" size=20>
	
	String filename = mr.getFilesystemName("upload");
	if(filename==null) {	//업로드가 안된 상태 
		vo.setFilename("");
		vo.setFilesize(0);
	}
	else {	//업로드가 된 상태
		File file = new File(path+"\\"+filename);
		vo.setFilename(filename);
		vo.setFilesize((int)file.length());
	}
	//4. DAO로 전송
	DataBoardDAO dao = DataBoardDAO.newInstance();
	dao.databoardInsert(vo);
	
	//5. 화면 이동 (list.jsp)
	response.sendRedirect("list.jsp");
%>