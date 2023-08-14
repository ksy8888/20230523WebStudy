<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css'>
<link rel="stylesheet" href="../assets/css/style.css">
<script type="text/javascript" src="https://code.jquery.com/jquery.js"></script>
<script type="text/javascript">

$(function(){
	$('#findcate').click(function (){
		let fd = $('#fd').val();
		console.log(fd);
	if(fd.trim()===""){
		$('#fd').focus();
		alert("검색어를 입력하세요")
		return
	}
	else{
		$('#findfrm').submit();
	}
})
</script>
</head>
<body>
<div class="tile" id="tile-1">
  <ul class="nav nav-tabs nav-justified" role="tablist">
     <li class="nav-item">
        <a href="adminpage_reserve.do" class="nav-link active">맛집 예약대기</a>
      </li>
      <li class="nav-item">
        <a href="adminpage_reserve1.do" class="nav-link">공유주방 예약대기</a>
      </li>
     </ul>
  </div>
  <form class="d-flex tm-search-form" id="findfrm" method="post" action="../admin/admin_reserveFind.do">
         	<input id="fd" class="form-control tm-search-input" type="search" placeholder="Search" aria-label="Search" style="width:70%;" value="${fd }" name="fd">
           	<button class="btn btn-outline-success tm-search-btn" type="submit"style="width:30%" id="findcate">검색
            <input id="reservefind" type="hidden" value="reserve" name="reserve">
            <i class="fas fa-search"></i>
       		</button>
       	</form>
<section>
<div class="container1" style="width: 1000px">
      <h2>맛집예약관리</h2>
    <c:forEach var="vo" items="${aflist }">
    <!--single booking details-->
    
    <div class="request-details">
    <div>
    	<p>예약번호</p>
    	<h2>${vo.fno }</h2>
    </div>
      <div class="date">
      	<p>예약일</p>
        <h3>${vo.frday }</h3>
      </div>
      <div class="time">
        <div class="placeholder">
          <i class="fas fa-hourglass-half"></i>
          <p>예약시간</p>
        </div>
        <h3>${vo.frtime }</h3>
      </div>
      <div class="company">
        <div class="placeholder">
          <i class="fas fa-building"></i>
          <p>맛집명</p>
        </div>
        <h3>${vo.ftitle }</h3>
      </div>
      <div class="live-stock">
        <div class="placeholder">
          <i class="fa fa-user"></i>
          <p>인원</p>
        </div>
        <h3>${vo.finwon }</h3>
      </div>
      <div class="booking-status">
        <div class="placeholder">
          <i class="fas fa-question-circle"></i>
          <p>예약상태</p>
        </div>
        <div class="label">
          			<c:if test="${vo.frok=='y' }">
						<span class="btn2 btn-sm btn-success">승인완료</span>
					</c:if>
					<c:if test="${vo.frok=='n' }">
						<a href="../admin/admin_reserve_ok.do?fno=${vo.fno }" class="btn2 btn-sm btn-success">승인대기</a>
					</c:if>
        </div>
      </div>
    </div>
    </c:forEach>
      </div>
   </section>
	<table>
		<tr>
			<td>
				<div id="reserve_info"></div>
			</td>
		</tr>
	</table>
	<div class="container">
      <div class="row">
        <div class="justify-content-center">
         <nav id="pagination" aria-label="Page navigation" style="margin-left:350px;">
	            <ul class="pagination justify-content-center">
		 			<c:if test="${afcurpage>1 }">
		            <li class="page-item"><a class="page-link" href="adminpage_reserve.do?page=${afcurpage>1?afcurpage-1:afcurpage }">Previous</a></li>
					</c:if>
					
		             <c:forEach var="i" begin="${afstartpage }" end="${afendpage }">
		             	<c:if test="${i==afcurpage }">
		             	<li class="page-item"><a class="active" class="active" href="adminpage_reserve.do?page=${i }">${i }</a></li>
		             	</c:if>
		             	<c:if test="${i!=afcurpage }">
		             	<li class="page-item"><a class="page-link" class="active" href="adminpage_reserve.do?page=${i }">${i }</a></li>
		             	</c:if>
		             </c:forEach> 
		             
	                <c:if test="${afcurpage<aftotalpage }">
	                <li class="page-item"><a class="page-link" href="adminpage_reserve.do?page=${afcurpage<aftotalpage?afcurpage+1:afcurpage }">Next</a></li>
	              </c:if>
	            </ul>
	        </nav>
        </div>
	   
    </div>
      </div>
</body>
</html>