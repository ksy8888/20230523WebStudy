package com.sist.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.controller.RequestMapping;
import com.sist.vo.*;
import com.sist.dao.ReserveDAO;

public class ReserveModel {
	@RequestMapping("food/diary.do")
	public String diaryData(HttpServletRequest request, HttpServletResponse response) {
		String fdno=request.getParameter("fdno");
		String strYear=request.getParameter("year");
		String strMonth=request.getParameter("month");
		
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d");
		String today=sdf.format(date);
		StringTokenizer st=new StringTokenizer(today,"-");
		String sy=st.nextToken();
		String sm=st.nextToken();
		String sd=st.nextToken();
		
		if(strYear==null)
			strYear=sy;
		
		if(strMonth==null)
			strMonth=sm;
		
		int year=Integer.parseInt(strYear);
		int month=Integer.parseInt(strMonth);
		int day=Integer.parseInt(sd);
		
		//요일
		String[] strWeek={"일","월","화","수","목","금","토"};
		
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE,1); //1 일차
		
		int week=cal.get(Calendar.DAY_OF_WEEK); // 요일 구하기
		int lastday=cal.getActualMaximum(Calendar.DATE); // 각달의 마지막 일
		
		week=week-1;
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("week", week);
		request.setAttribute("lastday", lastday);
		request.setAttribute("strWeek", strWeek);
		
