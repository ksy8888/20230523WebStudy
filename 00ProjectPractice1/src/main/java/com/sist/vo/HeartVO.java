package com.sist.vo;
/*
 * NO NUMBER,
	type NUMBER,
	id varchar2(20),
	cno NUMBER CONSTRAINT ht_cno_nn NOT NULL, ------------  fdno rdno skdno pdno
	CONSTRAINT ht_no_pk PRIMARY KEY(NO),
	CONSTRAINT ht_id_fk FOREIGN KEY(id)
	REFERENCES project_member(id),
	CONSTRAINT ht_type_ck CHECK(type IN(1,2,3,4))
 */
public class HeartVO {
  private int no,cno,type;
  private String id;
public int getNo() {
	return no;
}
public void setNo(int no) {
	this.no = no;
}
public int getCno() {
	return cno;
}
public void setCno(int cno) {
	this.cno = cno;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
  
}
