package com.sist.model;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.CartVO;
import com.sist.vo.MemberVO;
import com.sist.vo.ReserveFoodVO;
import com.sist.vo.ReserveShareVO;
public class MyPageModel {
	@RequestMapping("my/mypage.do")
	public String my_page(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("main_jsp", "../my/pwdcheck.jsp");
		
		 return "../jsp/main.jsp";
	}
	@RequestMapping("my/myinfo.do")
	public String my_page_info(HttpServletRequest request, HttpServletResponse response) {
		try
		{
			request.setCharacterEncoding("UTF-8");
		}catch(Exception ex) {}
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		MyDAO dao=MyDAO.newInstance();
		MemberVO vo=dao.myInfo(id);
		request.setAttribute("vo", vo);
		request.setAttribute("jsp", "../my/myinfo.jsp");
		request.setAttribute("main_jsp", "../my/mypage.jsp");
		 return "../jsp/main.jsp";
	}
	@RequestMapping("my/my_update.do")
	public String my_page_update(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		MyDAO dao=MyDAO.newInstance();
		MemberVO vo=dao.myInfo(id);
		request.setAttribute("vo", vo);
		request.setAttribute("jsp", "../my/my_update.jsp");
		request.setAttribute("main_jsp", "../my/mypage.jsp");
		
		return "../jsp/main.jsp";
	}
	@RequestMapping("my/my_update_ok.do")
	public String my_update_ok(HttpServletRequest request, HttpServletResponse response)
	{
//		name=?,nickname=?,birthday=?,email=?,sex=?,post=?,addr1=?,addr2=?
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		String id=request.getParameter("id");
		String nickname=request.getParameter("nickname");
		String email=request.getParameter("email");
		String post=request.getParameter("post");
		String addr1=request.getParameter("addr1");
		String addr2=request.getParameter("addr2");
		String phone=request.getParameter("phone");
		MemberVO vo=new MemberVO();
		vo.setId(id);
		vo.setNickname(nickname);
		vo.setEmail(email);
		vo.setPost(post);
		vo.setAddr1(addr1);
		vo.setAddr2(addr2);
		vo.setPhone(phone);
		
		MyDAO dao=MyDAO.newInstance();
		dao.myinfoUpdate(vo);
		
		return "redirect: ../my/myinfo.do";
	}
	
	@RequestMapping("my/my_delete_ok.do")
	public String user_delete(HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		MyDAO dao=MyDAO.newInstance();
		dao.myinfoDelete(id);
		System.out.println(id);
		session.invalidate();
		return "redirect:../jsp/main.do";
	} // 자동으로 로그아웃까지 돼야하는데 안댐
	//////////////////////////////////////////////////////////////////////////////////////////////
	//예약
		@RequestMapping("my/mypage_reserve.do")
		public String mypage_reserve(HttpServletRequest request, HttpServletResponse response) {
			HttpSession session=request.getSession();
			String fid=(String)session.getAttribute("id");
			ReserveDAO dao=ReserveDAO.newInstance();
			List<ReserveFoodVO> list=dao.reserveFoodInfoData(fid);
			
			request.setAttribute("list", list);
			request.setAttribute("jsp", "../my/mypage_reserve.jsp");
			request.setAttribute("main_jsp", "../my/mypage.jsp");
			return "../jsp/main.jsp";
		}
		@RequestMapping("my/mypage_reserve1.do")
		public String mypage_sreserve(HttpServletRequest request, HttpServletResponse response) {
			HttpSession session=request.getSession();
			String sid=(String)session.getAttribute("id");
			ReserveDAO dao=ReserveDAO.newInstance();
			List<ReserveShareVO> list=dao.reserveShareInfoData(sid);
			
			request.setAttribute("list", list);
			request.setAttribute("jsp", "../my/mypage_reserve1.jsp");
			request.setAttribute("main_jsp", "../my/mypage.jsp");
			return "../jsp/main.jsp";
		}
		@RequestMapping("my/mypage_cart.do")
		public String mypage_cart(HttpServletRequest request, HttpServletResponse response) {
			HttpSession session=request.getSession();
			String id=(String)session.getAttribute("id");
			CartDAO dao=CartDAO.newInstance();
			List<CartVO> list=dao.mypageCartListData(id);
			request.setAttribute("list", list);
			request.setAttribute("jsp", "../my/mypage_cart.jsp");
			request.setAttribute("main_jsp", "../my/mypage.jsp");
			return "../jsp/main.jsp";
		}
		
