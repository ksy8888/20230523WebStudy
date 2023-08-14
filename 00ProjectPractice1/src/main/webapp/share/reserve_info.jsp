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
	<table class="table">
	<thead><h3>예약정보</h3></thead>
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
  			</table>
  			<table class="table">
  				
  				<tr>
  					<th width="20%" class="text-center">예약번호</th>
  					<td width="30%" class="text-center">${rsvo.sno }</td>
  					<th width="20%" class="text-center">예약일</th>
  					<td width="30%" class="text-center">${rsvo.srday }</td>
  				</tr>
  				<tr>
  					<th width="20%" class="text-center">예약시간</th>
  					<td width="30%" class="text-center">${rsvo.srtime }</td>
  					<th width="20%" class="text-center">예약인원</th>
  					<td width="30%" class="text-center">${rsvo.sinwon }</td>
  				</tr>
  				<tr>
  					<th width="20%" class="text-center">등록일</th>
  					<td colspan="3" class="text-left">${rsvo.sdbday }</td>
  				</tr>
  			</table>
  	</table>
</body>
</html>