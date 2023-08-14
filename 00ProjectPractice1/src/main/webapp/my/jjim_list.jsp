<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
$(function(){
	$('.cancel').click(function(){
		let no =$('.titleno').attr("data-no")
		$.ajax({
			 type:'post',
			 url:'../jjim/jjim_delete.do',
			 data:{'no':no},
			 success:function(result)
			 {
				 alert('찜하기 cancle')
				 location.reload();
			 }
		})
	})
})
</script>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>

  <div class="container ">
  <h2 class="sectiontitle">${sessionScope.name }님의 찜 목록</h2>
  <div class="text-left">
    <a class="btn btn-xs " href="../jjim/jjim_list.do?type=1"  >레시피</a>
    <a class="btn btn-xs " href="../jjim/jjim_list.do?type=2"  >맛집</a>
    <a class="btn btn-xs " href="../jjim/jjim_list.do?type=3"  >스토어</a>
    <a class="btn btn-xs " href="../jjim/jjim_list.do?type=4"  >공유주방</a>
  </div>
  <hr>
  
  
   <table class="table" style="width: 1000px;">
   <tr>
   	<th class="text-center">번호</th>
   	<th class="text-center"></th>
   	<th class="text-center">제목</th>
   	<c:if test="${type==1 }">
   	<th class="text-center">쉐프</th>
   </c:if>
   <c:if test="${type==2 }">
   	<th class="text-center">전화</th>
   </c:if>
   <c:if test="${type==3 }">
   	<th class="text-center">가격</th>
   </c:if>
   <c:if test="${type==4 }">
   	<th class="text-center">가격</th>
   </c:if>
   	<th ></th>
   </tr>
   <c:forEach var="vo" items="${list}">
     <tr>
    <td class="text-center titleno" data-no="${vo.no }">${vo.no }</td>
    <c:if test="${type==1 }">
   	<td class="text-center">
   	<a href="../list/recipeDetail.do?rdno=${vo.cno }"><img src="${vo.poster }" style="width: 30px;height: 30px"></a>
   	</td>
   	<td class="text-center"><a href="../list/recipeDetail.do?rdno=${vo.cno }">${vo.title }</a></td>
   	</c:if>
   	<c:if test="${type==2 }">
   	<td class="text-center">
   	<a href="../food/foodDetail.do?fdno=${vo.cno }"><img src="${vo.poster }" style="width: 30px;height: 30px"></a>
   	</td>
   	<td class="text-center"><a href="../food/foodDetail.do?fdno=${vo.cno }">${vo.title }</a></td>
   	</c:if>
   	<c:if test="${type==3 }">
   	<td class="text-center">
   	<a href="../product/productDetail.do?pdno=${vo.cno }"><img src="${vo.poster }" style="width: 30px;height: 30px"></a>
   	</td>
   	<td class="text-center"><a href="../product/productDetail.do?pdno=${vo.cno }">${vo.title }</a></td>
   	</c:if>
   	<c:if test="${type==4 }">
   	<td class="text-center">
   	<a href="../share/shareDetail.do?skdno=${vo.cno }"><img src="${vo.poster }" style="width: 30px;height: 30px"></a>
   	</td>
   	<td class="text-center"><a href="../share/shareDetail.do?skdno=${vo.cno }">${vo.title }</a></td>
   	</c:if>
   	<c:if test="${type==1 }">
   	<td class="text-center">${vo.chef }</td>
    </c:if>
    <c:if test="${type==2 }">
   	<td class="text-center">${vo.tel }</td>
    </c:if>
    <c:if test="${type==3 }">
   	<td class="text-center">${vo.price }</td>
    </c:if>
    <c:if test="${type==4 }">
   	<td class="text-center">${vo.price }</td>
    </c:if>
   		<td>
   	   <a class="btn btn-xs btn-success cancel">취소</a>
   		</td>
     </tr>
   </c:forEach>
   </table>
   
<div class="text-center" style="margin-left:360px;">
			<ul class="pagination">
  	 	  <c:if test="${startpage>1 }">
  	 	  	<li><a href="jjim_list.do?type=${type}&page=${startpage-1 }" style="font-size:30px;">&lt;&nbsp;</a></li> 
  	 	  </c:if>
  	 	  <c:forEach var="i" begin="${startpage }" end="${endpage }">
  	 	     <c:if test="${curpage==i }">
  	 	     <li class="active"><a href="jjim_list.do?type=${type}&page=${i}" style="font-size:30px;">${i }&nbsp;</a></li>
  	 	     </c:if>
  	 	     <c:if test="${curpage!=i }">
  	 	     <li class=""><a href="jjim_list.do?type=${type}&page=${i}" style="font-size:30px;">${i }&nbsp;</a></li>
  	 	     </c:if>
  	 	  </c:forEach>
  	 	   <c:if test="${endpage<totalpage }">
  	 	    <li><a href="jjim_list.do?type=${type}&page=${endpage+1 }" style="font-size:30px;">&gt;</a></li>
  	 	  </c:if>
  	 	  	</ul>
  	 	  </div>
</div>
</body>
</html>