<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
 let i =0
 let u =0
 $(function(){
	  $(".heart").on("click", function() {
		    let fdno=$(this).attr('data-no')
			let types=$(this).attr('data-type')
			let hno=$(this).attr('data-cancel')
			console.log(hno);
			if(hno==0)
			{
				$(this).toggleClass("is-active");
				$.ajax({
					 type:'post',
					 data:{'jno':fdno,'types':types},
				 	 url:'../heart/insert.do',
				 	 success:function(result)
				 	 {
				 		 /*result count 총 갯수*/
				 		
				 		 location.reload(); 
				 	 }
				})
			}
			else 
			{
				$(this).toggleClass("is-active");
				$.ajax({
					 type:'post',
					 data:{'no':hno},
				 	 url:'../heart/heart_delete.do',
				 	 success:function(result)
				 	 {
				 		 /*result count 총 갯수*/
				 		 
				 		 location.reload(); 
				 	 }
				})
			}
		  });
	 
	  $('#reservebtn').click(function(){
			 let fdno=$(this).attr("data-no");
			 $('#r_fdno').val(fdno);
			 if(i===0){ 
				 $('#reserve_window').show();
				 $('.rrr').text('캔슬')
				 i=1;
				 $.ajax({
						type:'post',
						url:'diary.do',
						data:{"fdno":fdno},
						success:function(result)
						{
							$('#reserve_day').html(result);
						}
					})
			}
			 else
			{
				 $('#reserve_window').hide();
				 $('.rrr').text('예약하기')
				 i=0;
			}
		 })
	 $('#jjim').click(function(){
		 let types=$(this).attr('data-type')
		 let fdno=$(this).attr('data-no')
		 let no=$(this).attr('data-num')
		 $.ajax({
			 type:'post',
			 data:{'jno':fdno,'types':types},
			 url:'../jjim/insert.do',
			 success:function(result)
			 {
				 alert('찜하기 완료')
				 location.reload();
			 }
		 })
	 })
	  $('#jjims').click(function(){
		 let no=$(this).attr('data-num')
		 $.ajax({
			 type:'post',
			 data:{'no':no},
			 url:'../jjim/jjim_delete.do',
			 success:function(result)
			 {
				 alert('찜하기 취소')
				 location.reload();
			 }
		 })
	 })
	 $('.ups').click(function() {

		let no=$(this).attr('data-no2');

	$('.ups').text("수정");

	if(u==0) {

		$('#u'+no).show();

		$(this).text("취소");

		u=1;

	} else {

		$('#u'+no).hide();

		$(this).text("수정");

		u=0;

	}

		

	})
 })
</script>
<style type="text/css">
.heart {
  width: 100px;
  height: 100px;
  background: url("https://cssanimation.rocks/images/posts/steps/heart.png") no-repeat;
  background-position: 0 0;
  cursor: pointer;
  transition: background-position 1s steps(28);
  transition-duration: 0s;
  
  &.is-active {
    transition-duration: 1s;
    background-position: -2800px 0;
  }
}
</style>
</head>

<body>

<header class="page-header page-header-mini">

        <h1 class="title">${vo.title }</h1>
        <c:if test="${sessionScope.id!=null }">
         <c:if test="${hno!=0 }">
          <div class= "heart text-center like is-active" data-no="${fdno }" data-type="2" data-cancel="${hno }">${htotal }</div>
         </c:if>
         <c:if test="${hno==0 }">
          <div class= "heart text-center like " data-no="${fdno }" data-type="2" data-cancel="${hno }">${htotal }</div>
         </c:if>
        </c:if>

        <ol class="breadcrumb pb-0">

        	<li class="breadcrumb-item">영업시간:${vo.time }</li>&nbsp;
			<li class="breadcrumb-item">조회수:${vo.hit }</li>
        </ol>

    </header>

    <!-- End Of Page Header -->

	<div class="container">

  <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">

  <ol class="carousel-indicators">

    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>

    <c:forEach var="i" begin="1" end="${size }">

    <li data-target="#carouselExampleIndicators" data-slide-to="${i }"></li>

    </c:forEach>

    <!-- <li data-target="#carouselExampleIndicators" data-slide-to="2"></li> -->

  </ol>

  <div class="carousel-inner">

    <div class="carousel-item active">

      <img class="d-block w-100" src="${posters[0] }" alt="First slide" style="height: 350px">

    </div>

    <c:forEach var="i" begin="1" end="${size }">

    <div class="carousel-item">

      <img class="d-block w-100" src="${posters[i] }" alt="Second slide" style="height: 350px">

    </div>

    </c:forEach>

    <!-- <div class="carousel-item">

      <img class="d-block w-100" src="../assets/imgs/blog-3.jpg" alt="Third slide" style="height: 350px">

    </div> -->

  </div>

  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">

    <span class="carousel-control-prev-icon" aria-hidden="true"></span>

    <span class="sr-only">Previous</span>

  </a>

  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">

    <span class="carousel-control-next-icon" aria-hidden="true"></span>

    <span class="sr-only">Next</span>

  </a>

