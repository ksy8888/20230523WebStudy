package com.sist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.FoodVO;
import com.sist.vo.ReplyVO;
import com.sist.vo.ShareVO;

public class FoodModel {
	@RequestMapping("food/foodList.do")
	public String food_list(HttpServletRequest request,HttpServletResponse response)
  {
		
		String page = request.getParameter("page");
		String type = request.getParameter("type");
		
		 final int BLOCK=10;
		 if(page==null)
			 page="1";
		 if(type==null)
			 type="0";
		
		 FoodDAO fdao=FoodDAO.newInstance();
		 int curpage=Integer.parseInt(page);
		 int startpage=((curpage-1)/BLOCK*BLOCK)+1;
		 int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		 int totalpage=fdao.foodTotalPage(Integer.parseInt(type));
		 if(endpage>totalpage)
				endpage=totalpage; 
		 List<FoodVO> flist=fdao.foodListData(curpage,Integer.parseInt(type));
		 request.setAttribute("curpage", curpage);
		 request.setAttribute("totalpage", totalpage);
		 request.setAttribute("startpage", startpage);
		 request.setAttribute("endpage", endpage);
		 request.setAttribute("type", type);
		 
		 request.setAttribute("flist", flist);
	    request.setAttribute("main_jsp", "../food/foodList.jsp");
	  return "../jsp/main.jsp";
  }
 @RequestMapping("food/foodDetail_before.do")
 public String food_detail_before(HttpServletRequest request,HttpServletResponse response)
 {
	 String fdno=request.getParameter("fdno");
	 Cookie cookie=new Cookie("food_"+fdno, fdno);
	 cookie.setPath("/");
	 cookie.setMaxAge(60*60*24);
	 response.addCookie(cookie);
	 
	 return "redirect:../food/foodDetail.do?fdno="+fdno;
	 
 }
 @RequestMapping("food/foodDetail.do")
 public String food_detail(HttpServletRequest request,HttpServletResponse response)
 {
	 String fdno=request.getParameter("fdno");
	 FoodDAO dao=FoodDAO.newInstance();
	 JjimDAO jdao= JjimDAO.newInstance();
	 HttpSession session=request.getSession();
	 String id=(String)session.getAttribute("id");
	 int no=jdao.jjimNo(id, Integer.parseInt(fdno), 2);
	 request.setAttribute("no", no);
	 FoodVO vo=dao.foodDetailData(Integer.parseInt(fdno));
	 String poster=vo.getPoster();
	 List<String> posters=new ArrayList<>();
	 StringTokenizer st=new StringTokenizer(poster,"^");
	 while(st.hasMoreTokens())
	 {
		 posters.add(st.nextToken().replace("#", "&"));
	 }
	 String addr1=vo.getAddress().substring(0,vo.getAddress().indexOf("지번"));
	 String addr2=vo.getAddress().substring(vo.getAddress().indexOf("지번")).trim();
	 request.setAttribute("addr1", addr1);
	 request.setAttribute("addr2", addr2);
	 request.setAttribute("posters", posters);
	 request.setAttribute("size", posters.size()-1);
	 request.setAttribute("vo", vo);
	 
	 Cookie[] cookies=request.getCookies();
	 List<FoodVO> clist=new ArrayList<>();
	 if(cookies!=null)
	 {
		 for(int i=cookies.length-1;i>=0;i--)
		 {
			 if(cookies[i].getName().startsWith("food"))
			 {
				 String fdno2=cookies[i].getValue();
				 FoodVO fvo=dao.foodDetailData(Integer.parseInt(fdno2));
				 clist.add(fvo);
			 }
		 }
	 }
	 
	 request.setAttribute("clist", clist);
	 request.setAttribute("fdno", fdno);
	 request.setAttribute("main_jsp", "../food/foodDetail.jsp");
	 
	 HeartDAO hdao=HeartDAO.newInstance();
	 int hno=hdao.heartNo(id, Integer.parseInt(fdno), 2);
	 int htotal=hdao.HeartCountTotal(2, Integer.parseInt(fdno));
	 // jjim vo => no count 
	 request.setAttribute("htotal", htotal);
	 request.setAttribute("hno", hno);
	 
	 String cno = request.getParameter("fdno");
	 ReplyDAO rdao = ReplyDAO.newInstance();

	 List<ReplyVO> list = rdao.replyListData(4, Integer.parseInt(cno));
	 request.setAttribute("rList", list);
	 return "../jsp/main.jsp";
 }
}
