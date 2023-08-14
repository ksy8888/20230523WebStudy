package com.sist.vo;

import java.util.Date;

public class ReserveShareVO {
	private int sno,skdno;
	private String sid,srday,srtime,sinwon,srok;
	private Date sregdate;
	private String sposter, stitle, sprice, sdbday; //임시변수   //subquery or function
	
	
	public String getSprice() {
		return sprice;
	}
	public void setSprice(String sprice) {
		this.sprice = sprice;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getSkdno() {
		return skdno;
	}
	public void setSkdno(int skdno) {
		this.skdno = skdno;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSrday() {
		return srday;
	}
	public void setSrday(String srday) {
		this.srday = srday;
	}
	public String getSrtime() {
		return srtime;
	}
	public void setSrtime(String srtime) {
		this.srtime = srtime;
	}
	public String getSinwon() {
		return sinwon;
	}
	public void setSinwon(String sinwon) {
		this.sinwon = sinwon;
	}
	public String getSrok() {
		return srok;
	}
	public void setSrok(String srok) {
		this.srok = srok;
	}
	public Date getSregdate() {
		return sregdate;
	}
	public void setSregdate(Date sregdate) {
		this.sregdate = sregdate;
	}
	public String getSposter() {
		return sposter;
	}
	public void setSposter(String sposter) {
		this.sposter = sposter;
	}
	public String getStitle() {
		return stitle;
	}
	public void setStitle(String stitle) {
		this.stitle = stitle;
	}
	public String getSdbday() {
		return sdbday;
	}
	public void setSdbday(String sdbday) {
		this.sdbday = sdbday;
	}
	
	
}