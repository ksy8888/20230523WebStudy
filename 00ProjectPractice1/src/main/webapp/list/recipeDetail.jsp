<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">

 let u= 0;
$(function(){
	
		  $(".heart").on("click", function() {
		    let rdno=$(this).attr('data-no')
			let types=$(this).attr('data-type')
			let hno=$(this).attr('data-cancel')
			console.log(hno);
			if(hno==0)
			{
				$(this).toggleClass("is-active");
				$.ajax({
					 type:'post',
					 data:{'jno':rdno,'types':types},
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
				 let types=$(this).attr('data-type')
				 let rdno=$(this).attr('data-no')
				 let no=$(this).attr('data-num')
				 $.ajax({
					 type:'post',
					 data:{'jno':rdno,'types':types},
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

		let no=$(this).attr('data-no');

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
<meta charset="UTF-8">

<title>Insert title here</title>
<style type="text/css">
@font-face {
  font-family: Cyber;
  src: url("https://assets.codepen.io/605876/Blender-Pro-Bold.otf");
  font-display: swap;
}

* {
  box-sizing: border-box;
}

/* body {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  min-height: 100vh;
  font-family: 'Cyber', sans-serif;
  background: linear-gradient(90deg, #f5ed00 70%, #e6de00 70%), #fff700;
} */

body .cybr-btn + .cybr-btn {
  margin-top: 2rem;
}

.cybr-btn {
  --primary: hsl(var(--primary-hue), 85%, calc(var(--primary-lightness, 50) * 1%));
  --shadow-primary: hsl(var(--shadow-primary-hue), 90%, 50%);
  --primary-hue: 0;
  --primary-lightness: 50;
  --color: hsl(0, 0%, 100%);
  --font-size: 26px;
  --shadow-primary-hue: 180;
  --label-size: 9px;
  --shadow-secondary-hue: 60;
  --shadow-secondary: hsl(var(--shadow-secondary-hue), 90%, 60%);
  --clip: polygon(0 0, 100% 0, 100% 100%, 95% 100%, 95% 90%, 85% 90%, 85% 100%, 8% 100%, 0 70%);
  --border: 4px;
  --shimmy-distance: 5;
  --clip-one: polygon(0 2%, 100% 2%, 100% 95%, 95% 95%, 95% 90%, 85% 90%, 85% 95%, 8% 95%, 0 70%);
  --clip-two: polygon(0 78%, 100% 78%, 100% 100%, 95% 100%, 95% 90%, 85% 90%, 85% 100%, 8% 100%, 0 78%);
  --clip-three: polygon(0 44%, 100% 44%, 100% 54%, 95% 54%, 95% 54%, 85% 54%, 85% 54%, 8% 54%, 0 54%);
  --clip-four: polygon(0 0, 100% 0, 100% 0, 95% 0, 95% 0, 85% 0, 85% 0, 8% 0, 0 0);
  --clip-five: polygon(0 0, 100% 0, 100% 0, 95% 0, 95% 0, 85% 0, 85% 0, 8% 0, 0 0);
  --clip-six: polygon(0 40%, 100% 40%, 100% 85%, 95% 85%, 95% 85%, 85% 85%, 85% 85%, 8% 85%, 0 70%);
  --clip-seven: polygon(0 63%, 100% 63%, 100% 80%, 95% 80%, 95% 80%, 85% 80%, 85% 80%, 8% 80%, 0 70%);
  font-family: 'Cyber', sans-serif;
  color: var(--color);
  cursor: pointer;
  background: transparent;
  text-transform: uppercase;
  font-size: var(--font-size);
  outline: transparent;
  letter-spacing: 2px;
  position: relative;
  font-weight: 700;
  border: 0;
  min-width: 300px;
  height: 75px;
  line-height: 75px;
  transition: background 0.2s;
}

.cybr-btn:hover {
  --primary: hsl(var(--primary-hue), 85%, calc(var(--primary-lightness, 50) * 0.8%));
}
.cybr-btn:active {
  --primary: hsl(var(--primary-hue), 85%, calc(var(--primary-lightness, 50) * 0.6%));
}

.cybr-btn:after,
.cybr-btn:before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  clip-path: var(--clip);
  z-index: -1;
}

.cybr-btn:before {
  background: var(--shadow-primary);
  transform: translate(var(--border), 0);
}

.cybr-btn:after {
  background: var(--primary);
}

.cybr-btn__tag {
  position: absolute;
  padding: 1px 4px;
  letter-spacing: 1px;
  line-height: 1;
  bottom: -5%;
  right: 5%;
  font-weight: normal;
  color: hsl(0, 0%, 0%);
  font-size: var(--label-size);
}

.cybr-btn__glitch {
  position: absolute;
  top: calc(var(--border) * -1);
  left: calc(var(--border) * -1);
  right: calc(var(--border) * -1);
  bottom: calc(var(--border) * -1);
  background: var(--shadow-primary);
  text-shadow: 2px 2px var(--shadow-primary), -2px -2px var(--shadow-secondary);
  clip-path: var(--clip);
  animation: glitch 2s infinite;
  display: none;
}

.cybr-btn:hover .cybr-btn__glitch {
  display: block;
}

.cybr-btn__glitch:before {
  content: '';
  position: absolute;
  top: calc(var(--border) * 1);
  right: calc(var(--border) * 1);
  bottom: calc(var(--border) * 1);
  left: calc(var(--border) * 1);
  clip-path: var(--clip);
  background: var(--primary);
  z-index: -1;
}

@keyframes glitch {
  0% {
    clip-path: var(--clip-one);
  }
  2%, 8% {
    clip-path: var(--clip-two);
    transform: translate(calc(var(--shimmy-distance) * -1%), 0);
  }
  6% {
    clip-path: var(--clip-two);
    transform: translate(calc(var(--shimmy-distance) * 1%), 0);
  }
  9% {
    clip-path: var(--clip-two);
    transform: translate(0, 0);
  }
  10% {
    clip-path: var(--clip-three);
    transform: translate(calc(var(--shimmy-distance) * 1%), 0);
  }
  13% {
    clip-path: var(--clip-three);
    transform: translate(0, 0);
  }
  14%, 21% {
    clip-path: var(--clip-four);
    transform: translate(calc(var(--shimmy-distance) * 1%), 0);
  }
  25% {
    clip-path: var(--clip-five);
    transform: translate(calc(var(--shimmy-distance) * 1%), 0);
  }
  30% {
    clip-path: var(--clip-five);
    transform: translate(calc(var(--shimmy-distance) * -1%), 0);
  }
  35%, 45% {
    clip-path: var(--clip-six);
    transform: translate(calc(var(--shimmy-distance) * -1%));
  }
  40% {
    clip-path: var(--clip-six);
    transform: translate(calc(var(--shimmy-distance) * 1%));
  }
  50% {
    clip-path: var(--clip-six);
    transform: translate(0, 0);
  }
  55% {
    clip-path: var(--clip-seven);
    transform: translate(calc(var(--shimmy-distance) * 1%), 0);
  }
  60% {
    clip-path: var(--clip-seven);
    transform: translate(0, 0);
  }
  31%, 61%, 100% {
    clip-path: var(--clip-four);
  }
}

.cybr-btn:nth-of-type(2) {
  --primary-hue: 260;
}
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
<!-- Page Header -->
    <header class="page-header page-header-mini">
        <h1 class="title">${rvo.title }</h1> 
        <c:if test="${sessionScope.id!=null }">
         <c:if test="${hno!=0 }">
          <div class= "heart text-center like is-active" data-no="${rdno }" data-type="1" data-cancel="${hno }">${htotal }</div>
         </c:if>
         <c:if test="${hno==0 }">
          <div class= "heart text-center like " data-no="${rdno }" data-type="1" data-cancel="${hno }">${htotal }</div>
         </c:if>
        </c:if>
        <ol class="breadcrumb pb-0">
            <li class="breadcrumb-item">${rvo.info_1 }</a></li>
            <li class="breadcrumb-item">${rvo.info_2 }</a></li>
            <li class="breadcrumb-item">${rvo.info_3 }</a></li>
            
        </ol>
    </header>
    <!-- End Of Page Header -->

    <section class="container">
        <div class="page-container">
            <div class="page-content">
                <div class="card">
                    <div class="card-header pt-0">
                        <div class="blog-media mb-4">
                            <img src="${rvo.poster }" alt="" class="w-100">
                        </div>  
                        <small class="small text-muted">
                        <!--chefpos 값일때 -->
                        <c:if test="${rvo.chef_pos==null }">
                        <img alt="" src="../img/defaultchef_pos.jpg" style="width:50px; height:50px;">
                        </c:if>
                        <c:if test="${rvo.chef_pos!=null }">
                        <img alt="" src="${rvo.chef_pos }" style="width:50px; height:50px;">
                        </c:if>
                            <span class="px-2">·</span>
                            <span>${rvo.chef }</span>
                        </small><br>
                         <small class="small text-muted">
                         <!-- if문쓰기 -->
                            <span class="px-2">${rvo.chef_info }</span>
                        </small>
                    </div>
                    <!-- 상세설명 시작 -->
                    <div class="card-body border-top">
                        <h2 class="title text-center"><b>조리순서</b></h2> <!-- step 1,2,3,4 -->
                        <!-- for -->
                      <c:forEach var="step" items="${step_post }" varStatus="s">  
                        <table class="table" style="border: 1px solid cyan">
                          <tr>  
                         	 <th>${s.index+1 }번 ${step_text[s.index] }</th>   
                          </tr>
                          <tr>
                          		<td colspan="1"><img src="${step }" style="width:100%; margin:auto;height:300px;"></td> 
                          </tr>
                        </table>
                         </c:forEach>  
                        <!-- for문 종료 -->
                    </div>
                    <!-- button -->
                    
                    <div class="text-center">
                    
                    <a href="javascript:history.back()">
						  <input type="button" value="목록으로" class="btn btn-xs b1">
						  </a>
						
						&nbsp;

					<c:if test="${sessionScope.id!=null }">
					 	 <c:if test="${no==0 }">
					  		<button class=" btn b1" id="jjim"  data-no="${rdno }" data-type="1" data-num="${no }">찜하기</button>
					 	</c:if>
					  <c:if test="${no!=0 }">
					  	<button class="btn b1" id="jjims"  data-num="${no }">찜하기 취소</button>
					  </c:if>
					</c:if>
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

               				         <span class="btn btn-xs btn-danger ups" data-no="${rpvo.no }">수정</span>

			                         <a href="../reply/reply_delete.do?no=${rpvo.no }&type=${rpvo.type}&rdno=${rvo.rdno }" class="btn btn-xs btn-primary">삭제</a>

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

								  	     <input type=hidden name=rdno value="${rvo.rdno }"> 		  	

								  	     <input type=hidden name=no value="${rpvo.no }"> 

								  	     <input type="hidden" name="type" value="1">

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

<%--댓글 --%> <!-- dd -->

                        <h6 class="mt-5 mb-3 text-center"><a href="#" class="text-dark">Write Your Comment</a></h6>

                        <hr>

                      <c:if test="${sessionScope.id != null }">

                       <form method="post" action="../reply/reply_insert.do" class="inline">

						 <input type=hidden name=rdno value="${rvo.rdno }">

						 <input type=hidden name=type value="1">

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
              	
               <!--  <div style="height: 300px;width:280px; border:1px solid red">
                 </div> -->
    
<!--                 <div class="ad-card"> -->
<!--                    <div style="display"><span href="#" class="font-weight-bold">ADS</span></div> -->
<!--                     <input type="button" value="전체삭제" class="btn btn-sm btn-danger"> -->
<!--                 </div> -->
                <div class="ad-card" style="height: 500px">
                	<br><h3 class="">최근 본 레시피</h3> 
                   <div>
                   <!-- forEach -->
                   <c:forEach var="cvo" items="${clist }" varStatus="s">
                   	<c:if test="${s.index>=0 && s.index<3 }">
                   <table class="table">
                   		<tr>
                   		<td><a href="../list/recipeDetail.do?rdno=${cvo.rdno }">
                   		<img src="${cvo.poster }" style="width: 250px;height: 150px">
                   		</a>
                   		</td>
                   		</tr>
                   		<tr>
                   			<td>${cvo.title }</td>
                   		</tr>
                   		<!-- <input type="button" class="btn btn-xs btn-info" value="삭제"> -->
                   </table>
                   </c:if>
                   </c:forEach>
                   <!— forEach —>
                   </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>