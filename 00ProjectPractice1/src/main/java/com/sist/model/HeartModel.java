package com.sist.model;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.HeartDAO;
import com.sist.dao.JjimDAO;
import com.sist.vo.HeartVO;
import com.sist.vo.JjimVO;

public class HeartModel {
	@RequestMapping("heart/insert.do")
	public void jjim_insert(HttpServletRequest request,HttpServletResponse response) {
		String cno = request.getParameter("jno");
		String type = request.getParameter("types");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		HeartVO vo = new HeartVO();
		System.out.println("insert.do");
		// no, type,id , cno
		vo.setType(Integer.parseInt(type));
		vo.setId(id);
		vo.setCno(Integer.parseInt(cno));
		HeartDAO dao = HeartDAO.newInstance();
		dao.HeartInsert(vo);
		
//		return "redirect:../"+url[Integer.parseInt(type)]+cno;
	}
	
	@RequestMapping("heart/heart_delete.do")
	public void heart_delete(HttpServletRequest request,HttpServletResponse response) {
//		String cno = request.getParameter("jno");
//		String type = request.getParameter("type");
		String no = request.getParameter("no");
		HeartDAO dao = HeartDAO.newInstance();
		dao.heartCancel(Integer.parseInt(no));
//		return "redirect:../"+url[Integer.parseInt(type)]+cno;
	}
}
