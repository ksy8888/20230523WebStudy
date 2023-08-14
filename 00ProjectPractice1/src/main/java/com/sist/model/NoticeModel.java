package com.sist.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;
import com.sist.dao.NoticeDAO;
import com.sist.vo.NoticeVO;

public class NoticeModel {
	
	
	  @RequestMapping("notice/notice_list.do") 
	  public String notice_list(HttpServletRequest request, HttpServletResponse response) {
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

		  
			request.setAttribute("main_jsp", "../notice/notice_list.jsp"); 
			return "../jsp/main.jsp"; 
	  }
	  
	  @RequestMapping("notice/notice_detail.do")
	  public String notice_detail(HttpServletRequest request, HttpServletResponse response) {
		    String no = request.getParameter("no");
			NoticeDAO dao = NoticeDAO.newInstance();
			NoticeVO vo = dao.noticeDetailData(Integer.parseInt(no));
			
			String[] msg= {"","일반 공지","레시피 공지","맛집 공지","스토어 공지","공유주방 공지"};		
			vo.setNotice_type(msg[vo.getType()]);
			
			request.setAttribute("vo", vo);
			request.setAttribute("main_jsp", "../notice/notice_detail.jsp");
			
			return "../jsp/main.jsp"; 
	  }
	 
}
