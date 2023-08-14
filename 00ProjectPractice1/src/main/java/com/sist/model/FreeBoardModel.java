package com.sist.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sist.controller.RequestMapping;
import com.sist.dao.FreeBoardDAO;
import com.sist.dao.FreeBoardLikeDAO;
import com.sist.dao.FreeBoardReplyDAO;
import com.sist.vo.FreeBoardCategoryVO;
import com.sist.vo.FreeBoardReplyVO;
import com.sist.vo.FreeBoardVO;

public class FreeBoardModel {
	@RequestMapping("board/board_find.do")
	   public String board_find(HttpServletRequest request, HttpServletResponse response) {
	      
	      try {
	         request.setCharacterEncoding("UTF-8");
	      } catch (Exception e) {}
	      
	      String fd1 = request.getParameter("fd1"); //검색어
	      if(fd1==null) {
	         fd1 = " ";
	      }
	      String type = request.getParameter("type"); //제목인지
	      
	      
	      if(type.equals("제목")) {
	         type = "subject";
	      } else if(type.equals("작성자")) {
	         type = "name";
	      } else if(type.equals("내용")) {
	         type = "content";
	      }
	      
	      String page = request.getParameter("page");
	      final int BLOCK=5;
	      if(page==null)
	         page="1";
	      
	      FreeBoardDAO dao = FreeBoardDAO.newInstance();
	      
	      int curpage = Integer.parseInt(page);
	      int startpage = ((curpage-1)/BLOCK*BLOCK)+1;
	      int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
	      int totalpage = dao.freeboardFindTotalPage(fd1, type);
	      if(endpage>totalpage)
	         endpage=totalpage;
	      List<FreeBoardVO> list = dao.boardFindData(fd1,type,curpage);
//	      System.out.println(fd1);
//	      System.out.println(type);
	      request.setAttribute("list", list);
	      request.setAttribute("fd1", fd1);
	      request.setAttribute("type", type);
	      request.setAttribute("today", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
	      request.setAttribute("curpage", curpage);
	      request.setAttribute("totalpage", totalpage);
	      request.setAttribute("startpage", startpage);
	      request.setAttribute("endpage", endpage);
	      System.out.println(startpage);
	      System.out.println(curpage);
	      request.setAttribute("main_jsp", "../board/board_search.jsp");
	      return "../jsp/main.jsp";
	   }
	@RequestMapping("board/list.do")
	public String board_list(HttpServletRequest request, HttpServletResponse response) {
		
		FreeBoardDAO dao = FreeBoardDAO.newInstance();		
		String page = request.getParameter("page");
				
		String cno = request.getParameter("cno");		  
	//			
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		FreeBoardVO vo = new FreeBoardVO();
		vo.setId(id);
		
		final int BLOCK=5;
		if(page==null)
			page="1";
		if(cno==null)
			cno="0";
		int curpage = Integer.parseInt(page);
		int startpage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		int totalpage = dao.freeboardTotalPage(Integer.parseInt(cno));
		if(endpage>totalpage)
			endpage=totalpage;
		
		int curcno = Integer.parseInt(cno);
		List<FreeBoardVO> list = dao.freeboardListData(curpage,curcno);	
		request.setAttribute("curpage", curpage);
		request.setAttribute("totalpage", totalpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("curcno", curcno);
		request.setAttribute("list", list);
		request.setAttribute("cno", cno);
		//System.out.println(cno);
		request.setAttribute("main_jsp", "../board/list.jsp");
		return "../jsp/main.jsp";
		
	}
	
	@RequestMapping("board/board_detail.do")
	public String board_detail(HttpServletRequest request, HttpServletResponse response) {
		
		String bno = request.getParameter("bno");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		if(id != null) {
			FreeBoardLikeDAO ldao = FreeBoardLikeDAO.newInstance();
			int like_count = ldao.FreeBoardLikeOk(Integer.parseInt(bno), id);
			request.setAttribute("like_count", like_count);
		}
		
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		FreeBoardVO vo = dao.freeboardDetailData(Integer.parseInt(bno));
		request.setAttribute("vo", vo);
		
//		System.out.println(vo.getFilename());
		request.setAttribute("main_jsp", "../board/board_detail.jsp");
		
//댓글 읽기		
		FreeBoardReplyDAO fdao = FreeBoardReplyDAO.newInstance();
		List<FreeBoardReplyVO> list = fdao.replyListData(Integer.parseInt(bno));
		
		int reply_count = fdao.replyCount(Integer.parseInt(bno));
		request.setAttribute("list", list);
		request.setAttribute("reply_count", reply_count);
		
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("board/board_insert.do")
	public String board_insert(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("main_jsp", "../board/board_insert.jsp");
		return "../jsp/main.jsp";
	}
	
	@RequestMapping("board/board_insert_ok.do")
	public String board_insert_ok(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		  
		int size=1024*1024*100;
		//로컬주소 >> 나중에 서버컴의 로컬주소로 바꿔줘야함
//		 String path="C:\\webDev\\webStudy\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\0gotgan1\\board\\image"; 
		 String path="C:\\webDev\\webStudy\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\00ProjectPractice\\board\\image";
		//String path="../ii";
		String enctype="UTF-8";
		
		MultipartRequest mr = new MultipartRequest(request,path,size,enctype,new DefaultFileRenamePolicy());
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		String name = (String)session.getAttribute("name");
		
		FreeBoardVO vo = new FreeBoardVO();
	//	vo.setName(mr.getParameter("name"));
		vo.setSubject(mr.getParameter("subject"));
		vo.setContent(mr.getParameter("content"));
		vo.setPwd(mr.getParameter("pwd"));	
		vo.setId(id);
		vo.setName(name);
		
		String bc = request.getParameter("selectCategory");
		if(bc==null) {
			bc="1";
		}
		vo.setCno(Integer.parseInt(mr.getParameter("selectCategory")));

		
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
		  
		FreeBoardDAO dao = FreeBoardDAO.newInstance();	
		dao.freeboardInsert(vo);
		return "redirect:../board/list.do";
	}
	
	//삭제
	@RequestMapping("board/board_delete.do") 
	public void board_delete(HttpServletRequest request, HttpServletResponse response) {
		String bno = request.getParameter("bno");
		String pwd = request.getParameter("pwd");
		
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		String res = dao.freeboardDelete(Integer.parseInt(bno), pwd);
		
		try {
			PrintWriter out = response.getWriter();
			out.println(res); // Ajax에서 읽어서 처리 yes,no
		} catch (Exception e) {}
	}
	
	//수정
	@RequestMapping("board/board_update.do") 
	public String board_update(HttpServletRequest request, HttpServletResponse response) {
		
		String bno = request.getParameter("bno");
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		FreeBoardVO vo = dao.freeboardUpdateData(Integer.parseInt(bno));
		
		request.setAttribute("vo", vo);
		request.setAttribute("main_jsp", "../board/board_update.jsp");
		return "../jsp/main.jsp";
	}
	@RequestMapping("board/board_update_ok.do")
	public String board_update_ok(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		
		FreeBoardVO vo = new FreeBoardVO();
		vo.setName(request.getParameter("name"));
		vo.setSubject(request.getParameter("subject"));
		vo.setContent(request.getParameter("content"));
		vo.setPwd(request.getParameter("pwd"));
		vo.setBno(Integer.parseInt(request.getParameter("bno")));
		
		FreeBoardDAO dao = FreeBoardDAO.newInstance();
		boolean bCheck = dao.freeboardUpdate(vo);
		
		request.setAttribute("bCheck", bCheck);
		request.setAttribute("bno", vo.getBno());
		return "../board/board_update_ok.jsp";
	}
	
}
