package com.sist.model;
import java.lang.reflect.Method;
import java.util.Scanner;

import com.sist.controller.RequestMapping;

class Board {
	@RequestMapping("list.do")
	public void boardList() {
		System.out.println("목록 출력");
	}
	@RequestMapping("insert.do")
	public void boardInsert() {
		System.out.println("데이터 추가");
	}
	@RequestMapping("update.do")
	public void boardUpdate() {
		System.out.println("데이터 수정");
	}
	@RequestMapping("delete.do")
	public void boardDelete() {
		System.out.println("데이터 삭제");
	}
	@RequestMapping("find.do")
	public void boardFind() {
		System.out.println("데이터 찾기");
	}
}

public class MainClass_연습 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		System.out.println("URL");
		//list.dp => boardList
		String url = scan.next();
		
		//찾기
	/*	Board board = new  Board();
		if(url.equals("list.do")) {
			board.boardList();
		}
		if(url.equals("insert.do")) {
			board.boardInsert();
		}
		if(url.equals("update.do")) {
			board.boardUpdate();
		}
		if(url.equals("delete.do")) {
			board.boardDelete();
		}
		if(url.equals("find.do")) {
			board.boardFind();
		}
	*/
		try {
			Class clsName = Class.forName("com.sist.model.Board");	//클래스 정보 읽기
			//Board board = new  Board();
			Object obj = clsName.getDeclaredConstructor().newInstance(); //메모리 할당
			
			
			//class에 정의된 모든 메소드를 읽는다
			Method[] methods = clsName.getDeclaredMethods();
			
			for(Method m : methods) {
				//메소드위에 어노테이션 확인
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				if(rm.value().equals(url)) {
					//m(method)호출
					m.invoke(obj, null);
					
					//리턴형이 있는경우
					//String s = (String)m.invoke(obj, null);
				}
			}
		} catch (Exception e) {}
	}

}
