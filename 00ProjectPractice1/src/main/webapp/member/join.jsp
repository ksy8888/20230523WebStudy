<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
   <meta name="description" content="Start your development with JoeBLog landing page.">
   <meta name="author" content="Devcrud">
   <title>JoeBLog | Components</title>
   <!-- font icons -->
   <link rel="stylesheet" href="../assets/vendors/themify-icons/css/themify-icons.css">
   <!-- Bootstrap + Steller  -->
   <link rel="stylesheet" href="../assets/css/joeblog.css">
   
   <link rel="stylesheet" href="../shadow/css/shadowbox.css">
   
   
<style type="text/css">
/* .container{
	margin-top: 50px;
} */
.row1{
	margin: 0px auto;
	width:700px;
} 
h1{
	text-align: center;
	font-family: 'Noto Sans KR', sans-serif;
}
</style>
<script type="text/javascript" src="../shadow/js/shadowbox.js"></script>

<!-- import -->
<script type="text/javascript" src="https://code.jquery.com/jquery.js"></script>
<script type="text/javascript">
function check_pwd(){
	if(document.getElementById('pwd1').value !='' && document.getElementById('pwd2').value!=''){
        if(document.getElementById('pwd1').value==document.getElementById('pwd2').value){
            document.getElementById('check').innerHTML='비밀번호가 일치합니다.'
            document.getElementById('check').style.color='blue';
        }
        else{
            document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
            document.getElementById('check').style.color='red';
        }
	}
}
function fn_dbCheckId(){
	let id= $('#id').val()
	$.ajax({
			type:'post',
			url:'../member/idcheck_ok.do',
			data:{"id":id},
			success:function(result) // result(실행결과 읽기) => callback
			{
				let count=Number(result.trim());
				if(count==0)
				{
					$('#res').html(
					 '<span style="color:blue">'
					 +id+"는(은) 사용 가능한 아이디입니다!!</span>"
					)			
				}
				else if(count==1)
				{
					$('#res').html(
							 '<span style="color:red">'
							 +id+"는(은) 이미 사용중인 아이디입니다!!</span>"
							)
					$('#id').val("")
					$('#id').focus()
				}
				else
				{
					$('#res').html(
							 '<span style="color:purple">공백은 사용할 수 없습니다!!</span>'
							)
					$('#id').val("")
					$('#id').focus()
				}	
				
			}
		})
	
}
function fn_dbCheckNick(){
	let nickname= $('#nickname').val()
	$.ajax({
			type:'post',
			url:'../member/nicknamecheck_ok.do',
			data:{"nickname":nickname},
			success:function(result) // result(실행결과 읽기) => callback
			{
				let count=Number(result.trim());
				if(count==0)
				{
					$('#res2').html(
					 '<span style="color:blue">'
					 +nickname+"는(은) 사용 가능한 닉네임입니다!!</span>"
					)
									
				}
				else if(count==1)
				{
					$('#res2').html(
							 '<span style="color:red">'
							 +nickname+"는(은) 이미 사용중인 닉네임입니다!!</span>"
							)
					$('#nickname').val("")
					$('#nickname').focus()
				}
				else
				{
					$('#res2').html(
							 '<span style="color:purple">공백은 사용할 수 없습니다!!</span>'
							)
					$('#nickname').val("")
					$('#nickname').focus()
				}	
			}
		})
	
}
function fn_checkEmail(){
	let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
	let email=$('#email1').val()+"@"+$('#email2').val();
		if(regex.test(email))
		{
			// 형식이 맞으면
			$.ajax({
			type:'post',
			url:'../member/emailcheck_ok.do',
			data:{"email":email},
			success:function(result) // result(실행결과 읽기) => callback
			{
				let count=Number(result.trim());
				if(count==0)
				{
					$('#res3').html(
					 '<span style="color:blue">'
					+email+"는(은) 사용 가능한 이메일입니다!!</span>"
					)
									
				}
				else if(count==1)
				{
					$('#res3').html(
							 '<span style="color:red">'
							+email+"는(은) 이미 사용중인 이메일입니다!!</span>"
							)
					$('#email1').val("")
					$('#email2').val("")
				}
				
			}
		})
	}
		else
		{
			$('#res3').html(
					 '<span style="color:red">'
					 +"이미 사용중인 이메일입니다!!</span>"
					)
			$('#email1').val("")
			$('#email2').val("")
		}

}
</script>
<script>
    $(function() {
        $('#select').change(function() {
            if ($('#select').val() == 'directly') {
                $('#email2').attr("disabled", false);
                $('#email2').val("");
                $('#email2').focus();
            } else {
                $('#email2').val($('#select').val());
            }
        })
    });
    
    function checkNumber(event) {
    	  if(event.key === '.' 
    	     || event.key === '-'
    	     || event.key >= 0 && event.key <= 9) {
    	    return true;
    	  }
    	  
    	  return false;
    	}
