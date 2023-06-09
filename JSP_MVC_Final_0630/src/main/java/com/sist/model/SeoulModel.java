package com.sist.model;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.*;
import com.sist.vo.*;
import com.sist.common.*;
import com.sist.controller.RequestMapping;

public class SeoulModel {
	@RequestMapping("seoul/seoul_list.do")
	public String seoul_list(HttpServletRequest request, HttpServletResponse response) {
		
		String page = request.getParameter("page");
		if(page ==null) {
			page="1";
		}
		String type = request.getParameter("type");
		if(type==null) {
			type="1"; //seoul_location
		}
		
		 int curpage = Integer.parseInt(page);
		 //DAO연동
		 SeoulDAO dao = SeoulDAO.newInstance();
		 //VO
		 List<SeoulVO> list = dao.seoulListData(curpage, Integer.parseInt(type));
		 
		 int totalpage=dao.seoulTotalPage(Integer.parseInt(type));
		 final int BLOCK=5;		// 1~5페이지
		 int startPage=((curpage-1)/BLOCK*BLOCK)+1; 
		 int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		 if(endPage>totalpage) {
			 endPage = totalpage;
		 }
		 
		 //전송
		 request.setAttribute("curpage", curpage);
		 request.setAttribute("totalpage", totalpage);
		 request.setAttribute("list", list);
		 request.setAttribute("startPage", startPage);
		 request.setAttribute("endPage", endPage);
		 request.setAttribute("type", type);
		 request.setAttribute("main_jsp", "../seoul/seoul_list.jsp");
		 
		 String[] msg = {"","서울 명소","서울 자연&관광","서울 쇼핑"};
		 request.setAttribute("msg", msg[Integer.parseInt(type)]);
		 
		 CommonModel.commonRequestData(request);
		return "../main/main.jsp";
	}
	
	@RequestMapping("seoul/seoul_detail.do")
	public String seoul_detail(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		String type = request.getParameter("type");
		
		//DAO
		SeoulDAO dao = SeoulDAO.newInstance();
		SeoulVO vo = dao.seoulDetailData(Integer.parseInt(no), Integer.parseInt(type));
		request.setAttribute("vo", vo);
		request.setAttribute("type", type);
		
		request.setAttribute("main_jsp", "../seoul/seoul_detail.jsp");
		
		//100-794 서울특별시 중구 남대문로 39, 한국은행건물 (남대문로3가)
		String addr = vo.getAddress();
		String addr1 = addr.substring(addr.indexOf(" ")+1);
		String addr2 = addr1.substring(addr1.indexOf(" ")+1);
		String addr3 = addr2.substring(0,addr2.indexOf(" "));
		System.out.println(addr3);
		request.setAttribute("addr", addr3.trim()+ " 맛집");
		return "../main/main.jsp";
	}
}
