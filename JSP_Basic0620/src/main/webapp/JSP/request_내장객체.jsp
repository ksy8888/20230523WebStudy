
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%--
	JSP
	---
		1. 동작 순서
		   ------------------------------------------------
		   1) client 요청 (주소창을 이용해서 서버에 연결)
		   	  http://localhost:8080/JSP_Basic0620/JSP/request_%EB%82%B4%EC%9E%A5%EA%B0%9D%EC%B2%B4.jsp
		   	  ----	 --------- ---- ------------
		   	protocol  serverIP port   ContextPath
		   	  --------------------  -----------------------------------------------------------------
		   	  		서버관련					클라이언트 요청 관련 // URI *사용자가 어떤 파일을 요청했는지 확인	   	  		
		   	  ---------------------------------------------------------------------------------------- URL
		   2) DNS를 거쳐서 localhost(도메인) => ip 변경
		   3) ip/port를 이용해서 서버에 연결
		   	  new Socket(ip,port) => TCP
		   ----------------------------------------	  
		   4) Web Server	  	  					
		      httpd
		      -----
		      	= HTML,XML,CSS,JSON => Web Server 자체에서 처리후에 브라우저로 전송
		      	= JSP / Servlet은 처리하지 못한다
		      	  -------------------------
		      	  		|
		      	  	Web Container (WAS) => Java로 변경
		      	  						=> 컴파일
		      	  						=> 실행
		      	  						   ----
		      	  						     실행 결과를 메모리에 모아둔다
		   5) 메모리에 출력한 내용을 브라우저로 응답 
		   
		JSP (Java Server Page) : 서버에서 실행되는 자바파일
		-------------------------------------------
			_jspInit() => web.xml => 초기화
			_jspService() => 사용자요청을 처리하고 결과값을 HTML로 전송
			------------- 공백
			{
				영역에 소스 코딩 => JSP
			}
			_jspDestroy() => 새로고침, 화면 이동 ... 메모리에서 해제  
			
			
	public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException 
      {		
			 final javax.servlet.jsp.PageContext pageContext;
		    javax.servlet.http.HttpSession session = null;
		    final javax.servlet.ServletContext application;
		    final javax.servlet.ServletConfig config;
		    javax.servlet.jsp.JspWriter out = null;
		    final java.lang.Object page = this;
		    javax.servlet.jsp.JspWriter _jspx_out = null;
		    javax.servlet.jsp.PageContext _jspx_page_context = null;
		    
		    소스 코딩 영역
		    ==> JSP
      }  	  						     
		      	
		2. 지시자
			page 형식) <%@ page 속성="" 속성="" %>
			----
			  JSP 파일에 대한 정보
			  속성:
			  	contentType=""
			  		= 브라우저에 어떤 파일인지 알려준다
			  		  ----- HTML / XML / JSON (외에는 일반 텍스트)
			  	
			  	import: 라이브러리 읽기
			  	import="java.util.*,java.io.*"
			  	errorPage: error시에 이동하는 페이지 지정
			  	buffer="8kb" => 16kb 32kb...	  
		3. 스크립트 사용법
			자바가 코딩되는 영역 
			<%! %> : 선언문 (메소드 , 멤버변수) => 사용빈도 거의 없다
			<% %> : 자바 코딩(일반 자바) => 제어문 , 메소드 호출 , 지역변수 ..
			<%= %> : 화면 출력
					 out.println(여기에 들어가는 코딩)
			JSP = Model1은 2003유행 
			
			=> 표현식, 스크립트릿
			   ${}	   JSTL
		4. 내장객체
			=> 171 page
			9가지 지원
				******= request => HttpServletRequest
				request는 관리자 => 톰캣
				1) 서버정보 / 클라이언트 브라우저 정보
					getServerInfo()
					getPort()
					getMethod()
					getProtocol()
					***getRequestURL()
					***getRequestURI()
					***getContextPath()
				
				2) 사용자 요청 정보
					데이터 전송시 => 데이터가 request에 묶여서 들어온다
					= 단일 데이터
					getParameter() >> 실제값 가져올때
					= 다중 데이터
					getParameterValues() -> checkbox, select => multiline
					Ex) a.jsp?no=1&name=aaa&hobby=a&hobby=b&hobby=c
											-----------------------
					= 한글 변환 (디코딩)
					setCharacterEncoding => UTF-8
					= 키를 읽는다
					getParameterNames() >> "키값만"
					받는파일명?no=1&name=aaa
					-------
					a.jsp?no=1&name=aaa
				3) 추가 정보 => MVC
					setAttribute() : request 데이터 추가전송
					getAttribute() : 전송된 데이터 읽기
				*****= response => HttpServletResponse
				*****= session => HttpSession
				**= out => JspWriter
				***= application => ServletContext
				*****= pageContext => PageContext
				= page => Object	(this)
				= exception => Exception	=> try~catch
				= config => ServletConfig => web.xml
				
			-------------------------------------------------------	
				페이지 입출력
					request, response, out
			-------------------------------------------------------	
				외부 환경 정보	
					config
			-------------------------------------------------------	
				서블릿 관련
					application , pageContext , session
			-------------------------------------------------------	
				예외처리 관련
					exception
			-------------------------------------------------------	
		5. 액션 태그
		6. include
		7. cookie
		8. JSTL
		9. EL
		10. MVC
		
		String a = request.getParameter("a");
		String b = request.getParameter("b");
		String c = request.getParameter("c");
		String d = request.getParameter("d");
		
		<jsp:setProperty name="vo" property="*"/>   	>> 모든 데이터 한번에 받기_ property에 *
		
 --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<title>Insert title here</title>
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
 <style type="text/css">
