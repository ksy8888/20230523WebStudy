<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
let u=0
$(function(){
	let i=0;
	$('#reservebtn').click(function(){
		let skdno=$(this).attr("data-no");
		$('#s_skdno').val(skdno);
		$('#sreserve').hide();
		if(i===0){
			$.ajax({
				type:'post',
				url:'diary.do',
				data:{"skdno":skdno},
				success:function(result){
					$('#sreserve_day').html(result);
				}
			})
			$(this).text("취소");
			$('#sreserve').show();
			i=1;
			
		}else if(i===1){
			$(this).text("예약");
			$('#sreserve').hide();
			i=0;
		}
	})
	 $(".heart").on("click", function() {
		    let skdno=$(this).attr('data-no')
			let types=$(this).attr('data-type')
			let hno=$(this).attr('data-cancel')
			console.log(hno);
			if(hno==0)
			{
				$(this).toggleClass("is-active");
				$.ajax({
					 type:'post',
					 data:{'jno':skdno,'types':types},
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
	
	$('#jjim').click(function(){
		let skdno=$(this).attr('data-no')
		let count=$(this).attr('data-cnt')
		let types=$(this).attr('data-type')
		if(count==0)
		{
			$.ajax({
				type:'post',
				data:{'types':types,'jno':skdno},
				url:'../jjim/insert.do',
				success:function(result)
				{
					alert('찜하기 완료')
					location.reload();
				}
			})	
		}
		
	})
	$('#jjims').click(function(){
		let no = $(this).attr('data-cancel')
		let count=$(this).attr('data-cnt')
		console.log('no='+no)
		console.log('count='+count)
		if(count>=1)
		{
			$.ajax({
				type:'post',
				url:'../jjim/jjim_delete.do',
				data:{'no':no},
				success:function(result)
				{
					alert('찜하기 취소')
					loacation.reload();
				}
			})
		}
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
<title>Insert title here</title>

</head>

<body>

<!-- Page Header -->

    <header class="page-header page-header-mini">

        <h1 class="title">${svo.title }</h1>
		 <c:if test="${sessionScope.id!=null }">
         <c:if test="${hno!=0 }">
          <div class= "heart text-center like is-active" data-no="${skdno }" data-type="4" data-cancel="${hno }">${htotal }</div>
         </c:if>
         <c:if test="${hno==0 }">
          <div class= "heart text-center like " data-no="${skdno }" data-type="4" data-cancel="${hno }">${htotal }</div>
         </c:if>
        </c:if>
        <ol class="breadcrumb pb-0">

            <li class="breadcrumb-item">${svo.sub_title }</a></li>

        </ol>

    </header>

    <!-- End Of Page Header -->



    <section class="container">

        <div class="page-container">

            <div class="page-content">

                <div class="card">

                    <div class="card-header pt-0">

                        <div class="blog-media mb-4">

                            <img src="${svo.poster }" alt="" class="w-100">

                        </div>  

                        <small class="small text-muted">

                       	<span>${svo.max_mem }</span>

                            <span class="px-2">·</span>

                            <span>${svo.price }</span>

                        </small><br>

                         <small class="small text-muted">

                         <!-- if문쓰기 -->

                            <span class="px-2">${svo.run }</span>

                             <span class="px-2">${svo.holi }</span><br>

                             <span class="px-2">${svo.address }</span><br>

                             <span class="px-2">${svo.hs_tag }</span>

                        </small>

                    </div>

                    <!-- 상세설명 시작 -->

                    <div class="card-body border-top">

                        <h2 class="title text-center">소개</h2> <!-- step 1,2,3,4 -->

                        <!-- for -->

                        <pre style="white-space: pre-wrap;border:none;background-color: white">${svo.intro }</pre>

                        <!-- for문 종료 -->
	
                    </div>
					
                    <div class="card-body border-top">

                        <h2 class="title text-center">정보</h2> <!-- step 1,2,3,4 -->

                        <!-- for -->

                        <pre style="white-space: pre-wrap;border:none;background-color: white">${svo.info }</pre>

                        <!-- for문 종료 -->

                    </div>

                    <div class="card-body border-top">

                        <h2 class="title text-center">환불규정</h2> <!-- step 1,2,3,4 -->

                        <!-- for -->

                       <pre style="white-space: pre-wrap;border:none;background-color: white">${svo.refund }</pre>

                        <!-- for문 종료 -->

                    </div>
					<div class="text-center">
					<a class="btn  b1" style="width: 150px" href="javascript:history.back()">목록으로</a>
					<a  class="btn  b1 rrr" id="reservebtn"  style="width: 150px" data-no="${svo.skdno }">예약하기</a>  
					<c:if test="${sessionScope.id!=null }">
					<c:if test="${no==0 }">
					<span class="btn  b1" id="jjim" style="width: 150px"  data-type="4" data-cnt="${count }" data-no="${skdno }" >찜하기</span> 
					</c:if>
					
					<c:if test="${no!=0 }">
					<span class="btn  b1" id="jjims" style="width: 150px" data-cancel="${no }" data-cnt="${count }">찜하기 취소</span> 
					</c:if>
					
					
					</c:if>
					</div>
					<div class="container" style="display:none" id="sreserve">
    				   <table class="table" height=700>
      						<tr>
								<td width="65%" height="600" class="danger">
									<table class="table">
										<thead><h3>예약일 정보</h3></thead>
										<tr>
											<td id="sreserve_day"></td>
										</tr>
									</table>
								</td>
								<td width="35%" rowspan="1" class="info">
									<table class="table">
										<thead><h3>예약 정보</h3></thead>
										<tr>
											<td colspan="2" class="text-center">
												<img src="${svo.poster }" style="width: 200px; height: 220px" id="reserve_img">
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<h4 id="reserve_name">${svo.title }</h4>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<span style="color:gray; display: none;" id="sd">예약일 : </span><span id="share_day"></span>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<span style="color:gray; display: none;" id="st">예약시간 : </span><span id="share_t"></span>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<span style="color:gray; display: none;" id="si">예약인원 : </span><span id="share_i"></span>
											</td>
										</tr>
										<tr id="sok" style="display: none;">
											<td colspan="2" class="text-center">
											<form method="post" action="../share/reserve_ok.do">
												<input type="hidden" name="skdno" id="s_skdno">
												<input type="hidden" name="srday" id="s_day">
												<input type="hidden" name="srtime" id="s_time">
												<input type="hidden" name="sinwon" id="s_inwon">
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
												<td id="share_time"></td>
											</tr>
										</table>
									</td>
								<td width="25%" height="120" class="default">
									<table class="table">
										<thead><h3>인원 정보</h3></thead>
										<tr>
											<td id="share_inwon"></td>
										</tr>
									</table>
								</td>
							</tr>
    					</table>
    				  </div>
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

			                         <a href="../reply/reply_delete.do?no=${rpvo.no }&type=2&rdno=${svo.skdno }" class="btn btn-xs btn-primary">삭제</a>

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

								  	     <input type=hidden name=rdno value="${svo.skdno }"> 		  	

								  	     <input type=hidden name=no value="${rpvo.no }"> 

								  	     <input type="hidden" name="type" value="2">

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

						 <input type=hidden name=rdno value="${svo.skdno }">

						 <input type=hidden name=type value="2">

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
				
				 <div class="page-sidebar">

                <h6 class=" ">지도</h6>

                <div id="map" style="height: 300px;width: 280px; border: 1px solid red">

                	

                </div>	
<!--                  <div style="height: 300px;width:280px; border:1px solid red"> -->
<!--                  </div> -->
    
<!--                 <div class="ad-card"> -->
<!--                    <div style="display"><span href="#" class="font-weight-bold">ADS</span></div> -->
<!--                     <input type="button" value="전체삭제" class="btn btn-sm btn-danger"> -->
<!--                 </div> -->
                <div class="ad-card" style="height: 300px">
               <br><h3 class=" ">최근 본 레시피</h3>
                   <div>
<!--                     <span class="btn btn-xs btn-danger" id="cookieDel"><a href="../list/recipeCookieDel.do">전체삭제</a></span> -->
                   <!-- forEach -->
                 <c:forEach var="cvo" items="${clist }" varStatus="s">
                 <c:if test="${s.index>=0 && s.index<3 }">
                 <table class="table">
                 <tr>
                 <td><a href="../share/shareDetail.do?skdno=${cvo.skdno }"> <img src="${cvo.poster }" style="width: 200px; height:150px; border-radius:30px !important;">
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

geocoder.addressSearch('${svo.address}', function(result, status) {



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

            content: '<div style="width:220px;text-align:center;padding:6px 0;">${svo.title }</div>'

        });

        infowindow.open(map, marker);



        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다

        map.setCenter(coords);

    } 

});    

</script>

</body>

</html>