</div>

</div>



    <section class="container">

        <div class="page-container">

            <div class="page-content">

                <div class="card">

                    <!-- <div class="card-header pt-0">

                        <div class="blog-media mb-4">

                            <img src="assets/imgs/blog-6.jpg" alt="" class="w-100">사진

                        </div>  

                        <small class="small text-muted">

                            <span>최대인원</span>

                            <span class="px-2">·</span>

                            <span>가격</span>

                        </small><br>

                        <small class="small text-muted">

                        if

                            <span class="px-2">영업시간</span>

                            <span class="px-2">휴무</span><br>

                            <span class="px-2">주소</span>

                        </small>

                    </div> -->

                    <!-- 상세설명 시작 -->

                    <div class="card-body border-top">

                    	<h2 class="title text-left" >Restaurant</h2> <!-- step 1, 2, 3, 4,...  -->

                    		<pre>${vo.title }</pre>

                        </div>

                     <div class="card-body border-top">

                    	<h2 class="title text-left" >Address</h2> <!-- step 1, 2, 3, 4,...  -->

                    		<pre>${addr1 }<br>${addr2 }</pre>

                     </div>

                     <c:if test="${vo.type!='no' }">

                     <div class="card-body border-top">

                    	<h2 class="title text-left" >Type</h2> <!-- step 1, 2, 3, 4,...  -->

                    		<pre>${vo.type }</pre>

                     </div>

                     </c:if>

                     <c:if test="${vo.menu!='no' }">

                     <div class="card-body border-top">

                    	<h2 class="title text-left" >Menu</h2> <!-- step 1, 2, 3, 4,...  -->

                    		<pre>${vo.menu }</pre>

                     </div>

                     </c:if>

                     <c:if test="${vo.price!='no' }">

                     <div class="card-body border-top">

                    	<h2 class="title text-left" >Price</h2> <!-- step 1, 2, 3, 4,...  -->

                    		<pre>${vo.price }</pre>

                     </div>

                     </c:if>

                     <c:if test="${vo.parking!='no' }">

                     <div class="card-body border-top">

                    	<h2 class="title text-left" >Parking</h2> <!-- step 1, 2, 3, 4,...  -->

                    		<pre>${vo.parking }</pre>

                     </div>

                     </c:if>

                     <c:if test="${vo.time!='no' }">

                     <div class="card-body border-top">

                    	<h2 class="title text-left" >Opening hours</h2> <!-- step 1, 2, 3, 4,...  -->

                    		<pre>${vo.time }</pre>

                     </div>
					</c:if>
                     <div class="text-center">
     	 			
     				 <a  class="btn b1"  style="width: 150px" href="javascript:history.back()">맛집목록</a>
        		 	 <a  class="btn  b1 rrr" id="reservebtn"  style="width: 150px" data-no="${vo.fdno }">예약하기</a> 