.container-fluid {
	margin-top: 50px;
}
.row {
	margin:0px auto;
	width: 100%;
}
h1 {
	text-align: center
}

</style>
</head>
<body>
	<div class="container">
		<h1>개인 정보</h1>
		<div class="row">
			<form method=post action="request_ok.jsp">
			<table class="table">
				<tr>
				<th class="text-center" width=20%>이름</th>
					<td width=80%>
					 <input type=text name=name size=15 class="input-sm">
					</td>
				</tr>
				<tr>
				<th class="text-center" width=20%>성별</th>
					<td width=80%>
					<%-- 라디오버튼은 반드시 그룹 (name동일) --%>
					 <input type=radio name=sex value="남자" checked>남자
					 <input type=radio name=sex value="여자">여자
					</td>
				</tr>
				<tr>
				<th class="text-center" width=20%>전화번호</th>
					<td width=80%>
					<%--
						getParameter("tel") => name="tel"
					 --%>
						<select name="tel" class="input-sm">
							<option>010</option>
						</select>
						<input type=text name=tel2 size=15 class="input-sm">
					</td>				
				</tr>
				
				<tr>
				<th class="text-center" width=20%>소개</th>
					<td width=80%>
					 <textarea rows="8" cols="50" name="content"></textarea>
					</td>
				</tr>
				<tr>
				<th class="text-center" width=20%>취미</th>
					<td width=80%>
					 <input type="checkbox" name="hobby" value="운동">운동
					 <input type="checkbox" name="hobby" value="등산">등산
					 <input type="checkbox" name="hobby" value="낚시">낚시
					 <input type="checkbox" name="hobby" value="게임">게임
					 <input type="checkbox" name="hobby" value="공부">공부
					 <input type="checkbox" name="hobby" value="자전거">자전거
					 <input type="checkbox" name="hobby" value="여행">여행
					 <input type="checkbox" name="hobby" value="컴퓨터">컴퓨터
					</td>
				</tr>
			   <tr>
			   	<td colspan="2" class="text-center">
			   		<button class="btn btn-sm btn-danger">전송</button>
			   	</td>
			   </tr>
			</table>
			</form>
		</div>
	</div>
</body>
</html>