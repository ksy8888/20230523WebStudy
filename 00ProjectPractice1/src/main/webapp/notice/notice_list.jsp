<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

</style>
</head>
<body>
	<div class="wrapper row3">
	  <main class="container clear"> 
		<h2 class="sectiontitle">공지사항</h2>
		
			<table class="table table-hover" id="article-table">
				<tr>
					<td></td>
				</tr>
				<tr class="table-success">
					<th width=10% class="text-center">번호</th>
					<th width=20% class="text-center">제목</th>
					<th width=20% class="text-center">작성자</th>
					<th width=10% class="text-center">작성일</th>
					<th width=10% class="text-center">조회수</th>
					
				</tr>

				<c:forEach var="vo" items="${list}">
					<tr>
				       <td width=10% class="text-center">${vo.no }</td>
				       <td width=35%><a href="../notice/notice_detail.do?no=${vo.no}" style="color:black"><p style="color:red">[${vo.notice_type}]</p>&nbsp;${vo.subject }</a></td>
				       <td width=20% class="text-center">관리자</td>
				       <td width=10% class="text-center">${vo.dbday }</td> 
				       <td width=10% class="text-center">${vo.hit }</td>  				       
				    </tr>
				</c:forEach>
			
			</table>
			</main>
			</div>



	 <div class="container">
		<div class="row">
			<div class="justify-content-center">
				<nav id="pagination" aria-label="Page navigation"
					style="margin-left: 400px;">
					<ul class="pagination justify-content-center">
						<c:if test="${curpage>1 }">
							<li class="page-item"><a class="page-link"
								href="../admin/admnotice_list.do?page=${curpage>1?curpage-1:curpage }">Previous</a></li>
						</c:if>

						<c:forEach var="i" begin="${startpage }" end="${endpage }">
							<c:if test="${i==curpage }">
								<li class="page-item"><a class="active" class="active"
									href="../admin/admnotice_list.do?page=${i }">${i }</a></li>
							</c:if>
							<c:if test="${i!=curpage }">
								<li class="page-item"><a class="page-link" class="active"
									href="../admin/admnotice_list.do?page=${i }">${i }</a></li>
							</c:if>
						</c:forEach>

						<c:if test="${curpage<totalpage }">
							<li class="page-item"><a class="page-link"
								href="../admin/admnotice_list.do?page=${curpage<totalpage?curpage+1:curpage }">Next</a></li>
						</c:if>
					</ul>
				</nav>
			</div>

		</div>

	</div> 
</body>
</html>