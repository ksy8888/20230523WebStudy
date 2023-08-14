package com.sist.model;



import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;



import com.sist.controller.RequestMapping;

import com.sist.dao.ReplyDAO;

import com.sist.vo.ReplyVO;



public class ReplyModel {

	

	//list/recipeDetail.do?rdno=3

	// share/shareDetail.do?skdno=1

	private String[] url= {"","../list/recipeDetail.do?rdno=","../share/shareDetail.do?skdno=","../product/productDetail.do?pdno=","../food/foodDetail.do?fdno="};

	

	@RequestMapping("reply/reply_insert.do")

	public String reply_insert(HttpServletRequest request, HttpServletResponse response) {

		try {

			request.setCharacterEncoding("UTF-8");		

		} catch (Exception e) {}

		String cno = request.getParameter("rdno");

		String type = request.getParameter("type");

		String msg = request.getParameter("msg");

		

		HttpSession session = request.getSession();

		String id = (String)session.getAttribute("id");

		String name = (String)session.getAttribute("name");

		

		ReplyVO vo = new ReplyVO();

		vo.setCno(Integer.parseInt(cno));

		vo.setType(Integer.parseInt(type));

		vo.setMsg(msg);

		vo.setId(id);

		vo.setName(name);

		

		//DAO

		ReplyDAO dao = ReplyDAO.newInstance();

		dao.replyInsert(vo);

		

		return "redirect:"+url[Integer.parseInt(type)]+cno;

	}

	

	@RequestMapping("reply/reply_update.do")

	public String reply_update(HttpServletRequest request, HttpServletResponse response) {

		try {

			request.setCharacterEncoding("UTF-8");

		} catch (Exception e) {}

		 /*<input type=hidden name=rdno value="${rvo.rdno }"> 		  	

  	     <input type=hidden name=no value="${rpvo.no }"> 

  	     <input type="hidden" name="type" value="1">

  	     <textarea rows="5" cols="60" name="msg"

  	     */

		String cno = request.getParameter("rdno");

		String no = request.getParameter("no");

		String type = request.getParameter("type");

		String msg = request.getParameter("msg");

		

		ReplyDAO dao = ReplyDAO.newInstance();

		dao.replyUpdate(Integer.parseInt(no), msg);

		

		return "redirect:"+url[Integer.parseInt(type)]+cno;

	}

	

	@RequestMapping("reply/reply_delete.do") 

	public String reply_delete(HttpServletRequest request, HttpServletResponse response) { 

		String no = request.getParameter("no");

		String type = request.getParameter("type");

		String cno = request.getParameter("rdno");

		

		ReplyDAO dao = ReplyDAO.newInstance();

		dao.replyDelete(Integer.parseInt(no));

		return "redirect:"+url[Integer.parseInt(type)]+cno;

	}

	

}