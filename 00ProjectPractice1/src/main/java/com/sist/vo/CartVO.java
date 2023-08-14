package com.sist.vo;

/*
							 cart_no NUMBER,
                             pdsno NUMBER,
                             amount NUMBER,
                             price NUMBER,
                             id VARCHAR2(20),
                             ischeck NUMBER DEFAULT 0,
                             issale NUMBER DEFAULT 0,
                             regdate DATE DEFAULT SYSDATE,
 */
//서브쿼리
import java.util.*;
public class CartVO {
	private int cart_no,pdsno,amount,price,ischeck,issale,type;
	private String id,title,poster,PRICED_SALE;
	private Date regdate;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPRICED_SALE() {
		return PRICED_SALE;
	}
	public void setPRICED_SALE(String pRICED_SALE) {
		PRICED_SALE = pRICED_SALE;
	}
	public int getCart_no() {
		return cart_no;
	}
	public void setCart_no(int cart_no) {
		this.cart_no = cart_no;
	}
	public int getPdsno() {
		return pdsno;
	}
	public void setPdsno(int pdsno) {
		this.pdsno = pdsno;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getIscheck() {
		return ischeck;
	}
	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}
	public int getIssale() {
		return issale;
	}
	public void setIssale(int issale) {
		this.issale = issale;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
	
}