package com.sist.model;
import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.common.CommonModel;
import com.sist.controller.RequestMapping;
import com.sist.vo.*;
import com.sist.dao.*;

import java.util.*;

public class MainModel {
	
	@RequestMapping("main/main.do")
	public String main_page(HttpServletRequest request, HttpServletResponse response) {
		
		//home.jsp로 => category 전송
		FoodDAO dao  = FoodDAO.newInstance();
		List<CategoryVO> list = dao.foodCategoryListData();
		request.setAttribute("list", list);
		request.setAttribute("main_jsp", "../main/home.jsp");	//main.jsp 15줄로 들어가 실행
		
		//Footer고정
		CommonModel.commonRequestData(request);
		
		//쿠키읽기
		Cookie[] cookies = request.getCookies();
		List<FoodVO> clist = new ArrayList<FoodVO>();
		if(cookies != null) {
			for(int i=cookies.length-1; i>=0; i--) {
				if(cookies[i].getName().startsWith("food_")) {
					String fno = cookies[i].getValue();
					FoodVO vo = dao.foodDetailData(Integer.parseInt(fno));
					clist.add(vo);
				}
			}
		}
		request.setAttribute("clist", clist); //clist에 담아서 home.jsp
		
		return "../main/main.jsp";
	}
}
