package com.sist.model;

import com.sist.controller.RequestMapping;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.*;
import com.sist.vo.*;

public class FoodJJimLikeModel {
	
	//찜하기  >> 찜목록list 은 마이페이지Model에서 처리
	@RequestMapping("jjim/jjim_insert.do")
	public String jjim_insert(HttpServletRequest request, HttpServletResponse response) {
		//데이터얻기
		String fno = request.getParameter("fno");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		FoodJJimVO vo = new FoodJJimVO();
		vo.setId(id);
		vo.setFno(Integer.parseInt(fno));
		FoodJJimLikeDAO dao = FoodJJimLikeDAO.newInstance();
		dao.foodJJimInsert(vo);
		//화면 이동 (서버에서)
		//sendRedirect() => 재호출 => .do (request를 초기화)
		//forward() => 새로운 데이터 전송할 때 사용 (request에 값을 담아서 전송)
		//return "redirect:.."
		
		//redirect는 request를 넘겨주지않고 request가 초기화됨 >> request에 필요한 fno값만 따로 넘겨줌
		// .do 이면 request 안넘어가고 .jsp는 forward로 되어있어서 request넘겨줌(데이터보낼떄_request가 필요할때)
		//request를 초기화 한 다음에 fno값만 보내주겠다
		return "redirect:../food/food_detail.do?fno="+fno;
		
	}
	
	@RequestMapping("jjim/jjim_cancel.do")
	public String jjim_cancel(HttpServletRequest request, HttpServletResponse response) {
		
		String no = request.getParameter("no");
		FoodJJimLikeDAO dao = FoodJJimLikeDAO.newInstance();
		dao.foodJJimCancel(Integer.parseInt(no));
		return "redirect:../mypage/mypage_jjim_list.do";
	}
	
	@RequestMapping("like/like_insert.do")
	public String like_insert(HttpServletRequest request, HttpServletResponse response) {
		
		String fno = request.getParameter("fno");		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		FoodLikeVO vo = new FoodLikeVO();
		vo.setFno(Integer.parseInt(fno));
		vo.setId(id);
		FoodJJimLikeDAO dao = FoodJJimLikeDAO.newInstance();
		dao.foodLikeInsert(vo);
		
		return "redirect:../food/food_detail.do?fno="+fno;
	}
	

}
