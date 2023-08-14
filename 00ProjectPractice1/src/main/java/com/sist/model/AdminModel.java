package com.sist.model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.FreeBoardVO;
import com.sist.vo.MemberVO;
import com.sist.vo.NoticeVO;
import com.sist.vo.ProductVO;
import com.sist.vo.QnaVO;
import com.sist.vo.ReserveFoodVO;
import com.sist.vo.ReserveShareVO;

import java.util.*;
public class AdminModel {
	@RequestMapping("admin/adminpage.do")
	public String admin_page(HttpServletRequest request, HttpServletResponse response) {
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		AdminDAO dao=AdminDAO.newInstance();
		String page=request.getParameter("page");
		if(page==null)
			page="1";
	 	final int BLOCK=5;
		String fd=request.getParameter("fd");
		if(fd==null)
			fd="";
		List<MemberVO> ulist=dao.userInfo(Integer.parseInt(page),fd);
		
		int ucurpage=Integer.parseInt(page);
		int ustartpage=((ucurpage-1)/BLOCK*BLOCK)+1;
		int uendpage=((ucurpage-1)/BLOCK*BLOCK)+BLOCK;
		int utotalpage=dao.userinfoTotalPage(fd);
		if(uendpage>utotalpage)
			uendpage=utotalpage;
		request.setAttribute("fd", fd);
		request.setAttribute("ulist", ulist);
		request.setAttribute("utotalpage", utotalpage);
		request.setAttribute("ucurpage", ucurpage);
		request.setAttribute("ustartpage", ustartpage);
		request.setAttribute("uendpage", uendpage);
		request.setAttribute("jspp", "../admin/userinfo.jsp");
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");
		 return "../jsp/main.jsp";
	}
	@RequestMapping("admin/userinfo.do")
	public String user_info(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		AdminDAO dao=AdminDAO.newInstance();
		String page=request.getParameter("page");
		if(page==null)
			page="1";
	 	final int BLOCK=5;
		String fd=request.getParameter("fd");
		if(fd==null)
			fd="";
		List<MemberVO> ulist=dao.userInfo(Integer.parseInt(page),fd);
		
		int ucurpage=Integer.parseInt(page);
		int ustartpage=((ucurpage-1)/BLOCK*BLOCK)+1;
		int uendpage=((ucurpage-1)/BLOCK*BLOCK)+BLOCK;
		int utotalpage=dao.userinfoTotalPage(fd);
		if(uendpage>utotalpage)
			uendpage=utotalpage;
		request.setAttribute("fd", fd);
		request.setAttribute("ulist", ulist);
		request.setAttribute("utotalpage", utotalpage);
		request.setAttribute("ucurpage", ucurpage);
		request.setAttribute("ustartpage", ustartpage);
		request.setAttribute("uendpage", uendpage);
		request.setAttribute("jspp", "../admin/userinfo.jsp");
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");
		 return "../jsp/main.jsp";
	}
	@RequestMapping("admin/userupdate_ok.do")
	public String user_update_ok(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String nickname=request.getParameter("nickname");
		String birthday=request.getParameter("birthday");
		String email=request.getParameter("email");
		String sex=request.getParameter("sex");
		String post=request.getParameter("post");
		String addr1=request.getParameter("addr1");
		String addr2=request.getParameter("addr2");
		String phone=request.getParameter("phone");
		MemberVO vo=new MemberVO();
		vo.setId(id);
		vo.setName(name);
		vo.setNickname(nickname);
		vo.setBirthday(birthday);
		vo.setEmail(email);
		vo.setSex(sex);
		vo.setPost(post);
		vo.setAddr1(addr1);
		vo.setAddr2(addr2);
		vo.setPhone(phone);
		
		AdminDAO dao=AdminDAO.newInstance();
		dao.userinfoUpdate(vo);

		return "redirect: ../admin/userinfo.do";
	}
	@RequestMapping("admin/user_delete_ok.do")
	public String user_delete(HttpServletRequest request,HttpServletResponse response)
	{
		String id=request.getParameter("id");
		AdminDAO dao=AdminDAO.newInstance();
		dao.userinfoDelete(id);
		System.out.println(id);
		return "redirect: ../admin/userinfo.do";
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@RequestMapping("admin/product_manager.do")
	public String product_manager(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		String page=request.getParameter("page");
		if(page==null)
			page="1";
	 	final int BLOCK=5;
	 	AdminDAO dao=AdminDAO.newInstance();
	 	String fd=request.getParameter("fd");
	 	if(fd==null)
	 		fd="";
	 	List<ProductVO> plist=dao.ProductListManager(Integer.parseInt(page),fd);
		int pcurpage=Integer.parseInt(page);
		int pstartpage=((pcurpage-1)/BLOCK*BLOCK)+1;
		int pendpage=((pcurpage-1)/BLOCK*BLOCK)+BLOCK;
		int ptotalpage=dao.adminProductTotalPage(fd);
		if(pendpage>ptotalpage)
			pendpage=ptotalpage;
		request.setAttribute("fd", fd);
		request.setAttribute("plist", plist);
		request.setAttribute("ptotalpage", ptotalpage);
		request.setAttribute("pcurpage", pcurpage);
		request.setAttribute("pstartpage", pstartpage);
		request.setAttribute("pendpage", pendpage);
		
		request.setAttribute("jspp", "../admin/product_manager.jsp");
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");
		return "../jsp/main.jsp";
	}
	@RequestMapping("admin/product_update_ok.do")
	public String product_update(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		//title=?,subject=?,sale=?,priced_sale=?,original_pri=?,first_pri=?,delivery_pri=?,goods_count=?
		String pdno=request.getParameter("pdno");
		String title=request.getParameter("title");
		String subject=request.getParameter("subject");
		String sale=request.getParameter("sale");
		String price_sale=request.getParameter("price_sale");
		String original_pri=request.getParameter("original_pri");
		String first_pri=request.getParameter("first_pri");
		String delivery_pri=request.getParameter("delivery_pri");
		String goods_count=request.getParameter("goods_count");
		ProductVO vo=new ProductVO();
		vo.setPdno(Integer.parseInt(pdno));
		vo.setTitle(title);
		vo.setSubject(subject);
		vo.setSale(sale);
		vo.setPriced_sale(price_sale);
		vo.setOriginal_pri(original_pri);
		vo.setFirst_pri(first_pri);
		vo.setDelivery_pri(delivery_pri);
		vo.setGoods_count(Integer.parseInt(goods_count));
		
		AdminDAO dao=AdminDAO.newInstance();
		dao.product_update(vo);
		
		return "redirect: ../admin/product_manager.do";
	}
	
	@RequestMapping("admin/product_delete_ok.do")
	public String product_delete(HttpServletRequest request,HttpServletResponse response)
	{
		String pdno=request.getParameter("pdno");
		AdminDAO dao=AdminDAO.newInstance();
		dao.productDelete(Integer.parseInt(pdno));
		
		return "redirect: ../admin/product_manager.do";
	}
	
	@RequestMapping("admin/product_insert.do")
	public String product_insert(HttpServletRequest request,HttpServletResponse response)
	{
		
		request.setAttribute("jspp", "../admin/product_insert.jsp");
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");
		return "../jsp/main.jsp";
	}
	@RequestMapping("admin/product_insert_ok.do")
	public String product_insert_ok(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		String title=request.getParameter("title");
		String poster=request.getParameter("poster");
		String subject=request.getParameter("subject");
		String sale=request.getParameter("sale");
		String price_sale=request.getParameter("price_sale");
		String original_pri=request.getParameter("original_pri");
		String first_pri=request.getParameter("first_pri");
		String score=request.getParameter("score");
		String delivery_pri=request.getParameter("delivery_pri");
		String goods_count=request.getParameter("goods_count");
		ProductVO vo=new ProductVO();
		vo.setTitle(title);
		vo.setPoster(poster);
		vo.setSubject(subject);
		vo.setSale(sale);
		vo.setPriced_sale(price_sale);
		vo.setOriginal_pri(original_pri);
		vo.setFirst_pri(first_pri);
		vo.setScore(Double.parseDouble(score));
		vo.setDelivery_pri(delivery_pri);
		vo.setGoods_count(Integer.parseInt(goods_count));
		
		AdminDAO dao=AdminDAO.newInstance();
		dao.product_insert(vo);
		return "redirect: ../admin/product_manager.do";
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping("admin/board_manager.do")
	public String board_list(HttpServletRequest request, HttpServletResponse response) {
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}
		AdminDAO dao = AdminDAO.newInstance();
		String page=request.getParameter("page");
		final int bBLOCK=8;
		if(page==null)
			page="1";
		String fd=request.getParameter("fd");
		if(fd==null)
			fd="";	
		int bcurpage = Integer.parseInt(page);
		int bstartpage = ((bcurpage-1)/bBLOCK*bBLOCK)+1;
		int bendpage=((bcurpage-1)/bBLOCK*bBLOCK)+bBLOCK;
		int btotalpage = dao.boardManagerTotalPage(fd);
		if(bendpage>btotalpage)
			bendpage=btotalpage;
		List<FreeBoardVO> blist = dao.boardManagerListData(bcurpage,fd);
		request.setAttribute("blist", blist);
		request.setAttribute("btotalpage", btotalpage);
		request.setAttribute("bcurpage", bcurpage);
		request.setAttribute("bstartpage", bstartpage);
		request.setAttribute("bendpage", bendpage);
		
		request.setAttribute("fd", fd);
		request.setAttribute("jspp", "../admin/board_manager.jsp");
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("admin/board_update_ok.do")
	public String admin_board_update(HttpServletRequest request,HttpServletResponse response)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		}
		catch(Exception ex) {}

		String bno=request.getParameter("bno");
		String subject=request.getParameter("subject");
		String content=request.getParameter("content");
		FreeBoardVO vo=new FreeBoardVO();
		vo.setBno(Integer.parseInt(bno));
		vo.setSubject(subject);
		vo.setContent(content);
		AdminDAO dao=AdminDAO.newInstance();
		dao.adminBoardUpdate(vo);
	
		return "redirect: ../admin/board_manager.do";
	}
	@RequestMapping("admin/admin_board_delete_ok.do")
	public String admin_board_delete(HttpServletRequest request,HttpServletResponse response)
	{
		String bno=request.getParameter("bno");
		AdminDAO dao=AdminDAO.newInstance();
		dao.adminBoardDelete(Integer.parseInt(bno));
		
		return "redirect: ../admin/board_manager.do";
	}
	//예약
		@RequestMapping("admin/adminpage_reserve.do")
		public String admin_food_reserve(HttpServletRequest request, HttpServletResponse response) {
			String page=request.getParameter("page");
			if(page==null)
				page="1";
			final int BLOCK=5;
			ReserveDAO dao=ReserveDAO.newInstance();
			AdminDAO adao=AdminDAO.newInstance();
			List<ReserveFoodVO> list=dao.reserveFoodAdminData();
			List<ReserveFoodVO> aflist=adao.reserveFoodAdminListData(Integer.parseInt(page));
			int afcurpage=Integer.parseInt(page);
			int afstartpage=((afcurpage-1)/BLOCK*BLOCK)+1;
			int afendpage=((afcurpage-1)/BLOCK*BLOCK)+BLOCK;
			int aftotalpage=adao.reserveFoodAdmintotalpage();
			if(afendpage>aftotalpage)
				afendpage=aftotalpage;
			
			//페이지
			request.setAttribute("aflist", aflist);
			request.setAttribute("aftotalpage", aftotalpage);
			request.setAttribute("afcurpage", afcurpage);
			request.setAttribute("afstartpage", afstartpage);
			request.setAttribute("afendpage", afendpage);
			//예약
			request.setAttribute("list", list);
			request.setAttribute("jspp", "../admin/adminpage_reserve.jsp");
			request.setAttribute("main_jsp", "../admin/adminpage.jsp");
			return "../jsp/main.jsp";
		}
		@RequestMapping("admin/admin_reserve_ok.do")
		public String admin_food_ok(HttpServletRequest request, HttpServletResponse response) {
			String fno=request.getParameter("fno");
			ReserveDAO dao=ReserveDAO.newInstance();
			dao.reserveOk(Integer.parseInt(fno));
			return "redirect:../admin/adminpage_reserve.do";
		}
		