<%--         		 	 <a  class="btn  b1"   style="width: 150px" data-no="${vo.fdno }">찜</a>  --%>
					<c:if test="${sessionScope.id!=null }">
					  <c:if test="${no==0 }">
					  <button class=" btn b1" id="jjim"  data-no="${fdno }" data-type="2" data-num="${no }">찜하기</button>
					  </c:if>
					  <c:if test="${no!=0 }">
					  <button class="btn b1" id="jjims"  data-num="${no }">찜하기 취소</button>
					  </c:if>
					</c:if>
    				 </div>
     <!-- res -->
    				  <div class="container" style="display:none" id="reserve_window">
    				   <table class="table" height=700>
      						<tr>
								<td width="65%" height="600" class="danger">
									<table class="table">
										<thead><h3>예약일 정보</h3></thead>
										<tr>
											<td id="reserve_day"></td>
										</tr>
									</table>
								</td>
								<td width="35%" rowspan="1" class="info">
									<table class="table">
										<thead><h3>예약 정보</h3></thead>
										<tr>
											<td colspan="2" class="text-center">
												<img src="${vo.poster }" style="width: 200px; height: 220px" id="reserve_img">
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<h4 id="reserve_name">${vo.title }</h4>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<span style="color:gray; display: none;" id="fd">예약일 : </span><span id="food_day"></span>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<span style="color:gray; display: none;" id="ft">예약시간 : </span><span id="food_t"></span>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<span style="color:gray; display: none;" id="fi">예약인원 : </span><span id="food_i"></span>
											</td>
										</tr>
										<tr id="ok" style="display: none;">
											<td colspan="2" class="text-center">
											<form method="post" action="../food/reserve_ok.do">
												<input type="hidden" name="fdno" id="r_fdno">
												<input type="hidden" name="frday" id="r_day">
												<input type="hidden" name="frtime" id="r_time">
												<input type="hidden" name="finwon" id="r_inwon">
												<input type="submit" value="예약" class="btn btn-lg btn-primary">
											</form>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td width="25%" height="120" class="warning">
										<table class="table">
											<thead><h3>예약 시간정보</h3></thead>
											<tr>
												<td id="food_time"></td>
											</tr>
										</table>
									</td>
								<td width="25%" height="120" class="default">
									<table class="table">
										<thead><h3>인원 정보</h3></thead>
										<tr>
											<td id="food_inwon"></td>
										</tr>
									</table>
								</td>
							</tr>
    						</table>
    				  
    				  </div>
    				 <!-- end 예약 -->
                    
         <div class="card-footer">

              <h6 class="mt-5 mb-3 text-center"><a href="#" class="text-dark">댓글</a></h6>

                        <hr>

                       <div class="media">

                         <table class="table">

                           <tr>

                            <td>

                          <c:forEach var="rpvo" items="${rList }">

                                <table class="table">

                                 <tr>

                                  <td class="text-left">

                                   ●${rpvo.name }&nbsp;(${rpvo.dbday })

                                   </td>

                                   <td class="text-right">

               				        <c:if test="${sessionScope.id==rpvo.id }">

               				         <span class="btn btn-xs btn-danger ups" data-no2="${rpvo.no }">수정</span>

			                         <a href="../reply/reply_delete.do?no=${rpvo.no }&type=${rpvo.type }&rdno=${vo.fdno }" class="btn btn-xs btn-primary">삭제</a>

               				        </c:if>

                                   </td>

                                 </tr>

                                 <tr>

                                  <td>

                                   <pre>${rpvo.msg }</pre>                           

                                  </td>

                                 </tr>  

                                                             

                                 <tr style="display:none" class="updates" id="u${rpvo.no}"> 

				 			          <td colspan="2">

								  	   <form method="post" action="../reply/reply_update.do" class="inline">

								  	     <input type=hidden name=rdno value="${vo.fdno }"> 		  	

								  	     <input type=hidden name=no value="${rpvo.no }"> 

								  	     <input type="hidden" name="type" value="4">

								  	     <textarea rows="5" cols="60" name="msg" class="form-control">${rpvo.msg }</textarea>

										 <input type=submit value="댓글수정" class="btn btn-primary btn-block">

								  	   </form>

								  	  </td>

								  </tr>                                          

                            </table>                                                                                                                                                                                                                                                                                      

                         </c:forEach>

                          </tr>

                         </table>

                        </div>

