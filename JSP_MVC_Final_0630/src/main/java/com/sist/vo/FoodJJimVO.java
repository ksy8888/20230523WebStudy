package com.sist.vo;
/*
 	NO NUMBER,
	id VARCHAR2(20),  --누가찜
	fno NUMBER, --어떤거찜	
	
	
	찜 좋아요는 같은 모델에서 처리 => 
 */
public class FoodJJimVO {
	private int no,fno;
	private String id;
	private String name,poster,tel;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
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
