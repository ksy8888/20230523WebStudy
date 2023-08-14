package com.sist.vo;
/*
 * no NUMBER,
	category_name VARCHAR(30) CONSTRAINT bc_category_nn NOT NULL,
 */
public class FreeBoardCategoryVO {
	private int no;
	private String cname;
	
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	
}
