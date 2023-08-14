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
<br> 
	 <div class="container">
        <div class="container">
        
            <h2 class="col-6 tm-text-primary">
                맛집
                
            </h2>
            
<!--             <input type="button" value="전체보기" class="btn btn-xs b1"> -->
             <a href="../food/foodList.do?type=0" class="btn btn-lg b1"><b>전체</b></a>
            <a href="../food/foodList.do?type=1" class="btn btn-lg b1"><b>한식</b></a>
            <a href="../food/foodList.do?type=2" class="btn btn-lg b1"><b>양식</b></a>
            <a href="../food/foodList.do?type=3" class="btn btn-lg b1"><b>일식</b></a>
            <a href="../food/foodList.do?type=4" class="btn btn-xs b1"><b>중식</b></a>
            <a href="../food/foodList.do?type=5" class="btn btn-xs b1"><b>디저트</b></a>
            
        </div>
        <hr>
        <!-- forEachh -->
         <div class="row tm-mb-90 tm-gallery">
        <c:forEach var="flist" items="${flist }">
        	<div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <figure class="effect-ming tm-video-item">
              	  <a href="../food/foodDetail_before.do?fdno=${flist.fdno }">
                    <img src="${flist.poster }" alt="Image" class="img-fluid rounded" style="width:250px; height:150px; border-radius:20px;">
                    <figcaption class="d-flex align-items-center justify-content-center">
                        <h2>
                        <c:if test="${flist.title.length()>10 }">
                          	 ${flist.title.substring(0,10) }
                          </c:if>
                        
                          <c:if test="${flist.title.length()<10 }">
                          	 ${flist.title }
                          </c:if>
                        </h2>
                       
                    </figcaption>   
                     </a>                 
                </figure>
                <div class="d-flex justify-content-between tm-text-gray">
                	<span>${flist.score }</span> 	 
                    <span>${flist.price }</span>
                </div>
            </div>  
            </c:forEach>
            <!-- forEach end -->
        </div> 
        <div class="row">
<%--   	 	  <a href="recipeList.do?type=${type}&page=${curpage>1?curpage-1:curpage }" class="btn btn-sm btn-info">이전</a> --%>
<%--   	 	  ${curpage } page / ${totalpage } pages --%>
<%--   	 	  <a href="recipeList.do?type=${type }&page=${curpage<totalpage?curpage+1:curpage }" class="btn btn-sm btn-info">다음</a> --%>
		<div class="text-center" style="margin-left:360px;">
			<ul class="pagination">
  	 	  <c:if test="${startpage>1 }">
  	 	  	<li><a href="foodList.do?type=${type}&page=${startpage-1 }" style="font-size:30px;" >&lt;&nbsp;</a></li> 
  	 	  </c:if>
  	 	  <c:forEach var="i" begin="${startpage }" end="${endpage }">
  	 	     <c:if test="${curpage==i }">
  	 	     <li class="active"><a href="foodList.do?type=${type}&page=${i}" style="font-size:30px;" >${i }&nbsp;</a></li>
  	 	     </c:if>
  	 	     <c:if test="${curpage!=i }">
  	 	     <li class=""><a href="foodList.do?type=${type}&page=${i}" style="font-size:30px;" >${i }&nbsp;</a></li>
  	 	     </c:if>
  	 	  </c:forEach>
  	 	   <c:if test="${endpage<totalpage }">
  	 	    <li><a href="foodList.do?type=${type}&page=${endpage+1 }" style="font-size:30px;" >&gt;</a></li>
  	 	  </c:if>
  	 	  	</ul>
  	 	  </div>
        </div>
       </div>
        <!-- row -->
</body>
</html>