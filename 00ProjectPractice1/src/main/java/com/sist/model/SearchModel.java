package com.sist.model;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
import com.sist.vo.*;
import com.sist.dao.*;
import java.util.*;
public class SearchModel {
	static List<?> list;
	final static int BLOCK=10;
	static int curpage,startpage,endpage,totalpage=0;
	@RequestMapping("search/catefind.do")
	public String cate_find(HttpServletRequest request,HttpServletResponse response)
	{
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
}
