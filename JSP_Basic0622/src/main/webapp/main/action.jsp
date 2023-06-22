<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>JSP 액션 태그</h1>
	<table class="table">
		<tr>
		 <td>
		  <ul>
		   <li>&lt;jsp:include page=""&gt;: JSP특정영역 다른 JSP를 첨부(조립식)
		   		<br>
		   		&lt;jsp:include page=""&gt;		=> JSP를 따로 실행, HTML묶어서 사용 (동적)
		   		<br>
		   		&lt;%@ include file="" %&gt;	=> JSP를 먼저 묶어서 한번 컴파일한다 :menu,footer
		   </li>
		   <li>&lt;jsp:forward page=""&gt; :파일을 덮어쓴다 (URL을 유지=request 사용)</li>
		   <li>&lt;jsp:useBean page=""&gt; name="key"&gt;: 해당 JSP로 값을 전솔 때 사용</li>
		   <li> &lt;jsp:forward page=""&gt;</li>
		   <li></li>
		   <li></li>
		  </ul>
		 </td>
		</tr>
	</table>
</body>
</html>