		@RequestMapping("admin/adminpage_reserve1.do")
		public String admin_share_reserve(HttpServletRequest request, HttpServletResponse response) {
			String page=request.getParameter("page");
			if(page==null)
				page="1";
			final int BLOCK=5;
			ReserveDAO dao=ReserveDAO.newInstance();
			AdminDAO adao=AdminDAO.newInstance();
			List<ReserveShareVO> list=dao.reserveShareAdminData();
			List<ReserveShareVO> aslist=adao.reserveShareAdminListData(Integer.parseInt(page));
			
			int ascurpage=Integer.parseInt(page);
			int asstartpage=((ascurpage-1)/BLOCK*BLOCK)+1;
			int asendpage=((ascurpage-1)/BLOCK*BLOCK)+BLOCK;
			int astotalpage=adao.reserveShareAdmintotalpage();
			if(asendpage>astotalpage)
				asendpage=astotalpage;
			
			//페이지
			request.setAttribute("aslist", aslist);
			request.setAttribute("astotalpage", astotalpage);
			request.setAttribute("ascurpage", ascurpage);
			request.setAttribute("asstartpage", asstartpage);
			request.setAttribute("asendpage", asendpage);
			
			request.setAttribute("list", list);
			request.setAttribute("jspp", "../admin/adminpage_reserve1.jsp");
			request.setAttribute("main_jsp", "../admin/adminpage.jsp");
			return "../jsp/main.jsp";
		}
		@RequestMapping("admin/admin_reserve_ok1.do")
		public String admin_share_ok(HttpServletRequest request, HttpServletResponse response) {
			String sno=request.getParameter("sno");
			ReserveDAO dao=ReserveDAO.newInstance();
			dao.sreserveOk(Integer.parseInt(sno));
			return "redirect:../admin/adminpage_reserve1.do";
		}
	
