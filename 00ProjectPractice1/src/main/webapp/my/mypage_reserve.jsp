<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css'>
<link rel="stylesheet" href="../assets/css/style.css">
<script type="text/javascript" src="https://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
let i=0;
	$('.checks').click(function(){
		let fdno=$(this).attr("data-fdno");
		let fno=$(this).attr("data-fno");
		if(i===0) {
			$('#reserve_info').show();
		$.ajax({
			type:'post',
			url:'../food/reserve_info.do',
			data:{"fdno":fdno,"fno":fno},
			success:function(result){
				$('#reserve_info').html(result)
			}
		})
		i=1;
		}else if(i===1){
			$('#reserve_info').hide();
			i=0;
		}
	})
})
</script>
</head>
<body>
<div class="tile" id="tile-1">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs nav-justified" role="tablist">
     <li class="nav-item">
        <a href="mypage_reserve.do" class="nav-link active">맛집 예약내역</a>
      </li>
      <li class="nav-item">
        <a href="mypage_reserve1.do" class="nav-link">공유주방 예약내역</a>
      </li>
     </ul>
  </div>
<section>
<div class="container1" style="width: 1000px">
      <h2>${sessionScope.name }님의 예약 목록</h2>
    <c:forEach var="vo" items="${list }">
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
						<span class="btn2 btn-sm btn-success checks" data-fdno="${vo.fdno }" data-fno="${vo.fno }">예약완료</span>
					</c:if>
					<c:if test="${vo.frok=='n' }">
						<span class="btn2 btn-sm btn-default">예약대기</span>
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
</body>
</html>
