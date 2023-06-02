<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
 <%
 	String no=request.getParameter("no"); //사용자 요청
 	if(no==null)
 		no="1";	//실행할때 1번에 있는 값부터 보여줌
	GoodsDAO dao = GoodsDAO.newInstance();
 	GoodsVO vo = dao.goodsDetailData(Integer.parseInt(no));
 %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style.css">
<style type="text/css">
.container {
	margin-top: 50px;
	width: 1200px;
}
.row {
	width: 1000px;
	margin: 0px auto;
}
table {
	width:850px;
}
#image {
	width:100%;
	height:500px;
	border-radius:10px;
}
#title {
	font-size: 25px;
	font-weight: bold;
}

#sub {
	color:gray;
}
#percent {
	font-size: 25px;
	color: red;
	font-weight:bold;
}
#price {
	font-size: 25px;
	font-weight:bold;
}
#psub {
	font-size: 12px;
	color:#999;
} 
#label {
	font-size: 10px;
	color: green;
	font-weight: bold;
}
#price2 {
	font-size: 20px;
	color: green;
	font-weight: bold;
}
#star {
	font-size: 15px;
	color: orange;
	font-weight: bold;
}
#bold {
	font-weight:bold;
}

#count {
	color:gray;
}
/*사진*/
img[src*="delivery3"], img[src*="point"] {
	width: 20px;
	height: 20px;
}
#price3 {
	color:#4bc1bc;
	font-weight:bold;
}
#sel {
	width:100%;
	height:30px;
}
#cart, #buy {
	width: 220px;
	height: 70px;
	border: 2px green solid;
	font-size:20px;
	font-weight:bold;
}
#cart {
	background-color: white;
	color: green;
}
#buy {
	background-color: green;
	color: white;
}
/* #table_content {
	align: center;
}
 */
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<table class="table_content" width=800>
			 <tr>
				<td width="40%" class="tdcenter" rowspan="9">
					<img src="<%=vo.getPoster() %>" id="image">
				</td>
				<td width="60%" class="tdcenter">
				<span id="title">
				<%= vo.getName() %>
				</span>
				</td>
			 </tr>		
			 
			 <tr>
			 	<td width=60%>
			 	<span id="sub"><%=vo.getSub() %></span>
			 	</td>
			 </tr> 
			 <tr>
			 	<td width=60%>
			 	 <span id="percent"><%=vo.getDiscount() %>%</span>&nbsp;<span id="price"><%=vo.getPrice() %></span>
			 	 <p>
			 		<del id="psub">49,800원</del>
			 	 </p>
			 	</td>
			 </tr> 
			 <tr>
			 	<td width=60%>
			 		<span id="label">첫구매할인가</span>&nbsp;
			 		<span id="price2"><%=vo.getFirst_price() %></span>
			 	</td>
			 </tr> 
			 <tr>
			 	<td width=60%>
			 		<span id="star">★★★★★</span>&nbsp;
			 		<span id="bold">4.9</span>
			 		<span id="count">(9)</span>
			 	</td>
			 </tr> 
			 <tr>
			 	<td width=60%>
			 		<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_delivery3.png">
			 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 		<span id="deli"><%= vo.getDelivery() %></span>
			 	</td>
			 </tr> 
			 <tr>
			 	<td width=60%>
			 		<img src="https://recipe1.ezmember.co.kr/img/mobile/icon_point.png">
			 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			 		<span id="price3">149원</span> 적립 (모든 회원 구매액의 0.5% 적립)
			 	</td>
			 </tr> 
			 <tr>
			 	<td width=60%>
			 		<select id="sel">
			 			<option>옵션선택</option>
			 		</select>
			 	</td>
			 </tr> 
			  <tr>
			 	<td width=60%>
			 		<input type=button value="장바구니" id="cart">
			 		<input type=button value="바로구매" id="buy">
			 		
			 	</td>
			 </tr> 
			</table>
		</div>
	</div>
</body>
</html>