	@RequestMapping("admin/admin_find.do")
	public String admin_find(HttpServletRequest request,HttpServletResponse response)
	{
		List<?> list;
		int BLOCK=10;
		int curpage,startpage,endpage,totalpage=0;
		try
		{
			request.setCharacterEncoding("UTF-8");
		}catch(Exception ex) {}
		String cate = request.getParameter("cate");
		String fd= request.getParameter("fd");
		String page= request.getParameter("page");
		if(page==null) page="1";
		request.setAttribute("cate", cate);
		request.setAttribute("fd", fd);
		// 레시피 맛집 스토어 공유주방
		if(cate.equals("레시피")) {
			RecipeDAO dao=RecipeDAO.newInstance();
			  curpage=Integer.parseInt(page);
			  startpage=((curpage-1)/BLOCK*BLOCK)+1;
			  endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
			  totalpage=dao.recipeFindTotalPage(fd);
			 if(endpage>totalpage)
					endpage=totalpage;
			list = dao.recipeFind(curpage, fd);
			request.setAttribute("list", list);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("curpage", curpage);
			request.setAttribute("totalpage", totalpage);
			request.setAttribute("main_jsp", "../list/recipeSearch.jsp");
			return "../jsp/main.jsp";
		}else if(cate.equals("맛집")) {
			FoodDAO dao=FoodDAO.newInstance();
			
			  curpage=Integer.parseInt(page);
			  startpage=((curpage-1)/BLOCK*BLOCK)+1;
			  endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
			  totalpage=dao.foodSearchTotalPage(fd);
			 if(endpage>totalpage)
					endpage=totalpage;
			list = dao.findFood(curpage, fd);
			request.setAttribute("list", list);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("curpage", curpage);
			request.setAttribute("totalpage", totalpage);
			request.setAttribute("main_jsp", "../food/foodSearch.jsp");
			return "../jsp/main.jsp";
			//start랑 total을 받아야함 
		}else if(cate.equals("스토어")) {
			ProductDAO dao=ProductDAO.newInstance();
			curpage=Integer.parseInt(page);
			 startpage=((curpage-1)/BLOCK*BLOCK)+1;
			  endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
			  totalpage=dao.productFindTotalPage(fd);
			 if(endpage>totalpage)
					endpage=totalpage;
			 list = dao.productFindData(curpage, fd);
			request.setAttribute("list", list);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("curpage", curpage);
			request.setAttribute("totalpage", totalpage);
			request.setAttribute("main_jsp", "../product/productSearch.jsp");
			return "../jsp/main.jsp";
			
		}else if(cate.equals("공유주방")) {
			ShareDAO dao=ShareDAO.newInstance();
			 curpage=Integer.parseInt(page);
			  startpage=((curpage-1)/BLOCK*BLOCK)+1;
			  endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
			  totalpage=dao.shareFindTotalPage(fd);
			 if(endpage>totalpage)
					endpage=totalpage;
			list = dao.shareFindData(curpage, fd);
			request.setAttribute("list", list);
			request.setAttribute("startpage", startpage);
			request.setAttribute("endpage", endpage);
			request.setAttribute("curpage", curpage);
			request.setAttribute("totalpage", totalpage);
			request.setAttribute("main_jsp", "../share/shareSearch.jsp");
			return "../jsp/main.jsp";
		}
		return "redirect: ../jsp/main.do";
	}
////공지사항///////////////////////////////////////////////////////////////////////////////////////////
	@RequestMapping("admin/admnotice_list.do")
    public String admin_notice(HttpServletRequest request, HttpServletResponse response) {
		
		
		String page = request.getParameter("page");
		if(page==null) {
			page="1";
		}
		int curpage = Integer.parseInt(page);
		
		final int BLOCK=5;
		int startpage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		
		NoticeDAO dao = NoticeDAO.newInstance();			
		int totalpage = dao.noticeTotalPage();	
		if(endpage>totalpage)
			endpage=totalpage;	
		
		List<NoticeVO> list = dao.noticeListData(curpage);
		
		String[] msg= {"","일반 공지","레시피 공지","맛집 공지","스토어 공지","공유주방 공지"};		
		for(NoticeVO vo:list) {
			vo.setNotice_type(msg[vo.getType()]);		
		}
		request.setAttribute("list", list);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("jspp", "../admin/admnotice_list.jsp"); //main위에 admin올리고 그 위에 notice_list올려준
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");	//전체 기반은 main
		
		return "../jsp/main.jsp";
	}
	
