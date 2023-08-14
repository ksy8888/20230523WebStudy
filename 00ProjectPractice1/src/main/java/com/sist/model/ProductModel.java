package com.sist.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.dao.HeartDAO;
import com.sist.dao.JjimDAO;
import com.sist.dao.ProductDAO;
import com.sist.dao.ReplyDAO;
import com.sist.dao.ShareDAO;
import com.sist.vo.ProductVO;
import com.sist.vo.RecipeVO;
import com.sist.vo.ReplyVO;
import com.sist.vo.ShareVO;

public class ProductModel {
	@RequestMapping("product/productList.do")
	public String share_main(HttpServletRequest request,HttpServletResponse response)
  {
		String page = request.getParameter("page");
		String type=request.getParameter("type");
		 final int BLOCK=10;
		 if(page==null)
			 page="1";
		 if(type==null)
			 type="0";
		 ProductDAO pdao=ProductDAO.newInstance();
		 int curpage=Integer.parseInt(page);
		 int startpage=((curpage-1)/BLOCK*BLOCK)+1;
		 int endpage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
		 int totalpage=pdao.productTotalPage(Integer.parseInt(type));
		 if(endpage>totalpage)
				endpage=totalpage; 
		 List<ProductVO> plist=pdao.productListData(Integer.parseInt(type),curpage);
		 request.setAttribute("curpage", curpage);
		 request.setAttribute("totalpage", totalpage);
		 request.setAttribute("startpage", startpage);
		 request.setAttribute("endpage", endpage);
		 request.setAttribute("plist", plist);
		 request.setAttribute("type", type);
	    request.setAttribute("main_jsp", "../product/productList.jsp");
	  return "../jsp/main.jsp";
  }
	@RequestMapping("product/productDetail_before.do")
	public String recipe_Detail_before(HttpServletRequest request,HttpServletResponse response)
	{
		String pdno=request.getParameter("pdno");
		Cookie cookie=new Cookie("product_"+pdno, pdno); //쿠키생성
		// 저장의 위치 결정
		cookie.setPath("/");
		// 저장 기간 설정 
		cookie.setMaxAge(60*60*24);
		// 전송
		response.addCookie(cookie);
		return "redirect:../product/productDetail.do?pdno="+pdno;
	}
	@RequestMapping("product/productDetail.do")
	public String product_detail(HttpServletRequest request,HttpServletResponse response)
	{
		ProductDAO pdao=ProductDAO.newInstance();
		String pdno=request.getParameter("pdno");
		ProductVO pvo=pdao.productDetailData(Integer.parseInt(pdno));
		JjimDAO jdao=JjimDAO.newInstance();
		HttpSession session=request.getSession();
		String id=(String)session.getAttribute("id");
		
		int no=jdao.jjimNo(id, Integer.parseInt(pdno), 3);
		
		String price=pvo.getPriced_sale();
		int p=Integer.parseInt(price.replaceAll("[^0-9]", ""));
		
		request.setAttribute("price", p); //승주의 장바구니 가격
		request.setAttribute("no", no);
		request.setAttribute("pvo", pvo);
		request.setAttribute("pdno", pdno);
		 Cookie[] cookies= request.getCookies();
		 List<ProductVO> clist=new ArrayList<>();
		 if(cookies!=null)
		 {
			 for(int i=cookies.length-1;i>=0;i--)
			 {
				 if(cookies[i].getName().startsWith("product"))
				 {
					 String pdno2= cookies[i].getValue();
					 ProductVO vo=pdao.productDetailData(Integer.parseInt(pdno2));
					 clist.add(vo);
				 }
			 }
		 }
		request.setAttribute("clist", clist);
		HeartDAO hdao=HeartDAO.newInstance();
		 int hno=hdao.heartNo(id, Integer.parseInt(pdno), 3);
		 int htotal=hdao.HeartCountTotal(3, Integer.parseInt(pdno));
		 // jjim vo => no count 
		 request.setAttribute("htotal", htotal);
		 request.setAttribute("hno", hno);
		request.setAttribute("main_jsp", "../product/productDetail.jsp");
		String cno = request.getParameter("pdno");
	    ReplyDAO rdao = ReplyDAO.newInstance();

	    List<ReplyVO> list = rdao.replyListData(3, Integer.parseInt(cno));

	    request.setAttribute("rList", list);
		return "../jsp/main.jsp";
	}
}
