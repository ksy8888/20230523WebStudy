package com.sist.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * 	MVC 동작 과정
 * -----------
 * 1. 요청 (JSP) 	===> DispatcherServlet을 찾는다
 * 		list.do				|
 * 		insert.do			|
 * 						서버에서 받을 수 있는 부분 URI,URL
 * 						URI => Model을 찾는다
 * 	2. DispatcherServlet(Controller)
 * 		=> Front Controller : 요청 => Model 연결 => request를 전송
 * 									---------
 * 										요청 처리 기능을 가지고 있다	
 *  3. MVC 목적 : 보안
 *  			 자바와 HTML을 분리하는 이유
 *  			 ---
 *  			 확장성, 재사용, 변경이 쉽다
 *  4. 동작 순서
 *  				   .do						  request
 *  	JSP(링크,버튼)	---------> DispatcherServlet ---------> Model (DAO <=> 오라클)
 *  												↓		결과값을 request에 담아준다
 *  														request.setAttribute()
 *  	JSP(링크,버튼)	<-------- DispatcherServlet	 <----------
 *  	---			 request를 넘겨준다		       request를 넘겨준다
 		request.getAttribute() => ${}
 	5. DispatcherServlet은 최대한 고정
 	   --------------------------
 	6. 등록 (Model클래스) => XML로 세팅 (메뉴판)
 	7. 메소드 찾기 => 어노테이션 (메소드 자동호출이 가능)
 	--------------------------------------------------------
 */
import java.net.*;
import java.util.ArrayList;
import java.util.*;
@WebServlet("*.do")
public class DisPatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<String> clsList = new ArrayList<String>();
	
	//초기화 => XML에 등록된 클래스 읽기 (메뉴)
	public void init(ServletConfig config) throws ServletException {
		try {
			URL url = this.getClass().getClassLoader().getResource(".");	//현재폴더
			File file = new File(url.toURI());
			//System.out.println(file.getPath());
			//C:\webDev\webStudy\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\JSP_MVC_6_0630\WEB-INF\classes/JSP_MVC_6_0630/*.do
			String path = file.getPath();
			path = path.replace("\\", File.separator);
			// Window => \\ , Mac=> /
			path = path.substring(0,path.lastIndexOf(File.separator));
			System.out.println(path);
			path = path+File.separator+"application.xml";
			
			//XML 파싱
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			//파서기 (XML => DocumentBuilder , HTML => Jsoup)
			DocumentBuilder db = dbf.newDocumentBuilder();
			// 파서
			Document doc = db.parse(new File(path));
			// 필요한 데이터 읽기
			// root 태그 => beans   // Element(태그)
			Element beans = doc.getDocumentElement();
			System.out.println(beans.getTagName());
			
			//같은 태그를 묶어서 사용
			NodeList list = beans.getElementsByTagName("bean");
			for(int i=0; i<list.getLength(); i++) {
				//bean 태그를 1개씩 가지고 온다
				Element bean = (Element)list.item(i);
				String id = bean.getAttribute("id");	//bean안의 id태그 값
				String cls = bean.getAttribute("class"); //bean안의 class 태그 값
				System.out.println(id+":"+cls);
				clsList.add(cls);	//모델 클래스를 clsList에 등록
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//웹에서 사용자 요청 => servlet/jsp
	//servlet : 화면 출력은 하지 않는다 (연결)
	//화면 : jsp (View)
	/*
	 	Controller : Servlet
	 		Spring : DispatcherServlet
	 		Struts : ActionServlet
	 		Struts2 : FileDispathcher
	 					  ----------- 배달부 (request)
	 	Model : 요청 처리 => java
	 	View : 화면 출력 => jsp
	 					 ---- HTML
	 	
	 */
	//요청에 따라 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String path = request.getRequestURI();
			path = path.substring(request.getContextPath().length()+1);
			//http://localhost
			// /JSP_MVC_6_0630/food/category.do >> URI
			// ----------------ContextPath()
			// food/category.do     >> getContextPath().length()+1  ==> path
			for(String cls:clsList) {		//clsList >> 모델의 모든 클래스 >> bean(application.xml)			
				Class clsName = Class.forName(cls); // Class 정보 읽기				 
				Object obj = clsName.getDeclaredConstructor().newInstance(); //메모리 할당 new Board..				
				Method[] methods = clsName.getDeclaredMethods(); // 메소드 읽어 온다
				for(Method m : methods) {
					RequestMapping rm = m.getAnnotation(RequestMapping.class);	// @RequestMapping된 클래스 찾기
					if(rm.value().equals(path)) {
						String jsp = (String)m.invoke(obj, request,response);	//메소드 호출
						if(jsp==null) {	//리턴형이 void일때  (ajax 에서 사용)
							return;
						}
						else if (jsp.startsWith("redirect:")) {
							//sendRedirect
							response.sendRedirect(jsp.substring(jsp.indexOf(":")+1));
						} else {
							RequestDispatcher rd = request.getRequestDispatcher(jsp);
							rd.forward(request, response);
						}
						return;
					}
				}
			}
		} catch (Exception e) {}
	}

}
