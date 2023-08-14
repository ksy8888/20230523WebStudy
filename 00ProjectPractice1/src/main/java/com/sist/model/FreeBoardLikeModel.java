package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.FreeBoardLikeDAO;
import com.sist.vo.FreeBoardLikeVO;

public class FreeBoardLikeModel {
	@RequestMapping("like/like_insert.do")
	public String like_insert(HttpServletRequest request, HttpServletResponse response) {
		String bno = request.getParameter("bno");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		FreeBoardLikeVO vo = new FreeBoardLikeVO();
		vo.setBno(Integer.parseInt(bno));
		vo.setId(id);
		
		FreeBoardLikeDAO dao = FreeBoardLikeDAO.newInstance();
		dao.FreeBoardLikeInsert(vo);
		return "redirect:../board/board_detail.do?bno="+bno;
	}
	
	@RequestMapping("like/like_delete.do")
	public String like_delete(HttpServletRequest request, HttpServletResponse response) {
		
		String bno = request.getParameter("bno");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		

		FreeBoardLikeVO vo = new FreeBoardLikeVO();
		vo.setBno(Integer.parseInt(bno));
		vo.setId(id);
		
		FreeBoardLikeDAO dao = FreeBoardLikeDAO.newInstance();
		dao.FreeBoardLikeDelete(vo);
		
		return "redirect:../board/board_detail.do?bno="+bno;
	}
}
