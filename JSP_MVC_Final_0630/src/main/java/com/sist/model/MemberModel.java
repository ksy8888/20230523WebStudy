package com.sist.model;

import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.dao.MemberDAO;
import com.sist.vo.MemberVO;
import com.sist.vo.ZipcodeVO;

public class MemberModel {

	@RequestMapping("member/join.do") // <li><a href="../member/join.do">회원 가입</a></li> ../member/join.do 일치해야함 >> header.jsp파일
	public String memberJoin(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("main_jsp", "../member/join.jsp");	//화면이 바뀌는 부분
		return "../main/main.jsp";	//실행
	}
	
	@RequestMapping("member/idcheck.do")
	public String memberIdCheck(HttpServletRequest request, HttpServletResponse response) {
		return "../member/idcheck.jsp";
	}
	
	@RequestMapping("member/idcheck_ok.do")
	public void memberIdCheckOk(HttpServletRequest request, HttpServletResponse response) {
	 
		//ajax는 값만 전송 >>  void    //ajax가 아니고 화면만 전송할땐 return "~.jsp";
		String id = request.getParameter("id");
		MemberDAO dao = MemberDAO.newInstance();
		int count = dao.memberIdCheck(id);
		
		//데이터를 Ajax로 전송 ==> success:function(result) > result에 값이 담김
		try {
			PrintWriter out =response.getWriter();
			out.println(count); //function(result)의 result에 담기는 값
		} catch (Exception e) {
			// TODO: handle exception
		} 
		
	}
	
	@RequestMapping("member/postfind.do")	//사용자에게서 member/postfind.do 요청이 들어오면
	
	 public String memberPostFind(HttpServletRequest request, HttpServletResponse response) { 
		return "../member/postfind.jsp"; //postfind.jsp를 보여달라 
	}
	 
	
	@RequestMapping("member/postfind_result.do")
	public String memberPostFindResult(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		String dong = request.getParameter("dong");
		MemberDAO dao = MemberDAO.newInstance();
		int count = dao.postFindCount(dong);
		List<ZipcodeVO> list = dao.postFindData(dong);
		
		//어떤 값을 보낼 것 인가
		request.setAttribute("count", count);
		request.setAttribute("list", list);  //여기 받은 결과값을 
		return "../member/postfind_result.jsp";	 //여기에 출력
	}
	
	@RequestMapping("member/join_ok.do")
	public String memberJoinOk(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String post = request.getParameter("post");
		String addr1 = request.getParameter("addr1");
		String addr2 = request.getParameter("addr2");
		String phone1 = request.getParameter("phone1");
		String phone = request.getParameter("phone");
		String content = request.getParameter("content");
		
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPwd(pwd);
		vo.setName(name);
		vo.setSex(sex);
		vo.setBirthday(birthday);
		vo.setEmail(email);
		vo.setPost(post);
		vo.setAddr1(addr1);
		vo.setAddr2(addr2);
		vo.setPhone(phone1+"-"+phone);
		vo.setContent(content);
		
		MemberDAO dao = MemberDAO.newInstance();
		dao.memberInsert(vo);
		
		//이동
		return "redirect:../main/main.do";
	}
	
	@RequestMapping("member/login.do")
	public void memberLogin(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		MemberDAO dao = MemberDAO.newInstance();
		MemberVO vo = dao.memberLogin(id, pwd);
		
		//로그인 => 사용자의 일부 정보를 저장
		//로그인 완료시 세션에 저장해둘 값 설정
		HttpSession session = request.getSession();
		if(vo.getMsg().equals("OK")) {
			session.setAttribute("id", vo.getId());
			session.setAttribute("name", vo.getName());
			session.setAttribute("sex", vo.getSex());
			session.setAttribute("admin", vo.getAdmin());
			//전역변수 => 모든 JSP에서 사용 가능하다
		}
		// -> 결과값을 전송 => Ajax
		try {
			PrintWriter out = response.getWriter();
			//사용자 브라우저에 읽어 가능 메모리 공간
			out.println(vo.getMsg()); //NOID, NOPWD, OK
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/*
	 	<%@ page ~~ %>
	 	<%
	 		~~~
	 	%>
	 */
	@RequestMapping("member/logout.do") 
		public String memberLogout(HttpServletRequest request, HttpServletResponse response) {
		
			HttpSession session = request.getSession();
			session.invalidate();
			
			return "redirect:../main/main.do"; //16번째줄의 main.do를 실행
	}
	
	//id찾기
	@RequestMapping("member/idfind.do")
	public String memberIdFind(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../member/idfind.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	//ajax
	@RequestMapping("member/idfind_ok.do")
	public void memberIdFindOk(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		MemberDAO dao = MemberDAO.newInstance();
		String res = dao.memberId_EmailFind(email);	//res >> 없으면 NO
		//DAO
		try {
			//AJax에 값을 전송 => No, s*** 중 어떤 값을 보낼 것인지 >> DAO에서 처리
			PrintWriter out = response.getWriter(); //사용자 브라우저
			out.println(res); //ajax의 result에서 값을 받음
		} catch (Exception e) {
			
		}
		
	}
	
	//비번찾기
	@RequestMapping("member/passwordfind.do")
	public String memberPwdFind(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../member/passwordfind.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("member/passwordfindOk.do")
	public void memberPasswordFindOk(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		// {"name":'',"email":sss{
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		MemberDAO dao = MemberDAO.newInstance();
		//결과값 받기
		String res = dao.memberPasswordFind(name, email);
		
		//ajax로 전송
		try {
			//스프링 => @RestController
			PrintWriter out = response.getWriter();
			out.println(res);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@RequestMapping("member/member_update.do")
	public String memberUpdate(HttpServletRequest request, HttpServletResponse response) {
		// 이전의 개인 정보를 보낸다 => id
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		//DAO연동
		 MemberDAO dao = MemberDAO.newInstance();
		 MemberVO vo = dao.memberUpdateData(id);
		 request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../member/join_update.jsp");	
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("member/join_update_ok.do")
	public String memberUpdateOk(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String post = request.getParameter("post");
		String addr1 = request.getParameter("addr1");
		String addr2 = request.getParameter("addr2");
		String phone1 = request.getParameter("phone1");
		String phone = request.getParameter("phone");
		String content = request.getParameter("content");
		
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPwd(pwd);
		vo.setName(name);
		vo.setSex(sex);
		vo.setBirthday(birthday);
		vo.setEmail(email);
		vo.setPost(post);
		vo.setAddr1(addr1);
		vo.setAddr2(addr2);
		vo.setPhone(phone1+"-"+phone);
		vo.setContent(content);
		
		//DAO 연결
		MemberDAO dao = MemberDAO.newInstance();
		MemberVO mvo = dao.memberUpdate(vo);
		if(mvo.getMsg().equals("yes")) {
			HttpSession session = request.getSession();
			session.setAttribute("name", mvo.getName());
			
		}
		
		request.setAttribute("mvo", mvo);
		
		return "../member/join_update_ok.jsp";
	}
	
	@RequestMapping("member/member_delete.do")
	public String memberDelete(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../member/join_delete.jsp");
		CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("member/join_delete_ok.do")
	public void memberDeleteOk(HttpServletRequest request, HttpServletResponse response) {
	
		String pwd = request.getParameter("pwd");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		//DAO
		MemberDAO dao = MemberDAO.newInstance();
		String result = dao.memberDeleteOk(id, pwd);
		if(result.equals("yes")) {
			session.invalidate();
		}
		
		try {
			PrintWriter out = response.getWriter();
			out.println(result); // ajax의 yes,no
		} catch (Exception e) {}
	}
}