<%--댓글 --%>

                        <h6 class="mt-5 mb-3 text-center"><a href="#" class="text-dark">Write Your Comment</a></h6>

                        <hr>
                      <c:if test="${sessionScope.id != null }">

                       <form method="post" action="../reply/reply_insert.do" class="inline">

						 <input type=hidden name=rdno value="${vo.fdno }">

						 <input type=hidden name=type value="4">

                         <textarea rows="5" cols="60" name="msg" id="msg" class="form-control" placeholder="댓글을 작성해주세요."></textarea>                                             

                         <input type=submit class="btn btn-primary btn-block" value="댓글쓰기" >             

                        </form>

                      </c:if>                      

                 </div>                  

                </div> 



                <h6 class="mt-5 text-center">Related Posts</h6>

                <hr>

                <div class="row">                       

                    <div class="col-md-6 col-lg-4">

                        <div class="card mb-5">

                            <div class="card-header p-0">                                   

                                <div class="blog-media">

                                    <img src="assets/imgs/blog-2.jpg" alt="" class="w-100">

                                    <a href="#" class="badge badge-primary">#Placeat</a>        

                                </div>  

                            </div>

                            <div class="card-body px-0">

                                <h6 class="card-title mb-2"><a href="#" class="text-dark">Voluptates Corporis Placeat</a></h6>  

                                <small class="small text-muted">January 20 2019 

                                    <span class="px-2">-</span>

                                    <a href="#" class="text-muted">34 Comments</a>

                                </small>

                            </div>                  

                        </div>

                    </div>

                    <div class="col-md-6 col-lg-4">

                        <div class="card mb-5">

                            <div class="card-header p-0">                                   

                                <div class="blog-media">

                                    <img src="assets/imgs/blog-3.jpg" alt="" class="w-100">

                                    <a href="#" class="badge badge-primary">#dolores</a>        

                                </div>  

                            </div>

                            <div class="card-body px-0">

                                <h6 class="card-title mb-2"><a herf="#" class="text-dark">Dolorum Dolores Itaque</a></h6>   

                                <small class="small text-muted">January 19 2019 

                                    <span class="px-2">-</span>

                                    <a href="#" class="text-muted">64 Comments</a>

                                </small>

                            </div>      

                        </div>

                    </div>

                    <div class="col-md-6 col-lg-4 d-none d-lg-block">

                        <div class="card mb-5">

                            <div class="card-header p-0">                                   

                                <div class="blog-media">

                                    <img src="assets/imgs/blog-4.jpg" alt="" class="w-100">

                                    <a href="#" class="badge badge-primary">#amet</a>       

                                </div>  

                            </div>

                            <div class="card-body px-0">

                                <h6 class="card-title mb-2">Quisquam Dignissimos</h6>   

                                <small class="small text-muted">January 17 2019 

                                    <span class="px-2">-</span>

                                    <a href="#" class="text-muted">93 Comments</a>

                                </small>

                            </div>  

                        </div>

                    </div>

                </div>

            </div>

            <!-- Sidebar -->

            <div class="page-sidebar">

                <h6 class=" ">지도</h6>

                <div id="map" style="height: 300px;width: 280px; border: 1px solid red">

                	

                </div>

                    

                <!-- <div class="ad-card" style="">

                    <div style="display: block;"><span href="#" class="font-weight-bold">ADS</span></div>

                    <input type="button" value="전체삭제" class="btn btn-sm btn-danger">

                </div> -->

                 <div class="ad-card" style="height: 300px">
               <br><h3 class=" ">최근 본 맛집</h3>
                   <div>
<!--                     <span class="btn btn-xs btn-danger" id="cookieDel"><a href="../list/recipeCookieDel.do">전체삭제</a></span> -->
                   <!-- forEach -->
                 <c:forEach var="cvo" items="${clist }" varStatus="s">
                 <c:if test="${s.index>=0 && s.index<3 }">
                 <table class="table">
                 <tr>
                 <td><a href="../food/foodDetail.do?fdno=${cvo.fdno }"> <img src="${cvo.poster }" style="width: 200px; height:150px; border-radius:30px !important;">
<!--                   -->
				 </a> 
                 </td>
                 </tr>
                 <tr>
                   <td>${cvo.title }</td>
                 </tr>
                 </table>
                 </c:if>
                 </c:forEach>
                   <!-- forEach -->
                  
                   </div>
                   
                </div>

            </div>

        </div>

    </section>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2633d4b49e21c9b14bd17316553d25a2&libraries=services"></script>

<script>

var mapContainer = document.getElementById('map'), // 지도를 표시할 div 

    mapOption = {

        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표

        level: 3 // 지도의 확대 레벨

    };  



// 지도를 생성합니다    

var map = new kakao.maps.Map(mapContainer, mapOption); 



// 주소-좌표 변환 객체를 생성합니다

var geocoder = new kakao.maps.services.Geocoder();



// 주소로 좌표를 검색합니다

geocoder.addressSearch('${addr1}', function(result, status) {



    // 정상적으로 검색이 완료됐으면 

     if (status === kakao.maps.services.Status.OK) {



        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);



        // 결과값으로 받은 위치를 마커로 표시합니다

        var marker = new kakao.maps.Marker({

            map: map,

            position: coords

        });



        // 인포윈도우로 장소에 대한 설명을 표시합니다

        var infowindow = new kakao.maps.InfoWindow({

            content: '<div style="width:150px;text-align:center;padding:6px 0;">${vo.title }</div>'

        });

        infowindow.open(map, marker);



        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다

        map.setCenter(coords);

    } 

});    

</script>

</body>

</html>