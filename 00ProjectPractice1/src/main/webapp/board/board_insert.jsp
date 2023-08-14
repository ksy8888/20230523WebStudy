<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
 function board_write() {
	//내용 입력 안하면 못 넘어가게
	let frm = document.frm;	
	
	if(frm.name.value=="")  {
		frm.name.focus();
		return;
	}
	if(frm.subject.value=="")  {
		frm.subject.focus();
		return;
	}
	if(frm.content.value=="")  {
		frm.content.focus();
		return;
	}
	if(frm.pwd.value=="")  {
		frm.pwd.focus();
		return;
	}
	frm.submit();	//submit버튼을 함수화
}
 function func(cate) {
	document.frm.selectCategory.value = cate;
 }
</script>
</head>
<body>
	<div class="wrapper row3">
	  <main class="container clear"> 
	  	<h2 class="sectiontitle">글쓰기</h2>
	  	<div class="row">
	  	<form method=post action="../board/board_insert_ok.do" name=frm enctype="multipart/form-data">
	  	 <table class="table">
	  	  <tr>
	  	   <th width=15%> 분류
	  	   	 <select name="cate" class="input-sm" onchange="func(this.value)">
	  	   	  <!--   <option value="0">전체</option> -->
	  	   	 	<option value="1">자유</option>
	  	  		<!-- <option value="2">공지</option> -->
	  	  		<option value="3">레시피</option>
	  	  		<option value="4">스토어</option>
	  	  		<option value="5">공유주방</option>
	  	   	 </select>
	  	   	<input type=hidden name="selectCategory" value="1" readonly>
	  	   	<!-- <td width=85%>
	  	    <input type=text name=cate size=50 class="input-sm">
	  	    </td> -->
	  	   </th>
	  	  </tr>
	  	  <tr>
	  	   <th width=15%>이름</th>
	  	   <td width=85%>
	  	   <!-- <input type=text name=name size=20 class="input-sm"> -->
	  	   <%--  ${sessionScope.name} --%>
	  	   <input type="text" name="name" value="${sessionScope.name}" readonly>
	  	   </td>
	  	  </tr>
	  	   <tr>
	  	   <th width=15%>제목</th>
	  	   <td width=85%>
	  	    <input type=text name=subject size=50 class="input-sm">
	  	   </td>
	  	  </tr>
	  	   <tr>
	  	   <th width=15%>내용</th>
	  	   <td width=85%>
	  	    <textarea rows="10" cols="50" name="content"></textarea>
	  	   </td>
	  	  </tr>
	  	  <tr>
	  	   <th width=15%>첨부파일</th>
	  	   <td width=85%>
	  	    <input type=file name="upload" class="input-sm" size=20>
	  	   </td>
	  	  </tr> 
	  	  <tr>
	  	   <th width=15%>비밀번호</th>
	  	   <td width=85%>
	  	    <input type=password name=pwd size=10 class="input-sm">
	  	   </td>
	  	  </tr>
	  	  <tr>
	  	   <td colspan="2" class="text-center">
	  	    <!-- <input type=submit value="글쓰기" class="btn btn-sm btn-success">   -->
	  	    <input type=button value="글쓰기" class="btn btn-sm btn-success" onclick="board_write()">
	  	    <input type=button value="취소" class="btn btn-sm btn-info" onclick="javascript:history.back()">
	  	   </td>
	  	  </tr>
	  	 </table>
	  	  </form>
	  	 </div>
	  	
	  </main>
	 </div> 
</body>
</html>
	  	  		