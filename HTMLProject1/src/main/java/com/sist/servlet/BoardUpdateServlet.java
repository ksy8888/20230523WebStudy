package com.sist.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;


@WebServlet("/BoardUpdateServlet")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전송 방식
				response.setContentType("text/html;charset=UTF-8");
			
				String no = request.getParameter("no"); //no라는 변수의 값을 가져와라
				
			//오라클에서 값을 얻어온다
				BoardDAO dao = BoardDAO.newInstance();
				BoardVO vo = dao.BoardDetailData(Integer.parseInt(no));
				
			//브라우저가 읽을 수 있게 출력
				PrintWriter out = response.getWriter();
				
				out.print("<html>");
				out.print("<head>");
				out.print("<link rel = stylesheet href=HTML/table.css>");
				out.print("</head>");
				out.print("<body>");
				out.print("<center>");
				out.print("<h1>수정하기</h1>");
				out.print("<table width=700 class=table_content>");
				
				out.print("<tr>");
				out.print("<th width=20%>번호</th>");
				out.print("<td width=30% align=center>"+vo.getNo()+"</td>");
				out.print("<th width=20%>작성일</th>");
				out.print("<td width=30% align=center>"+vo.getDbday()+"</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<th width=20%>이름</th>");
				out.print("<td width=30% align=center>"+vo.getName()+"</td>");
				out.print("<th width=20%>조회수</th>");
				out.print("<td width=30% align=center>"+vo.getHit()+"</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<th width=20%>제목</th>");
				out.print("<td colspan=3>"+vo.getSubject()+"</td>");
				out.print("</tr>");
				
				out.print("<tr>");
				out.print("<td colspan=4 height=200 align=left valign=top>");
				out.print("<pre style=\"white-space:pre-wrap\">"+vo.getContent()+"</pre>");
				// style=\"white-space:pre-wrap\" 자동 줄바꿈
				out.print("</td>");
				out.print("</tr>");
				
				
				//out.print("<a href=BoardUpdateServlet?no="+vo.getNo()+">수정</a>&nbsp;");
				//out.print("<a href=BoardListServlet>취소</a>");
				
				out.println("<tr>");
				out.println("<td colspan=2 align=center>");
				out.println("<input type = submit value=수정>");
				out.println("<input type = button value=취소 onclick=\"javascript:history.back()\">");
				out.println("</td>");
				out.print("</tr>");
			
				
				out.print("</table>");
				out.print("</center>");
				out.print("</body>");
				out.print("</html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//	response.setContentType("text/html;charset=UTF-8");
			
		//	String no = request.getParameter("no");
			
		
			
	}

}
