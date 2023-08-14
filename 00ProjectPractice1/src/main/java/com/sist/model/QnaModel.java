package com.sist.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.QnaBoardDAO;
import com.sist.vo.QnaVO;

public class QnaModel {
	@RequestMapping("QnA/list.do")
	public String qna_list(HttpServletRequest request, HttpServletResponse response) {
		
		 String page = request.getParameter("page");
			if(page==null) {
				page="1";
			}
		int curpage = Integer.parseInt(page);
			
		final int BLOCK=5;
		int startpage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
			
		QnaBoardDAO dao = QnaBoardDAO.newInstance();
		int totalpage = dao.QnATotalPage();
		if(endpage>totalpage)
			endpage=totalpage;
		
		List<QnaVO> list = dao.QnAListData(curpage);
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("list", list);
		
		request.setAttribute("main_jsp", "../QnA/list.jsp");
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("QnA/insert.do")
	public String qna_insert(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("main_jsp", "../QnA/insert.jsp");
		return "../jsp/main.jsp";
		
	}
	
	@RequestMapping("QnA/insert_ok.do")
	public String qna_insert_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		HttpSession session = request.getSession();
		String id= (String)session.getAttribute("id");
		String name= (String)session.getAttribute("name");
		
		QnaVO vo = new QnaVO();
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setId(id);
		vo.setName(name);
		
		QnaBoardDAO dao = QnaBoardDAO.newInstance();
		dao.QnaInsert(vo);
		
		return "redirect:../QnA/list.do";
	}
	
	@RequestMapping("QnA/detail.do")
    public String replyboard_detail(HttpServletRequest request, HttpServletResponse response) {
		
		String no = request.getParameter("no");
		QnaBoardDAO dao = QnaBoardDAO.newInstance();
		
		//연동
		QnaVO vo = dao.replyBoardDetailData(Integer.parseInt(no));
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../QnA/detail.jsp");
		return "../jsp/main.jsp";
	}
	//삭제
	@RequestMapping("QnA/delete.do")
	public String replyboard_delete(HttpServletRequest request, HttpServletResponse response) {
		String no =  request.getParameter("no");
		QnaBoardDAO dao = QnaBoardDAO.newInstance();
		dao.QnABoardDelete(Integer.parseInt(no));
		
		return "redirect:../QnA/list.do";
	}
	
	@RequestMapping("QnA/update.do")
	public String replyboard_update(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		
		QnaBoardDAO dao = QnaBoardDAO.newInstance();
		QnaVO vo = dao.QnAUpdateData(Integer.parseInt(no));
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../QnA/update.jsp");
		
		return "../jsp/main.jsp";
	}
	@RequestMapping("QnA/update_ok.do")
	public String replyboard_update_ok(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String no = request.getParameter("no");
		
		QnaVO vo = new QnaVO();
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setNo(Integer.parseInt(no));
		
		QnaBoardDAO dao = QnaBoardDAO.newInstance();
		dao.QnAUpdate(vo);
		
		return "redirect:../QnA/detail.do?no="+no;
	}
}
