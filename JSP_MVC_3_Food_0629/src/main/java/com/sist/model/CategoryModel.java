package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;

//		   request	 request
//Controller  =>  Model  =>  JSP
public class CategoryModel implements Model {

	@Override					//Controller에서 받은 값을 request에 담음
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {	
		System.out.println("Controller2:" + request);
		//<jsp:useBean id="dao" class="com.sist.dao5.FoodDAO"/>
		/*
		 * <% //Category목록 List<CategoryVO> list = dao.foodCategoryData(); %>
		 */
		FoodDAO dao = FoodDAO.newInstance();
		List<CategoryVO> list = dao.foodCategoryData();
		//request : DispatcherServlet의 request  >> JSP에게 전송
		request.setAttribute("list", list);	//Dispatcher에게 받은 request를 list에 담아서
		return "food/category.jsp";			//category.jsp에게 보냄 >> controller가 보내줌
	}

}
