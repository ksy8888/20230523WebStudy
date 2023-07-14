<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

</head>
<body>
  <div class="wrap">
   <main>
     <section id="vert-nav">
      <h3>마이페이지</h3>
       <nav role='navigation'>
        <ul class="topmenu">
          <li><a href="#0"><i class="entypo-home"></i>마이페이지 홈</a></li>
          <li><a href="#0"><i class="entypo-user"></i>회원수정</a></li>
          <li><a href="#0"><i class="entypo-user"></i>회원탈퇴</a></li>
          <li><a href="#0"><i class="entypo-brush"></i>자유게시판</a></li>
          <li><a href="#0"><i class="entypo-brush"></i>묻고답하기</a></li>
          <li><a href="../mypage/mypage_reserve.do"><i class="entypo-vcard"></i>맛집예약</a></li>
          <li><a href="#0"><i class="entypo-vcard"></i>장바구니</a></li>
          <li><a href="#0"><i class="entypo-vcard"></i>구매내역</a></li>
          
           <!-- DAO에서 id 들어오는것 확인 >> 아이디는 session에 저장되어있으니 데이터보낼게 없음 -->
           <!--  DAO의 매개변수를 확인하면 ?뒤에 어떤 변수를 넘겨줄지 확인 가능하다 -->
          <li><a href="../mypage/mypage_jjim_list.do"><i class="entypo-vcard"></i>찜목록</a></li>
        </ul>
      </nav>  
    </section>
   </main>
  </div>
  
</body>
</html>