package com.sist.food;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;


/*
 	@GetMapping => GET
 	@PostMapping => POST
 	@RequestMapping => GET/POST
 	----------------------------- 400 오류(GET,POST맞지않았을때)
 */

@WebServlet("/FoodSearchServlet")
public class FoodSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//service는 doGET , doPOST 동시에 사용가능
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		//사용자 요청값을 받는다
		request.setCharacterEncoding("UTF-8");
		
		String addr = request.getParameter("addr");	//name값 => "addr"
		if(addr==null) 	//주소없을 때 default로 마포를 설정
			addr="마포";
		
		String strPage = request.getParameter("page");	/// 아래 for문에서 page받음
		if(strPage==null)	//이 부분 없으면 500오류
			strPage="1";
		
		int curpage = Integer.parseInt(strPage);	//strPage에 null값 들어오기 때문에 위에서 default 설정 필요함
		
		FoodDAO2 dao = FoodDAO2.newInstance();		////
		List<FoodVO1> list = dao.foodFindData(addr, curpage);
		int totalpage =(int)(Math.ceil(dao.foodRowCount(addr)/12.0));
		int count = dao.foodRowCount(addr); //검색결과 몇건 ~
		final int BLOCK=5;
		//curpage=1 => startPage=1
		//curpage=2 => (2-1)/5 => 0
		int startPage = ((curpage-1)/BLOCK*BLOCK)+1;
		int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		// < [1][2][3][4][5] >
		//curpage => 1~5 => startPage=1 , endPage=5 을 유지
		// < [6][7][8][9][10] >
		
		//화면
		/*
		 <ul class="pagination">
		  <li><a href="#">1</a></li>
		  <li><a href="#">2</a></li>
		  <li><a href="#">3</a></li>
		  <li><a href="#">4</a></li>
		  <li><a href="#">5</a></li>
		</ul>
		 */
		if(endPage > totalpage)	//마지막 페이지
			endPage=totalpage;	// 5 10 15 20 25 ...
		
		PrintWriter out = response.getWriter();
		//페이징 기법
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<style>");
		out.println(".container{margin-top:50px}");
		out.println(".row{");
		out.println("margin:0px auto;");
		out.println("width:1024px}</style>");
		
		out.println("</head>");
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<div class=row>");
		out.println("<table class = table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<form method=post action=FoodSearchServlet>");
		out.println("<input type=text name=addr size=25 class=input-sm>");
		out.println("<input type=submit  value=검색 class=\"btn btn-sm btn-danger\">");
		out.println("</form>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<div style=\"height:30px\"></div>");
		
		for(FoodVO1 vo:list) {
			out.println("<div class=\"col-md-3\">");	//한줄에 4개 출력 >> 12/3
			out.println("<div class=\"thumbnail\">");
			out.println("<a href=\"#\">");
			out.println("<img src=\""+vo.getPoster()+"\" style=\"width:100%\">");
			out.println("<div class=\"caption\">");
			out.println("<p style=\"font-size:9px\">"+vo.getName()+"</p>");
			out.println("</div>");
			out.println("</a>");
			out.println("</div>");
			out.println("</div>");
		}
		out.println("</div>");	//row
		out.println("<div style=\"height:10px\"></div>");
		out.println("<div class=row>");
		out.println("<div class=text-center>");
		out.println("<ul class=pagination>");
		out.println("<li><a href=#>&lt;</a></li>");
		for(int i=startPage; i<=endPage; i++)
		{
			out.println("<li "+(curpage==i?"class=active":"")+"><a href=FoodSearchServlet?page="+i+">"+i+"</a></li>");
		}
		out.println("<li><a href=#>&gt;</a></li>");
		out.println("</ul>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>");	//container
		out.println("</body>");	
		out.println("</html>");
		
	}

}
