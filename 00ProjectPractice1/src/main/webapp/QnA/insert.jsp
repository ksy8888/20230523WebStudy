<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.row {
  margin: 0px auto;
  width: 750px; /* 가운데정렬 */
}
</style>

</head>
<body>
   <div class="wrapper row3">
	<main class="container clear">
	<div style="height:30px;"></div>
	 <h2 class="sectiontitle">QnA</h2>
	  <div class="row">
	  <form method="post" action="../QnA/insert_ok.do">
	   <table class="table">
	    <tr>
	     <th width=20% class="text-center">제목</th>
	     <td width=80%>
	      <input type=text name=subject size=50 class="input-sm">
	     </td>
	    </tr>
	    <tr>
	     <th width=20% class="text-center">내용</th>
	     <td width=80%>
	      <textarea rows="10" cols="50" name=content></textarea>
	     </td>
	    </tr>
	    <tr>
	     <td colspan="2" class="text-center">
	     <button class="btn btn-sm btn-success>">글쓰기</button>
	      <input type=button  value="취소" class="btn btn-sm btn-info" onclick="javascript:history.back()">
	     </td>
	    </tr>
	   </table>
	   </form>
	  </div>
	</main>
   </div>

</body>
</html>