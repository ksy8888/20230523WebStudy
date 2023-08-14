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
                스토어
            </h2>
             <a href="../product/productList.do?type=0" class="btn btn-lg  b1"><b>전체</b></a>
            <a href="../product/productList.do?type=1" class="btn btn-lg  b1"><b>특가</b></a>
            <a href="../product/productList.do?type=2" class="btn btn-lg b1"><b>신상</b></a>
            <a href="../product/productList.do?type=3" class="btn btn-lg b1"><b>베스트</b></a>
        </div>
        <hr>
        <!-- for -->
        <div class="row tm-mb-90 tm-gallery">
        <c:forEach var="plist" items="${plist }" varStatus="s">
            <div class="col-xl-3 col-lg-4 col-md-6 col-sm-6 col-12 mb-5">
                <a href="../product/productDetail_before.do?pdno=${plist.pdno }"><figure class="effect-ming tm-video-item">
                    <img src="${plist.poster }" alt="Image" class="img-fluid" style="width:250px; height:150px; border-radius:20px;">
                    <figcaption class="d-flex align-items-center justify-content-center">
                    <h2>     
                    <c:if test="${plist.title.length()>10 }">
                    <span class="tm-text-gray-dark" >${plist.title.substring(0,10) }</span>
                    </c:if>
                    <c:if test="${plist.title.length()<=10 }">
                    <span class="tm-text-gray-dark" >${plist.title }</span>
                    </c:if>
                    </h2>
                    </figcaption>  
                                      
                </figure></a>
                <div class="d-flex justify-content-between tm-text-gray">
                    <span class="tm-text-gray-light">${plist.score }</span>
                  	<span class="tm-text-gray-light">${plist.first_pri }</span>
                    
                </div>
            </div>
            </c:forEach>
              <!-- for문 종료 -->
        </div> 
        <div class="row">
<%--   	 	  <a href="recipeList.do?type=${type}&page=${curpage>1?curpage-1:curpage }" class="btn btn-sm btn-info">이전</a> --%>
<%--   	 	  ${curpage } page / ${totalpage } pages --%>
<%--   	 	  <a href="recipeList.do?type=${type }&page=${curpage<totalpage?curpage+1:curpage }" class="btn btn-sm btn-info">다음</a> --%>
		<div class="text-center" style="margin-left:360px;">
			<ul class="pagination">
  	 	  <c:if test="${startpage>1 }">
  	 	  	<li><a href="productList.do?type=${type }&page=${startpage-1 }" style="font-size:30px;">&lt;&nbsp;</a></li> 
  	 	  </c:if>
  	 	  <c:forEach var="i" begin="${startpage }" end="${endpage }">
  	 	     <c:if test="${curpage==i }">
  	 	     <li class="active"><a href="productList.do?type=${type }&page=${i}" style="font-size:30px;">${i }&nbsp;</a></li>
  	 	     </c:if>
  	 	     <c:if test="${curpage!=i }">
  	 	     <li class=""><a href="productList.do?type=${type }&page=${i}" style="font-size:30px;">${i }&nbsp;</a></li>
  	 	     </c:if>
  	 	  </c:forEach>
  	 	   <c:if test="${endpage<totalpage }">
  	 	    <li><a href="productList.do?type=${type }&page=${endpage+1 }" style="font-size:30px;">&gt;</a></li>
  	 	  </c:if>
  	 	  	</ul>
  	 	  </div>
        </div>
      </div> 
        <!-- row -->
</body>
</html>