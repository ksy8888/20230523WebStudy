package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
public class FoodDetailModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		FoodDAO dao = FoodDAO.newInstance();
		String fno = request.getParameter("fno");
		FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
		
		
		String address = vo.getAddress();
		String addr1 = address.substring(0, address.lastIndexOf("지"));	/*서울특별시 강남구 도산대로78길 25 에스앤에스빌딩 1F*/
		String addr2 = address.substring(address.lastIndexOf("지")+3);	/*서울시 강남구 청담동 11-12 에스앤에스빌딩 1F*/
		/*
		서울특별시 강남구 도산대로78길 25 에스앤에스빌딩 1F 지번 서울시 강남구 청담동 11-12 에스앤에스빌딩 1F
		*/
		String temp = addr1.trim().substring(addr1.indexOf(" ")+1);	/*강남구 도산대로78길 25*/
		String addr3 = temp.substring(0, temp.indexOf(" "));	/* 강남구 */
		
		
		request.setAttribute("vo", vo);
		request.setAttribute("addr1", addr1);
		request.setAttribute("addr2", addr2);
		request.setAttribute("addr3", addr3);
		return "food/food_detail.jsp";
	}

}
