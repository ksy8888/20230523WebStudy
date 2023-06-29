package com.sist.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
public class FoodListModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) {
		
		FoodDAO dao = FoodDAO.newInstance();
		String cno = request.getParameter("cno");
		CategoryVO cvo = dao.foodCategoryInfoData(Integer.parseInt(cno));
		List<FoodVO> list = dao.foodCategoryListData(Integer.parseInt(cno));
		
		request.setAttribute("cvo", cvo);
		request.setAttribute("list", list);	//cvo,list정보담아서
		return "food/food_list.jsp";		//food_list.jsp에게 보냄
	}

}
