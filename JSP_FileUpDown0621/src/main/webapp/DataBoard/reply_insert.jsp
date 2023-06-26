<%@ page import="com.sist.vo.ReplyVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="dao" class="com.sist.dao.DataBoardDAO"/>
<%
	request.setCharacterEncoding("UTF-8");

//INSERT INTO jspReply VALUES(jr_no_seq.nextval,?,?,?,?,SYSDATE);
//											    --------
	String bno = request.getParameter("bno");
	String msg = request.getParameter("msg");
	String id=(String)session.getAttribute("id");
	String name=(String)session.getAttribute("name");
	
	ReplyVO vo = new ReplyVO();
	vo.setBno(Integer.parseInt(bno));
	vo.setId(id);
	vo.setMsg(msg);
	vo.setName(name);
	
	//DAO 연동
	dao.replyInsert(vo);
	//이동
	response.sendRedirect("detail.jsp?no="+bno);
%>