function fn_phone(){
	let phone=$('#phone1').val()+"-"+$('#phone').val();
	if(phone=="" || phone.length!=12)
	{
		$('#res4').html(
				'<span style="color:purple">사용할수없는번호입니다!!</span>'
				)
		$('#phone').val("")
		$('#phone').focus();
	}
	else
	{
		// 형식이 맞으면
		$.ajax({
		type:'post',
		url:'../member/phonecheck_ok.do',
		data:{"phone":phone},
		success:function(result) // result(실행결과 읽기) => callback
		{
			let count=Number(result.trim());
			if(count==0)
			{
				$('#res4').html(
				 '<span style="color:blue">'
				+phone+"는(은) 사용 가능한 전화번호입니다!!</span>"
				)		
			}
			else if(count==1)
			{
				$('#res4').html(
						 '<span style="color:red">'
						+phone+"는(은) 이미 사용중인 전화번호입니다!!</span>"
						)
				$('#phone').val("");
				$('#phone').focus();
			}
			
		}
	})
  }
	
}
</script>
<script>
Shadowbox.init({
	players:['iframe']
})
$(function(){
	$('#postBtn').click(function(){
		Shadowbox.open({
			content:'../member/postfind.do',
			player:'iframe',
			width:500,
			height:350,
			title:'우편번호 검색'
		})
	})
})
</script>
<script type="text/javascript">
function check(){
	/* 아이디 유효성 검사 */
	if(joinFrm.id.value.length == 0){ // myform.id.value == "" 이것도 가능
		alert("아이디가 누락됐습니다.");
		joinFrm.id.focus(); // 포커스를 이동시켜 바로 아이디를 입력할 수 있게
		return false;
	}
	
	/* 비밀번호 및 비밀번호 확인 유효성 검사 */
	if(joinFrm.pwd1.value.length == 0){
		alert("비밀번호가 누락됐습니다.");
		joinFrm.pwd1.focus(); // 포커스를 이동시켜 바로 비밀번호를 입력할 수 있게
		return false;
	}
	
	if(joinFrm.pwd2.value.length == 0){
		alert("비밀번호확인이 누락됐습니다.");
		joinFrm.pwd2.focus(); 
		return false;
	}
	
	if(joinFrm.pwd1.value != joinFrm.pwd2.value){
		alert("비밀번호를 재확인하세요.");
		joinFrm.pwd2.select(); //이걸로 하면 focus 이동하면서 입력한게 블록으로 선택됨
		return false;
	}
	
	/* 이름 유효성 검사 */
	if(joinFrm.name.value.length == 0){
		alert("이름이 누락됐습니다.");
		joinFrm.name.focus(); 
		return false;
	}
	
	/* 닉네임 유효성 검사 */
	if(joinFrm.nickname.value.length == 0){
		alert("닉네임이 누락됐습니다.");
		joinFrm.nickname.focus(); 
		return false;
	}
	
	/* 생년월일 유효성 검사 */
	if(joinFrm.birthday.value.length == 0){
		alert("생년월일이 누락됐습니다.");
		joinFrm.birthday.focus(); 
		return false;
	}
	
	/* 전화번호 유효성 검사 */
	if(joinFrm.phone2.value.length == 0){
		alert("전화번호가 누락됐습니다.");
		joinFrm.phone2.focus(); 
		return false;
	}
	
	/* 이메일 유효성 검사 */
	if(joinFrm.email1.value.length == 0){
		alert("이메일이 누락됐습니다.");
		joinFrm.email1.focus(); 
		return false;
	}
	if(joinFrm.email2.value.length == 0){
		alert("이메일이 누락됐습니다.");
		joinFrm.email2.focus(); 
		return false;
	}	
	
	/* 주소 유효성 검사 */
	if(joinFrm.post.value.length == 0){
		alert("우편번호가 누락됐습니다.");
		joinFrm.post.focus(); 
		return false;
	}
	if(joinFrm.addr1.value.length == 0){
		alert("주소가 누락됐습니다.");
		joinFrm.addr1.focus(); 
		return false;
	}
	if(joinFrm.id!=0 && joinFrm.pwd1!=0 && joinFrm.pwd2!=0 && joinFrm.name!=0 
			&& joinFrm.nickname!=0 && joinFrm.birthday!=0 && joinFrm.phone2!=0 
			&& joinFrm.email1!=0 && joinFrm.email2!=0&& joinFrm.post!=0&& joinFrm.addr1!=0){
		
		alert("회원가입을 축하합니다!!");
		
	}
		
}
$(function(){
	$('#joinBtn').click(function(){
		$('#joinFrm').submit();
		
	})
})
</script>
</head>
<body>
   <header class="header header-mini"> 
      <div class="header-title">회원가입</div> 
      <nav aria-label="breadcrumb">
      </nav>
   </header>

   <!-- main content -->
   <div class="container">

      <!-- Inputs -->
      <div class="row row1">
        <form method=post action="../member/join_ok.do" name="joinFrm" id="joinFrm">      
	      <table class="table">
	        <tr>
	          <th class="text-left" width=30%>아이디</th>
	          <td width=70%>
	            <input type=text name=id id="id" onkeydown="inputIdChk()" class=input-sm" size=15 placeholder="ID를 입력하세요">
	            <button type=button onclick="fn_dbCheckId()" name=dbCheckId class=checkId >중복확인</button>
	          </td>
	        </tr>
	        <tr>
	          <td id=res colspan="2" class="text-right"></td>
	        </tr>
	        <tr>
	          <th class="text-left" width=30%>비밀번호</th>
	          <td width=70%>
	            <input type=password name=pwd id=pwd1 onchange="check_pwd()" class=input-sm" size=20>
	          </td>
	        </tr>
	        <tr>
	          <th class="text-left" width=30%>비밀번호 확인</th>
	          <td width=70%>
	            <input type=password name=pwd id=pwd2 onchange="check_pwd()" class=input-sm" size=20><br><span id="check"></span>
	          </td>
	        </tr>
	        <tr>
	          <th class="text-left" width=30%>이름</th>
	          <td width=70%>
	            <input type=text name=name class=input-sm" size=15>
	          </td>
	        </tr>
	        <tr>
	          <th class="text-left" width=30%>닉네임</th>
	          <td width=70%>
	            <input type=text name=nickname id="nickname" class=input-sm" size=15>
	            <button type=button onclick="fn_dbCheckNick()" name=dbCheckNick class=checkNick>중복확인</button>
	          </td>
	        </tr>
	        <tr>
	          <td id=res2 colspan="2" class="text-right"></td>
	        </tr>
	        <tr>
	          <th class="text-left" width=30%>성별</th>
	          <td width=70% class="inline">
	            <input type=radio name=sex value="남자" checked>남자
              <input type=radio name=sex value="여자">여자
	          </td>
	        </tr>
	        <tr>
	          <th class="text-left" width=30%>생년월일</th>
	          <td width=70%>
	            <input type=date id="date" name=birthday class=input-sm">
	          </td>
	        </tr>
	        
	        <tr>
            <th class="text-left" width=30%>전화</th>
            <td width=70%>
              <select name=phone1 id="phone1" class="input-sm">
                <option>010</option>
              </select>
              <input type=text name=phone2 id="phone" size=12 class="input-sm" onkeypress='return checkNumber(event)' maxlength="8">
              <input type=button value="전화체크" class="btn btn-sm btn-warning" id="phoneBtn" onclick="fn_phone()">
            </td>
          </tr>
            <tr>
	          <td id=res4 colspan="2" class="text-right"></td>
	        </tr>
	        <tr>
	          <th class="text-left" width=30%>이메일</th>
	          <td width=70%>
	            <input type=text name="email1" class="input-sm" id="email1" size=20>
	            <span>@</span>
	            <input type=text name="email2" class="input-sm" id="email2" size=20 placeholder="이메일을 선택하세요">
	            
	            	<select id="select">
	            		<option value="" disabled selected>E-mail 선택</option>
	            		<option value="directly" id="textEmail">직접 입력하기</option>
	                    <option value="naver.com" id="naver.com">naver.com</option>
	                    <option value="hanmail.net" id="hanmail.net">hanmail.net</option>
	                    <option value="gmail.com" id="gmail.com">gmail.com</option>
	                    <option value="nate.com" id="nate.com">nate.com</option>
                    </select>
                 <input type=button value="이메일체크" class="btn btn-sm btn-danger" onclick="fn_checkEmail()">
	          </td>
	           
	        </tr>
	        <tr>
	          <td id=res3 colspan="2" class="text-right"></td>
	        </tr>
	        <tr>
	           <th class="text-left" width=30%>주소</th>
	           <td width=70%>
				    <input type="text" name=post id="post" placeholder="우편번호">
					<input type="button" id="postBtn" onclick="sample3_execDaumPostcode()" value="우편번호 찾기"><br>
					<input type="text" name=addr1 id="addr1" placeholder="주소"><br>
					<input type="text" name=addr2 id="addr2" placeholder="상세주소">
			   </td>
	         </tr>
	         <tr>
	          <td colspan="2" class="text-center">
	            <input type=submit value="회원가입" class="btn btn-sm btn-info" onClick="return check()"><!-- id="joinBtn" -->
	            <input type=button value="취소" class="btn btn-sm btn-info"
	             onclick="javascript:history.back()">
	          </td>
	        </tr>
	      </table>
	      </form>
	      </div>
         <!-- end of inputs -->   

      
   <!-- Page Footer -->
    <footer class="page-footer">
        <div class="container">
            <p class="border-top mb-0 mt-4 pt-3 small">&copy; <script>document.write(new Date().getFullYear())</script>, JoeBlog Created By <a href="https://www.devcrud.com" class="text-muted font-weight-bold" target="_blank">DevCrud.</a>  All rights reserved </p> 
        </div>      
    </footer>
    <!-- End of Page Footer -->
   
   <!-- core  -->
   <script src="assets/vendors/jquery/jquery-3.4.1.js"></script>
   <script src="assets/vendors/bootstrap/bootstrap.bundle.js"></script>

</body>
</html>