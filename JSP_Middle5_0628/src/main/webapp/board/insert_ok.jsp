<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
<jsp:useBean id="dao" class="com.sist.dao.ReplyBoardDAO"/>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
	//한글 넘어오니까 
	request.setCharacterEncoding("UTF-8");
%>
<%-- 사용자가 보내준 데이터를 vo객체에 첨부 --%>
<jsp:useBean id="vo" class="com.sist.dao.ReplyBoardVO">
 <jsp:setProperty property="*" name="vo"/>
 </jsp:useBean>
 <%
 	// DAO => 오라클에 데이터 첨부
 	dao.boardInsert(vo);
 	// 화면 이동 => list.jsp
 	
 %>
 <c:redirect url="list.jsp"></c:redirect>