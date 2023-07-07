package com.sist.model;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class FreeBoardReplyModel {
	@RequestMapping("board/reply_insert.do")
	public String reply_insert(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			request.setCharacterEncoding("UTF-8");
			
		} catch (Exception e) {}
		
/*
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());  //session
			ps.setString(3, vo.getName()); //session
			ps.setString(4, vo.getMsg());
 */
		String bno = request.getParameter("bno"); //name=bno value="${vo.no } //bno에는 vo.no값담겨잇음
		String msg = request.getParameter("msg");
		
		//세션에서 id,name값 읽기
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		
		FreeBoardReplyVO vo = new FreeBoardReplyVO();
		vo.setBno(Integer.parseInt(bno));
		vo.setMsg(msg);
		vo.setId(id);
		vo.setName(name);	//얘네들을
		
		//DAO로 전송
		FreeBoardReplyDAO dao = FreeBoardReplyDAO.newInstance();
		dao.replyInsert(vo);	//여기에
		
		return "redirect:../board/detail.do?no="+bno;
	}
	
	@RequestMapping("board/reply_update.do")
	public String reply_update(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			
		} catch (Exception e) {}
		
		String bno = request.getParameter("bno"); //게시물 번호 (이동 목적)
		String msg = request.getParameter("msg");
		String no = request.getParameter("no");   //댓글 번호 (수정 목적)
		
		//DAO 연결 => 오라클 변경
		FreeBoardReplyDAO dao = FreeBoardReplyDAO.newInstance();
		dao.replyUpdate(Integer.parseInt(no), msg);
		
		return "redirect:../board/detail.do?no="+bno;
	}
}
