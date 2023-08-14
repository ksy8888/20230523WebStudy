<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript" src="http://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
 let i=0;
 let u=0;
 let k=0;
 $(function() {
		$('#del').click(function() { 
			if(i==0) {
				$(this).text("취소"); 
				$('#delTr').show();
				i=1;
			}
			else {
				$(this).text("삭제");
				$('#delTr').hide();
				i=0;
			}	
		})
		
		$('#delBtn').click(function() {
			let pwd = $('#pwd1').val();
			let bno = $('#delBtn').attr("data-bno");
			
			if(pwd.trim()=="") {
				$('#pwd1').focus();
				return;
			}
			 console.log(pwd+bno)
			$.ajax({
				type:'post',
				url:'../board/board_delete.do',
				data: {"bno":bno, "pwd":pwd},
				success: function(result) {
					let res = result.trim();
					
					if(res=='NO') {
						alert("비밀번호가 틀립니다")
						$('#pwd1').val("")
						$('#pwd1').focus()
					} else {
						location.href="../board/list.do"
					}
				}
			})	
		})
		//댓글 수정 
		//<span class="btn btn-xs btn-success ups" data-no="${rvo.no }">수정</span>
		$('.ups').click(function() {
			let no = $(this).attr("data-no");
		$('.ups').text("수정");
		//$('.updates').hide();
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
		//대댓글
		$('.ins').click(function() {
			let no = $(this).attr("data-no");
			$('.ins').text("Reply");
			$('.reins').hide();
			$('.updates').hide();
			if(k==0) {
				$(this).text("취소");
				$('#k'+no).show();
				k=1;
			}
			else {
				$(this).text("⇒Reply");
				$('#k'+no).hide();
				k=0;
			}
		})
})
</script>

</head>
<body>
<div class="wrapper row3">
</div>
	<main class="container clear">
    <!-- <header class="py-5 text-center"> -->
        <h1 style="margin-top:50px;">게시판</h1>
   <!--  </header> -->
    <div class="row g-5">
        <section class="col-md-3 col-lg-4 order-md-last">
            <aside>
                <p>요리조리요리킹조리킹</p>
                <p><a href="https://github.com/http-ksy/yorijoriking">제작자들 깃주소</a></p>                              
                <img src="../board/image/bb.png"style="width:300px; height:300px;">
            </aside>
        </section>
        <article id="article-content" class="col-md-9 col-lg-8">
            <table class="table">
            <%--  bno,name,subject,content,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest  --%>
              <tr>
               <th width=20% class="text-center">번호</th>
               <td width=30% class="text-center">${vo.bno }</td>
		  	   <th width=20% class="text-center">작성일</th>
		  	   <td width=30% class="text-center">${vo.regdate }</td>
              </tr>
              <tr>
		  	  <th width=20% class="text-center">이름</th>
		  	  <td width=30% class="text-center">${vo.name }</td>
		  	  <th width=20% class="text-center">조회수</th>
		  	  <td width=30% class="text-center">${vo.hit }</td>
		  	 </tr>
		  	 <tr>
		  	  <th width=20% class="text-center">제목</th>
		  	  <td width=30% class="text-center">${vo.subject }</td>
<%--좋아요 --%>	
 	  
		  	  <th width=20% class="text-center">
		  	   <c:if test="${sessionScope.id==null }">
		  	    <span class="btn btn-xs"><img src="../board/image/nlike.png" style="width:25px; height:25px;" alt=""></span>
		  	   </c:if>
		  	   <c:if test="${sessionScope.id!=null }">
		  	    <c:if test="${like_count == 0 }">
		  	     <a href="../like/like_insert.do?bno=${vo.bno }" class="btn btn-xs"><img src="../board/image/nlike.png" style="width:25px; height:25px"></a>
		  	    </c:if>
		  	    <c:if test="${like_count != 0 }">
		  	     <a href="../like/like_delete.do?bno=${vo.bno }" class="btn btn-xs"><img src="../board/image/like1.png" style="width:25px; height:25px;" alt=""></a>
		  	     
		  	    </c:if>
		  	   </c:if>  	  
		  	  </th>
		  	  <td width=30% class="text-center">${vo.suggest }</td>
<%-- --%>		  	 
		  	 </tr>
		  	 		  				 
		  	 <tr>
		  	 <th class="text-center">내용</th>
		  	 <td colspan="4" class="text-left" valign="top" height="200">
		  	   <pre style="white-space:pre-wrap;border:none;background-color:white">${vo.content }</pre>
		  	  </td>
		  	 </tr>
		  	 
		  	 <c:if test="${vo.filename!=null }"> 
		  	 <tr>	  	
		  	  <th width=20% class="text-center">파일</th>
		  	  <td colspan="3" height="200"> 	
		  	  <img src="../board/image/${vo.filename }"  style="width:350px; height:350px; border-radius:15px;"/>
		  	  </td>
		  	 </tr>
		  	 </c:if>
		  	 <tr>
		  	 <td colspan="4" class="text-right">
		  	 <c:if test="${sessionScope.id != null}">
		  	   <c:if test="${sessionScope.id == vo.id }">			  	    		  	 	  	 
		  	     <a href="../board/board_update.do?bno=${vo.bno }" class="btn btn-xs btn-success" style="border-radius:15px;">수정</a>
		  	     <span class="btn btn-xs btn-success" id="del" style="border-radius:15px;">삭제</span>	  	   
		  	  </c:if>
		  	  </c:if>
		  	   
		  	   <a href="../board/list.do" class="btn btn-xs btn-warning" style="border-radius:15px;">목록</a>
			 </td>
			 
		  	 </tr>
		  	 <tr style="display:none" id="delTr">
		  	  <td colspan="4" class="text-right inline">
		  	  비밀번호:<input type=password name=pwd id=pwd1 size=10 class="input-sm">
		  	  	    <input type=button value="삭제" data-bno="${vo.bno }" class="btn btn-sm btn-primary" id="delBtn">
		  	  </td>
		  	 </tr>	  	 
            </table>
            
            
            <div class="card-footer">
              <h6 class="mt-5 mb-3 text-center"><a href="#" class="text-dark">총 댓글:&nbsp;${reply_count }개</a></h6>
                        <hr>
                       <div class="media">
                         <table class="table">
                           <tr>
                            <td>
                          <c:forEach var="rvo" items="${list }">
                                <table class="table">
                                 <tr>
                                  <td class="text-left">
       <%--답변이 있으면 --%>           <!-- <div class="media-body">  -->  
                                   <c:if test="${rvo.group_tab > 0 }">
                                     <c:forEach var="i" begin="1" end="${rvo.group_tab }">
		 			                  &nbsp;&nbsp;&nbsp;&nbsp;
		 			                </c:forEach>
		 			                <img src="../board/image/rep.png" style="width:20px; height:20px;">
                                   </c:if>       
                                   ●${rvo.name }&nbsp;(${rvo.dbday })
                                   <!-- </div> --> 
                                   </td>
                                   <td class="text-right">
                                     <c:if test="${sessionScope.id!=null }">	<%-- 로그인 되었고 --%>
			 					      <c:if test="${sessionScope.id == rvo.id }">	<%-- 본인이 썼는지 --%>
			 			                <span class="btn btn-xs btn-success ups" data-no="${rvo.no }" style="border-radius:15px;">수정</span>
			 			                <a href="../board/reply_delete.do?no=${rvo.no}&bno=${vo.bno}" class="btn btn-xs btn-info" style="border-radius:15px;">삭제</a>
			 			              </c:if>  
			 			             </c:if> 
                                   </td>
                                 </tr>
                                 <tr>
                                  <td>
                                   <pre>${rvo.msg }</pre>
                                   <c:if test="${rvo.filename!=null }"> 									  	  								  	  
								  	  <img src="../board/image/${rvo.filename }"  style="width:100px; height:100px; border-radius:15px;"/>
								  	 </c:if>								                                     
                                  </td>
                                  <td>
                                  <span class="text-dark small font-weight-bold ins" data-no="${rvo.no }"><img src="../board/image/reply.png" style="width:20px; height:20px;"></span>
                                  </td>
                                 </tr>  
                                   
 <%--대댓글 --%>                                
                                 <tr style="display:none" class="reins" id="k${rvo.no}"> <%--수정버튼 여러개 >> 구분 --%>
				 			          <td colspan="2">
								  	   <form method="post" action="../board/reply_reply_insert.do" class="inline">
								  	     <input type=hidden name=bno value="${vo.bno }"> <%-- 페이지 이동하는 변수 --%>
								  	 <%-- bno는 다시 detail.do로 이동 --%>    
								  	     <input type=hidden name=pno value="${rvo.no }"> <%-- rvo에 해당하는 msg를 변경 (수정대상)--%>
								  	     <textarea rows="5" cols="60" name="msg" class="form-control"></textarea>
										 <input type=submit value="댓글쓰기" class="btn btn-primary btn-block">
								  	   </form>
								  	  </td>
								  </tr>  
 <%-- --%>                               
                                 
                                  <tr style="display:none" class="updates" id="u${rvo.no}"> <%--수정버튼 여러개 >> 구분 --%>
				 			          <td colspan="2">
								  	   <form method="post" action="../board/reply_update.do" class="inline">
								  	     <input type=hidden name=bno value="${vo.bno }"> <%-- 페이지 이동하는 변수 --%>
								  	 <%-- bno는 다시 detail.do로 이동 --%>    
								  	     <input type=hidden name=no value="${rvo.no }"> <%-- rvo에 해당하는 msg를 변경 (수정대상)--%>
								  	     <textarea rows="5" cols="60" name="msg" class="form-control">${rvo.msg }</textarea>
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
                       <form method="post" action="../board/reply_insert.do" class="inline" enctype="multipart/form-data">
						 <input type=hidden name=bno value="${vo.bno }">
                         <textarea rows="5" cols="60" name="msg" class="form-control" placeholder="댓글을 작성해주세요."></textarea>
                         <input type="file" name="upload"  name="fileName">                                                
                         <input type=submit class="btn btn-primary btn-block" value="댓글쓰기">             
                        </form>
                      </c:if>
<%-- --%>                         
                 </div>
                 <div class="col-sm-4">
	  	
	  			</div>
         </article>
         
    </div>

    <div class="row g-5">
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&lt;</span>
                        <span class="sr-only">이전글</span>
                    </a>
                </li>

                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&gt; </span>
                        <span class="sr-only">다음글</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div> 
</main>
</body>
</html>