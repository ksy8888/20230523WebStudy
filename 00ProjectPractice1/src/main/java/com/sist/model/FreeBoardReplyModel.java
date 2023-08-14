package com.sist.model;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.controller.RequestMapping;
import com.sist.dao.*;
import com.sist.vo.*;

public class FreeBoardReplyModel {

	@RequestMapping("board/reply_insert.do")
	public String reply_insert(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			
		} catch (Exception e) {}
		
		int size=1024*1024*100;
		String path="C:\\webDev\\webStudy\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\00ProjectPractice\\board\\image";
		String enctype="UTF-8";
		MultipartRequest mr = new MultipartRequest(request,path,size,enctype,new DefaultFileRenamePolicy());
		String bno = mr.getParameter("bno");
		//String msg = request.getParameter("msg");
		
		//세션
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		
		FreeBoardReplyVO vo = new FreeBoardReplyVO();
		//vo.setBno(Integer.parseInt(bno));		
		//vo.setBno(mr.getParameter(Integer.parseInt(bno)));
		vo.setBno(Integer.parseInt(mr.getParameter("bno")));
		vo.setMsg(mr.getParameter("msg"));
		vo.setId(id);
		vo.setName(name);
		
		//vo.setMsg(msg);
		
		
		String filename = mr.getFilesystemName("upload");			
		  if(filename==null) { //업로드가 안된 상태 			
			vo.setFilename("");
			vo.setFilesize(0);				 			 
		  } else { //업로드가 된 상태 
			 File file = new File(path+"\\"+filename);
			// System.out.println(file);
			 vo.setFilename(filename); 
			 vo.setFilesize((int)file.length());
		}
		
		//DAO
		FreeBoardReplyDAO dao = FreeBoardReplyDAO.newInstance();
		dao.replyInsert(vo);
		
		return "redirect:../board/board_detail.do?bno="+bno;
	}
	
	@RequestMapping("board/reply_delete.do")
	public String reply_delete(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		String bno = request.getParameter("bno");
		
		//DAO에 삭제 요청
		FreeBoardReplyDAO dao = FreeBoardReplyDAO.newInstance();
		dao.replyDelete(Integer.parseInt(no));
		return "redirect:../board/board_detail.do?bno="+bno;
	}
	
	@RequestMapping("board/reply_update.do") //bno,no
	public String reply_update(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String bno = request.getParameter("bno");		
		String no = request.getParameter("no");
		String msg = request.getParameter("msg");
		
		FreeBoardReplyDAO dao = FreeBoardReplyDAO.newInstance();
		dao.replyUpdate(Integer.parseInt(no), msg);
		return "redirect:../board/board_detail.do?bno="+bno;
	}
	
	//대댓글
	@RequestMapping("board/reply_reply_insert.do")
	public String reply_reply_insert(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String bno = request.getParameter("bno");
		String pno = request.getParameter("pno");
		String msg = request.getParameter("msg");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		
		FreeBoardReplyVO vo= new FreeBoardReplyVO();
		vo.setBno(Integer.parseInt(bno));
		vo.setMsg(msg);
		vo.setId(id);
		vo.setName(name);
		
		FreeBoardReplyDAO dao = FreeBoardReplyDAO.newInstance();
		dao.replyReplyInsert(Integer.parseInt(pno), vo);
		return "redirect:../board/board_detail.do?bno="+bno;
	}
}