		@RequestMapping("my/mypage_buy.do")
		public String mypage_buy(HttpServletRequest request, HttpServletResponse response) {
			HttpSession session=request.getSession();
			String id=(String)session.getAttribute("id");
			CartDAO dao=CartDAO.newInstance();
			List<CartVO> list=dao.mypageCartBuyListData(id);
			request.setAttribute("list", list);
			request.setAttribute("jsp", "../my/mypage_buy.jsp");
			request.setAttribute("main_jsp", "../my/mypage.jsp");
			return "../jsp/main.jsp";
		}
		
		@RequestMapping("my/cart_cancel.do")
		public String mypage_cart_cancel(HttpServletRequest request, HttpServletResponse response) {
			String pdno=request.getParameter("pdno");
			CartDAO dao=CartDAO.newInstance();
			dao.cartCancel(Integer.parseInt(pdno));
			return "redirect:../my/mypage_cart.do";
		}
		
		@RequestMapping("cart/cart_insert.do")
		public String cart_insert(HttpServletRequest request, HttpServletResponse response) {
			String pdsno=request.getParameter("pdsno");
			String price=request.getParameter("price");
			String amount=request.getParameter("amount");
			
			CartVO vo=new CartVO();
			
			System.out.println(pdsno);
			System.out.println(price);
			System.out.println(amount);
			
			vo.setPdsno(Integer.parseInt(pdsno));
			vo.setPrice(Integer.parseInt(price));
			vo.setAmount(Integer.parseInt(amount));
			
			
			
			HttpSession session=request.getSession();
			String id=(String)session.getAttribute("id");
			vo.setId(id);
			
			CartDAO dao=CartDAO.newInstance();
			dao.cartInsert(vo);
			return "redirect:../product/productDetail.do?pdno="+pdsno;
		}
		@RequestMapping("cart/cart_buy.do")
		public void cart_buy(HttpServletRequest request, HttpServletResponse response) {
			String no=request.getParameter("cart_no");
			CartDAO dao=CartDAO.newInstance();
			dao.cartBuy(Integer.parseInt(no));
			
		}
		@RequestMapping("my/pwdcheck.do")
		public String pwd_check(HttpServletRequest request,HttpServletResponse response)
		{
			try
			{
				request.setCharacterEncoding("UTF-8");
			}
			catch(Exception ex) {}
			String pwd=request.getParameter("pwd");
			HttpSession session=request.getSession();
			String id=(String)session.getAttribute("id");
			MyDAO dao=MyDAO.newInstance();
			String dbpwd=dao.mypwd(id);
			if(pwd.equals(dbpwd))
			{
				try
				{
					request.setCharacterEncoding("UTF-8");
				}catch(Exception ex) {}
				session=request.getSession();
				
				dao=MyDAO.newInstance();
				MemberVO vo=dao.myInfo(id);
				request.setAttribute("vo", vo);
				request.setAttribute("jsp", "../my/myinfo.jsp");
				request.setAttribute("main_jsp", "../my/mypage.jsp");
				
				 return "../jsp/main.jsp";
			}
			else
			{
				request.setAttribute("dbpwd", dbpwd);
				request.setAttribute("pwd", pwd);
				request.setAttribute("main_jsp", "../my/pwdcheck.jsp");
				
				return "../jsp/main.jsp";
			}
		} 
}
