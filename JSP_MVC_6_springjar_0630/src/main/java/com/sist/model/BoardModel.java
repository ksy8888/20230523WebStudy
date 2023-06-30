package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.controller.RequestMapping;

//BoardModel.java 클래스 생성후 application.xml에 BoardModel클래스 추가시켜야함
public class BoardModel {
	
	@RequestMapping("board/list.do")
	public String boardList(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("msg", "게시판 목록!!");	//이 request를 
		return "../board/list.jsp";	//얘한테 보냄
	}
	@RequestMapping("board/insert.do")
	public String boardInsert(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("msg", "게시판 추가!!");	//이 request를 
		return "../board/insert.jsp";	//얘한테 보냄
	}
	@RequestMapping("board/update.do")
	public String boardUpdate(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("msg", "게시판 수정!!");	//이 request를 
		return "../board/update.jsp";	//얘한테 보냄
	}
	@RequestMapping("board/delete.do")
	public String boardLDelete(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("msg", "게시판 삭제!!");	//이 request를 
		return "../board/delete.jsp";	//얘한테 보냄
	}
}