	//공지작성
	@RequestMapping("admin/admnotice_insert.do") 
	public String notice_insert(HttpServletRequest request, HttpServletResponse response) {
	  
		request.setAttribute("jspp", "../admin/admnotice_insert.jsp"); //main위에 admin올리고 그 위에 notice_list올려준
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");	//전체 기반은 main
		
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("admin/admnotice_insert_ok.do")
	 public String notice_insert_ok(HttpServletRequest request, HttpServletResponse response) {
	    
		  try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		  
		  String type = request.getParameter("type");
		  String subject = request.getParameter("subject");
		  String content = request.getParameter("content");
		  
		  HttpSession session = request.getSession();
		  String id = (String)session.getAttribute("id");
		  String name = (String)session.getAttribute("name");
		  
		  //vo로 묶어줌
		  NoticeVO vo = new NoticeVO();
		  vo.setType(Integer.parseInt(type));
		  vo.setSubject(subject);
		  vo.setContent(content);
		  vo.setId(id);
		  vo.setName(name);
		  
		  //DAO
		  NoticeDAO dao = NoticeDAO.newInstance();
		  dao.noticeInsert(vo);
		  
		 return "redirect:../admin/admnotice_list.do";
		 
		 
	  }
	
	@RequestMapping("admin/notice_update.do")
	 public String notice_update(HttpServletRequest request, HttpServletResponse response) {
		  String no = request.getParameter("no");
		  NoticeDAO dao = NoticeDAO.newInstance();
		  NoticeVO vo = dao.noticeUpdateData(Integer.parseInt(no));
		  
		  request.setAttribute("vo", vo);
		  request.setAttribute("jspp", "../admin/admnotice_update.jsp");
		  request.setAttribute("main_jsp", "../admin/adminpage.jsp");

		  return "../jsp/main.jsp";
	}
	
	@RequestMapping("admin/notice_update_ok.do")
	 public String notice_update_ok(HttpServletRequest request, HttpServletResponse response) {
		  
		  try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		  //insert부분
		  String type = request.getParameter("type");
		  String subject = request.getParameter("subject");
		  String content = request.getParameter("content");
		  String no = request.getParameter("no");
		  
		  //vo로 묶어줌
		  NoticeVO vo = new NoticeVO();
		  vo.setType(Integer.parseInt(type));
		  vo.setSubject(subject);
		  vo.setContent(content);
		  vo.setNo(Integer.parseInt(no));
		  
		  NoticeDAO dao = NoticeDAO.newInstance();
		  dao.noticeUpdate(vo);
		  return "redirect:../admin/admnotice_list.do";
	  }
	@RequestMapping("admin/notice_delete.do")
	public String notice_delete(HttpServletRequest request, HttpServletResponse response) {
		  
		  String no = request.getParameter("no");
		  
		  //DAO연동
		  NoticeDAO dao = NoticeDAO.newInstance();
		  dao.noticeDelete(Integer.parseInt(no));
		  return "redirect:../admin/admnotice_list.do";
	  }
	
///// 묻고 답하기///////////////////////////////////////
	@RequestMapping("admin/admqna_list.do")
	public String adminpage_qna_list(HttpServletRequest request, HttpServletResponse response) {
		
		 String page = request.getParameter("page");
		 if(page==null)
			 page="1";
		 int curpage = Integer.parseInt(page);
		 QnaBoardDAO dao= QnaBoardDAO.newInstance();
		 List<QnaVO> list = dao.replyBoardAdminListData(curpage);
				 
		 int totalpage = dao.replyBoardAdminTotalPage();
		 
		 request.setAttribute("list", list);
		 request.setAttribute("curpage", curpage);
		 request.setAttribute("totalpage", totalpage);
		 
		request.setAttribute("jspp", "../admin/admqna_list.jsp");
		request.setAttribute("main_jsp", "../admin/adminpage.jsp");
			
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("admin/QnA_insert.do")
	public String adminpage_replyboard_insert(HttpServletRequest request, HttpServletResponse response) {
		 String no = request.getParameter("no");
		 
		 request.setAttribute("no", no); 
		 request.setAttribute("jspp", "../admin/admqna_insert.jsp");
		 request.setAttribute("main_jsp", "../admin/adminpage.jsp");
		 return "../jsp/main.jsp";
	 }
	
	 @RequestMapping("admin/QnA_insert_ok.do")
	 public String adminpage_replyboard_insert_OK(HttpServletRequest request, HttpServletResponse response) {
		 
		 try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		 String subject = request.getParameter("subject");
		 String content = request.getParameter("content");
		 String pno = request.getParameter("pno");	//pno: 질문한 번호
		 
		 HttpSession session = request.getSession();
		 String id = (String)session.getAttribute("id");
		 String name = (String)session.getAttribute("name");
		 
		 QnaVO vo = new QnaVO();
		 vo.setSubject(subject);
		 vo.setContent(content);
		 vo.setId(id);
		 vo.setName(name);
			
		 QnaBoardDAO dao = QnaBoardDAO.newInstance();
		 dao.replyBoardAdminInsert(Integer.parseInt(pno), vo);
		 
		 return "redirect:../admin/admqna_list.do";
	 }
	 @RequestMapping("admin/admin_reserveFind.do")
		public String admin_reserveFind(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (Exception e) {}
			String fd=request.getParameter("fd");
			String page=request.getParameter("page");
//			System.out.println(fd);
			final int BLOCK=5;
			if(page==null)
				page="1";
			AdminDAO dao=AdminDAO.newInstance();
			int afcurpage=Integer.parseInt(page);
			int afstartpage=((afcurpage-1)/BLOCK*BLOCK)+1;
			int afendpage=((afcurpage-1)/BLOCK*BLOCK)+BLOCK;
			int aftotalpage=dao.adminreserveFindTotalpage(fd);
			if(afendpage>aftotalpage)
				afendpage=aftotalpage;
			List<ReserveFoodVO> aflist=dao.adminreserveFind(Integer.parseInt(page), fd);
			//페이지
			request.setAttribute("aflist", aflist);
			request.setAttribute("aftotalpage", aftotalpage);
			request.setAttribute("afcurpage", afcurpage);
			request.setAttribute("afstartpage", afstartpage);
			request.setAttribute("afendpage", afendpage);
			
			request.setAttribute("fd", fd);
			request.setAttribute("jspp", "../admin/adminpage_reserve_find.jsp");
			request.setAttribute("main_jsp", "../admin/adminpage.jsp");
			return "../jsp/main.jsp";
		}
		
		@RequestMapping("admin/admin_reserveFind1.do")
		public String admin_reserveFindshare(HttpServletRequest request, HttpServletResponse response) {
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (Exception e) {}
			String fd=request.getParameter("fd");
			String page=request.getParameter("page");
//			System.out.println(fd);
			final int BLOCK=5;
			if(page==null)
				page="1";
			AdminDAO dao=AdminDAO.newInstance();
			int ascurpage=Integer.parseInt(page);
			int asstartpage=((ascurpage-1)/BLOCK*BLOCK)+1;
			int asendpage=((ascurpage-1)/BLOCK*BLOCK)+BLOCK;
			int astotalpage=dao.adminreserveShareTotalpage(fd);
			if(asendpage>astotalpage)
				asendpage=astotalpage;
			List<ReserveShareVO> aslist=dao.adminreserveshareFind(Integer.parseInt(page), fd);
			//페이지
			request.setAttribute("aslist", aslist);
			request.setAttribute("astotalpage", astotalpage);
			request.setAttribute("ascurpage", ascurpage);
			request.setAttribute("asstartpage", asstartpage);
			request.setAttribute("asendpage", asendpage);
			
			request.setAttribute("fd", fd);
			request.setAttribute("jspp", "../admin/adminpage_reserve_find1.jsp");
			request.setAttribute("main_jsp", "../admin/adminpage.jsp");
			return "../jsp/main.jsp";
		}
}