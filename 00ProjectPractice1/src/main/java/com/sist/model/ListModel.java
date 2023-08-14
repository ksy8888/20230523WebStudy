package com.sist.model;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.*;


import com.sist.controller.RequestMapping;
import com.sist.dao.HeartDAO;
import com.sist.dao.JjimDAO;
import com.sist.dao.RecipeDAO;
import com.sist.dao.ReplyDAO;
import com.sist.vo.RecipeVO;
import com.sist.vo.ReplyVO;

public class ListModel {

	@RequestMapping("list/recipeList.do") //URL 뒤에 붙는 페이지 설계
public String recilpeList_page(HttpServletRequest request,HttpServletResponse response)
 {
	 RecipeDAO dao=RecipeDAO.newInstance();
	 String page = request.getParameter("page");
	 String type = request.getParameter("type");
	 final int BLOCK=10;
	 if(page==null)
		 page="1";
	 
	 if(type==null)
		 type="all";
	 int curpage=Integer.parseInt(page);
	 int startpage=((curpage-1)/BLOCK*BLOCK)+1;
	 int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
	 int totalpage=dao.recipeTotalPage(type);
	 if(endpage>totalpage)
			endpage=totalpage; 
	 List<RecipeVO> rlist=dao.recipeCategoryDataList(type,curpage);
	 request.setAttribute("curpage", curpage);
	 request.setAttribute("totalpage", totalpage);
	 request.setAttribute("startpage", startpage);
	 request.setAttribute("endpage", endpage);
	 request.setAttribute("rlist", rlist);
	 request.setAttribute("type", type);
	 request.setAttribute("main_jsp", "../list/recipeList.jsp");
	 return "../jsp/main.jsp";
 }
	@RequestMapping("list/recipeDetail_before.do")
	public String recipe_Detail_before(HttpServletRequest request,HttpServletResponse response)
	{
		String rdno=request.getParameter("rdno");
		Cookie cookie=new Cookie("recipe_"+rdno, rdno); //쿠키생성
		// 저장의 위치 결정
		cookie.setPath("/");
		// 저장 기간 설정 
		cookie.setMaxAge(60*60*24);
		// 전송
		response.addCookie(cookie);
		return "redirect:../list/recipeDetail.do?rdno="+rdno;
	}
	@RequestMapping("list/recipeDetail.do") //URL 뒤에 붙는 페이지 설계
	public String recilpeDetail_page(HttpServletRequest request,HttpServletResponse response)
	 {
		 RecipeDAO dao=RecipeDAO.newInstance();
		 String rdno=request.getParameter("rdno");
		 RecipeVO rvo=dao.recipeDetailData(Integer.parseInt(rdno));
		 JjimDAO jdao=JjimDAO.newInstance();
		 HttpSession session=request.getSession();
		 String id=(String)session.getAttribute("id");
//		 int count=jdao.JjimCount(id, Integer.parseInt(rdno), 1);
		 int  no=jdao.jjimNo(id,Integer.parseInt(rdno),1);
//		 if(count>0)
//		 {
//			 no=jdao.jjimNo(id,Integer.parseInt(rdno),1);
//		 }
		 
		 HeartDAO hdao=HeartDAO.newInstance();
		 int hno=hdao.heartNo(id, Integer.parseInt(rdno), 1);
		 int htotal=hdao.HeartCountTotal(1, Integer.parseInt(rdno));
		 // jjim vo => no count 
		 request.setAttribute("htotal", htotal);
		 request.setAttribute("hno", hno);
		 
		 request.setAttribute("no", no);
//		 request.setAttribute("count", count);
		 request.setAttribute("rvo", rvo);
		 request.setAttribute("rdno", rdno);
		 
		 List<String> step_post= new ArrayList<String>();
		 List<String> step_text= new ArrayList<String>();
		 StringTokenizer st=new StringTokenizer(rvo.getStep_pos(),"^");
		 StringTokenizer st1=new StringTokenizer(rvo.getStep_text(),"^");
		 while(st.hasMoreTokens())
		 {
			 step_post.add(st.nextToken());
			 
		 }
		 while(st1.hasMoreTokens())
		 {
			 step_text.add(st1.nextToken());
			 
		 }
		 if(step_post.size()== step_text.size())
		 {
		 request.setAttribute("step_post", step_post);
		 request.setAttribute("step_text", step_text);
		 }
		 else if(step_post.size()> step_text.size())
		 {
			int minus = step_post.size()-step_text.size();
			for(int i=0; i<minus;i++)
			{
				step_text.add(" ");
			}
			 request.setAttribute("step_post", step_post);
			 request.setAttribute("step_text", step_text);
		 }
		 else
		 {
			 int minus = step_text.size()-step_post.size();
			 for(int i=0; i<minus;i++)
				{
					step_post.add(" ");
				}
				 request.setAttribute("step_post", step_post);
				 request.setAttribute("step_text", step_text);
		 }
		 Cookie[] cookies= request.getCookies();
		 List<RecipeVO> clist=new ArrayList<>();
		 if(cookies!=null)
		 {
			 for(int i=cookies.length-1;i>=0;i--)
			 {
				 if(cookies[i].getName().startsWith("recipe"))
				 {
					 String rdno2= cookies[i].getValue();
					 RecipeVO vo=dao.recipeDetailData(Integer.parseInt(rdno2));
					 clist.add(vo);
				 }
			 }
		 }
		 request.setAttribute("clist", clist);
		 
		 request.setAttribute("main_jsp", "../list/recipeDetail.jsp");
		 
		 String cno = request.getParameter("rdno");
		 ReplyDAO rdao = ReplyDAO.newInstance();

		 List<ReplyVO> list = rdao.replyListData(1, Integer.parseInt(cno));

		 request.setAttribute("rList", list);
		 return "../jsp/main.jsp";
	 }
//	@RequestMapping("list/recipeCookieDel.do")
//	public String recipe_cookieDel(HttpServletRequest request,HttpServletResponse response)
//	{
//		String rdno=request.getParameter("rdno");
//		Cookie[] cookies = request.getCookies();
//	
//			if(cookies!=null)
//			{
//				for(int i=0; i<cookies.length;i++)
//				{
//					if(cookies[i].getName().startsWith("recipe_"))
//					{
//						cookies[i].setPath("/");
//						cookies[i].setMaxAge(0);
//						response.addCookie(cookies[i]);
//					}
//				}
//			}
//			return "redirect:../list/recipeDetail.do";
//			
//			
//	}
}
