package com.sist.vo;
/*
 	NO NUMBER,
	id VARCHAR2(20),  --누가 좋아요
	fno NUMBER, --어떤거 좋아요
 */
public class FoodLikeVO {
	private int no,fno;
	private String id;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
