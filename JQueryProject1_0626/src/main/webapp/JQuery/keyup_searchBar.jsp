<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao2.*"%>
<%
	//객체 생성
	FoodDAO dao = FoodDAO.newInstance();
	List<FoodBean> list = dao.foodAllData();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">
.container{
   margin-top: 50px;
}
.row {
  margin: 0px auto;
  width:700px;	/* 테이블 크기*/
}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function() {
	$('#keyword').keyup(function() {
		let k=$('#keyword').val();	//val() >> 입력값 가져옴
		
		$('#user-table > tbody > tr').hide();
		let temp= $('#user-table > tbody > tr > td:nth-child(3n+2):contains("'+k+'")') //제목에서만 찾음 2,5,8...
		//table
		/*
		  번호	제목   tel
			1 |  2  |  3		>> 3n+2 (열3개 3n / 2번째 +2)
			------------		>> 번호에서 찾으려면 3n+1
			4 |  5  |  6	
			------------
			7 |  8  |  9 ....	
		*/
		$(temp).parent().show();	//td의 parent(부모)인 tr에서 찾아라
	})
})
</script>
</head>
<body>
	<div class="container">
		<h1 class="text-center">맛집 목록</h1>
		<div class="row">
			<table class="table">
				<tr>
				 <td>
				  <input type=text size=25 class="input-sm" id="keyword">
				 </td>
				</tr>
			</table>
		</div>
		<div class="row">
		<table class="table" id="user-table">
		 <tr>
		  <th class="text-center"></th>
		  <th class="text-center">맛집명</th>
		  <th class="text-center">전화</th>
		 </tr>
		 <tbody>
		 <%
		 	for(FoodBean vo : list)  {
		 %>
		 	<tr>
		 		<td class="text-center">
		 			<img src="<%= vo.getPoster() %>" width=35 height=35>
		 		</td>
		 		<td><%= vo.getName() %></td>
		 		<td><%= vo.getTel() %></td>
		 	</tr>
		 <%		
		 	}
		 %>
		 </tbody>
		</table>
		</div>
	</div>
</body>
</html>