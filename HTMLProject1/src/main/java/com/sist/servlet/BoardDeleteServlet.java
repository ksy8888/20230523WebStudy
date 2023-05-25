package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;

// 비밀번호 받아서 넘겨줘야하기 때문에 Get, Post 둘 다 필요함

// BoardDetailServlet , BoardDeleteServlet
@WebServlet("/BoardDeleteServlet")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// GET에서 화면 출력 => HTML => 삭제 위해 비밀번호 입력 창 만들어줌
	//화면을 띄우면서 INSERT 안됨 >> 처리하는것을 따로둬야함(POST)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//전송 방식 => HTML
		response.setContentType("text/html;charset=UTF-8");
		//클라이언트가 전송한 값을 받는다
		//BoardDetailServlet에서 받아옴
		String no = request.getParameter("no");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel = stylesheet href=HTML/table.css>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>삭제하기</h1>");
		out.println("<form method=post action=BoardDeleteServlet>");
		out.println("<table class = table_content width=300>");
		out.println("<tr>");
		out.println("<th width = 30%>비밀번호</th>");
		out.println("<td width = 70%><input type=password name=pwd size=15 required>");
		out.println("<input type=hidden name=no value="+no+">"); // 사용자에게 보여주면 안되는 데이터 => 화면 출력 없이 데이터를 전송 => hidden 	//--
		out.println("</td></tr>");		
		out.println("<tr>");
		out.println("<td colspan=2 align=center>");
		out.println("<input type = submit value=삭제>");
		out.println("<input type = button value=취소 onclick=\"javascript:history.back()\">");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	
	// POST 는 요청에 대한 처리 담당 (비밀번호 확인해서 넘겨줘야함)  (삭제처리)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		//사용자 전송값 받기
		String no = request.getParameter("no"); 	//--
		String pwd = request.getParameter("pwd");
		
		//디코딩 => 한글이 넘어왔을때만 사용
		// 숫자,알파벳은 1,2 byte 동시에 처리
		PrintWriter out = response.getWriter();
		
		//db DAO 연동
		BoardDAO dao = BoardDAO.newInstance();
		boolean bCheck = dao.boardDelete(Integer.parseInt(no), pwd);
		
		//수정 => 상세보기창으로 이동 Detail으로
		
		if(bCheck==true) {
			//목록으로 이동
			response.sendRedirect("BoardListServlet");
			
		}
		else {
			// 삭제창으로 이동
			out.println("<script>");
			out.println("alert(\"비밀번호 틀립니다!\");");
			out.println("history.back();");
			out.println("</script>");
		}
	}

}