		// 오라클 데이터 읽기
		int[] rday=new int[32];
		ReserveDAO dao=ReserveDAO.newInstance();
		String r=dao.foodReserveDay(Integer.parseInt(fdno));
		st=new StringTokenizer(r,",");
		while(st.hasMoreTokens()) {
			int a=Integer.parseInt(st.nextToken());
			if(a>=day) {
				rday[a]=1; //1은 예약가능 날짜 0은 불가능한 날짜
			}
		}
		request.setAttribute("rday", rday);
		return "../food/diary.jsp";
	}
	@RequestMapping("food/reserve_main.do")
	public String reserve_main(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../food/reserve_main.jsp");
		return "../jsp/main.jsp";
	}
	@RequestMapping("food/inwon.do")
	public String reserve_inwon(HttpServletRequest request, HttpServletResponse response) {
		
		return "../food/inwon.jsp";
	}
	@RequestMapping("food/time.do")
	public String reserve_time(HttpServletRequest request, HttpServletResponse response) {
		String day=request.getParameter("day");
		ReserveDAO dao=ReserveDAO.newInstance();
		String times=dao.reserve_day_time(Integer.parseInt(day));
		StringTokenizer st=new StringTokenizer(times,",");
		List<String> list=new ArrayList<String>();
		while(st.hasMoreTokens()) {
			String time=dao.reserve_get_time(Integer.parseInt(st.nextToken()));
			list.add(time);
		}
		
		request.setAttribute("list", list);
		return "../food/time.jsp";
	}
	@RequestMapping("food/reserve_ok.do")
	public String reserve_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String fdno=request.getParameter("fdno");
		String frday=request.getParameter("frday");
		String frtime=request.getParameter("frtime");
		String finwon=request.getParameter("finwon");
		
		//DAO 연결
		System.out.println("fdno="+fdno);
		System.out.println("frday="+frday);
		System.out.println("frtime="+frtime);
		System.out.println("finwon="+finwon);
		
		HttpSession session=request.getSession();
		String fid=(String)session.getAttribute("id");
		
		System.out.println("fid="+fid);
		
		ReserveFoodVO vo=new ReserveFoodVO();
		vo.setFdno(Integer.parseInt(fdno));
		vo.setFid(fid);
		vo.setFrday(frday);
		vo.setFrtime(frtime);
		vo.setFinwon(finwon);
		
		ReserveDAO dao=ReserveDAO.newInstance();
		dao.reserve_ok(vo);
		return "redirect:../my/mypage_reserve.do";
	}
	@RequestMapping("food/reserve_info.do")
	public String reserve_info(HttpServletRequest request, HttpServletResponse response) {
		String fno=request.getParameter("fno"); //예약정보
		String fdno=request.getParameter("fdno"); //맛집정보 읽기
		// DataBase 
		ReserveDAO dao=ReserveDAO.newInstance();
		ReserveFoodVO rfvo=dao.reserveInfoData(Integer.parseInt(fno));
		FoodVO fvo=dao.reserveFoodInfoData(Integer.parseInt(fdno));
		request.setAttribute("fvo", fvo);
		request.setAttribute("rfvo", rfvo);
		return "../food/reserve_info.jsp";
	}
	
	
	//주방
	@RequestMapping("share/diary.do")
	public String sdiaryData(HttpServletRequest request, HttpServletResponse response) {
		String skdno=request.getParameter("skdno");
		String strYear=request.getParameter("year");
		String strMonth=request.getParameter("month");
		
		Date date = new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-M-d");
		String today=sdf.format(date);
		StringTokenizer st=new StringTokenizer(today,"-");
		String sy=st.nextToken();
		String sm=st.nextToken();
		String sd=st.nextToken();
		
		if(strYear==null)
			strYear=sy;
		
		if(strMonth==null)
			strMonth=sm;
		
		int year=Integer.parseInt(strYear);
		int month=Integer.parseInt(strMonth);
		int day=Integer.parseInt(sd);
		
		//요일
		String[] strWeek={"일","월","화","수","목","금","토"};
		
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE,1); //1 일차
		
		int week=cal.get(Calendar.DAY_OF_WEEK); // 요일 구하기
		int lastday=cal.getActualMaximum(Calendar.DATE); // 각달의 마지막 일
		
		week=week-1;
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("day", day);
		request.setAttribute("week", week);
		request.setAttribute("lastday", lastday);
		request.setAttribute("strWeek", strWeek);
		
		// 오라클 데이터 읽기
		int[] rday=new int[32];
		ReserveDAO dao=ReserveDAO.newInstance();
		String r=dao.shareReserveDay(Integer.parseInt(skdno));
		st=new StringTokenizer(r,",");
		while(st.hasMoreTokens()) {
			int a=Integer.parseInt(st.nextToken());
			if(a>=day) {
				rday[a]=1; //1은 예약가능 날짜 0은 불가능한 날짜
			}
		}
		request.setAttribute("rday", rday);
		return "../share/diary.jsp";
	}
	@RequestMapping("share/reserve_main.do")
	public String sreserve_main(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("main_jsp", "../share/reserve_main.jsp");
		return "../jsp/main.jsp";
	}
	@RequestMapping("share/inwon.do")
	public String sreserve_inwon(HttpServletRequest request, HttpServletResponse response) {
		
		return "../share/inwon.jsp";
	}
	@RequestMapping("share/time.do")
	public String sreserve_time(HttpServletRequest request, HttpServletResponse response) {
		String day=request.getParameter("day");
		ReserveDAO dao=ReserveDAO.newInstance();
		String times=dao.reserve_day_time(Integer.parseInt(day));
		StringTokenizer st=new StringTokenizer(times,",");
		List<String> list=new ArrayList<String>();
		while(st.hasMoreTokens()) {
			String time=dao.reserve_get_time(Integer.parseInt(st.nextToken()));
			list.add(time);
		}
		
		request.setAttribute("list", list);
		return "../share/time.jsp";
	}
	@RequestMapping("share/reserve_ok.do")
	public String sreserve_ok(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {}
		String skdno=request.getParameter("skdno");
		String srday=request.getParameter("srday");
		String srtime=request.getParameter("srtime");
		String sinwon=request.getParameter("sinwon");
		
		//DAO 연결
		System.out.println("skdno="+skdno);
		System.out.println("srday="+srday);
		System.out.println("srtime="+srtime);
		System.out.println("sinwon="+sinwon);
		
		HttpSession session=request.getSession();
		String sid=(String)session.getAttribute("id");
		
		System.out.println("sid="+sid);
		
		ReserveShareVO vo=new ReserveShareVO();
		vo.setSkdno(Integer.parseInt(skdno));
		vo.setSid(sid);
		vo.setSrday(srday);
		vo.setSrtime(srtime);
		vo.setSinwon(sinwon);
		
		ReserveDAO dao=ReserveDAO.newInstance();
		dao.sreserve_ok(vo);
		return "redirect:../my/mypage_reserve1.do";
	}
	@RequestMapping("share/reserve_info.do")
	public String sreserve_info(HttpServletRequest request, HttpServletResponse response) {
		String sno=request.getParameter("sno"); //예약정보
		String skdno=request.getParameter("skdno"); //맛집정보 읽기
		// DataBase 
		ReserveDAO dao=ReserveDAO.newInstance();
		ReserveShareVO rsvo=dao.sreserveInfoData(Integer.parseInt(sno));
		ShareVO svo=dao.reserveShareInfoData(Integer.parseInt(skdno));
		request.setAttribute("svo", svo);
		request.setAttribute("rsvo", rsvo);
		return "../share/reserve_info.jsp";
	}
}
