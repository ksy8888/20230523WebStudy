<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div style="margin-left: 490px"> <!-- 사이즈 보기 -->
	<h1>상품정보 보기</h1>
	</div>
	<form  method=post action="product_insert_ok.do">
	<table class="table"> <!-- 사이즈 보기 -->
		<tr>
			<th >이름</th>
			  <td><input type="text" name=title size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>사진</th>
			<td><input type="text" name=poster size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>부제목</th>
			<td><textarea style="width: 170px" name=subject ></textarea></td>
		</tr>	
		<tr>
			<th>할인률</th>
			<td><input type="text" name=sale size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>할인가</th>
			<td><input type="text" name=priced_sale size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>기본가</th>
			<td><input type="text" name=original_pri size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>첫구매 할인가</th>
			<td><input type="text" name=first_pri size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>별점</th>
			<td><input type="text" name=score size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>배달비</th>
			<td><input type="text" name=dilivery_pri size=10 class="input-sm"></td>
		</tr>	
		<tr>
			<th>수량</th>
			<td><input type="text" name=goods_count size=10 class="input-sm"></td>
		</tr>	
	</table>
		<div class="text-right">
			<button  class="btn btn-sm btn-danger">저장</button>
			<a href="javascript:history.back()" class="btn btn-xs btn-danger">취소</a>
		</div>
	</form>
</body>
</html>