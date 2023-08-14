<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
.row {
  margin: 0px auto;
  width: 850px; /* 가운데정렬 */
}
</style>
</head>
<body>
  <div class="wrapper row3">
	<main class="container clear">
	<div style="height:30px;"></div>
	 <h2 class="sectiontitle">묻고 답하기</h2>
	  <div class="row">
	   <table class="table">
	    <tr>
	     <th width=20% class="text-center">번호</th>
	     <td width=30% class="text-center">${vo.no }</td>
	     <th width=20% class="text-center">작성일</th>
	     <td width=30% class="text-center">${vo.dbday }</td>
	    </tr>
	    <tr>
	     <th width=20% class="text-center">이름</th>
	     <td width=30% class="text-center">${vo.name }</td>
	     <th width=20% class="text-center">조회수</th>
	     <td width=30% class="text-center">${vo.hit }</td>
	    </tr>
	     <tr>
	     <th width=20% class="text-center">제목</th>
	     <td colspan="3">${vo.subject }</td>
	    </tr>
	    <tr>
	     <td colspan="4" valign="top" class="text-left" height="200">
	      <pre style="white-space:pre-wrap; background-color:white; border:none">${vo.content }</pre>
	     </td>
	    </tr>
	    <tr>
	     <td colspan="4" class="text-right">
	     <c:if test="${sessionScope.id == vo.id }">
	      <a href="../QnA/update.do?no=${vo.no}" class="btn btn-xs btn-success">수정</a>
	      <a href="../QnA/delete.do?no=${vo.no }" class="btn btn-xs btn-info">삭제</a>
	     </c:if> 
	      <a href="../QnA/list.do" class="btn btn-xs btn-warning">목록</a>
	     </td>
	    </tr>
	   </table>
	  </div>
	  <div style="height:10px"></div>
	  <div class="row">
	    
	  </div>
	</main>
   </div>	
</body>
</html>
	     