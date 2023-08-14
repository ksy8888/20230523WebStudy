package com.sist.vo;

import java.util.*;
public class ReserveFoodVO {
	private int fno,fdno;
	private String fid,frday,frtime,finwon,frok;
	private Date fregdate;
	private String fposter, ftitle, ftel, fdbday; //임시변수   //subquery or function
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public int getFdno() {
		return fdno;
	}
	public void setFdno(int fdno) {
		this.fdno = fdno;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getFrday() {
		return frday;
	}
	public void setFrday(String frday) {
		this.frday = frday;
	}
	public String getFrtime() {
		return frtime;
	}
	public void setFrtime(String frtime) {
		this.frtime = frtime;
	}
	public String getFinwon() {
		return finwon;
	}
	public void setFinwon(String finwon) {
		this.finwon = finwon;
	}
	public String getFrok() {
		return frok;
	}
	public void setFrok(String frok) {
		this.frok = frok;
	}
	public Date getFregdate() {
		return fregdate;
	}
	public void setFregdate(Date fregdate) {
		this.fregdate = fregdate;
	}
	public String getFposter() {
		return fposter;
	}
	public void setFposter(String fposter) {
		this.fposter = fposter;
	}
	public String getFtitle() {
		return ftitle;
	}
	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}
	public String getFtel() {
		return ftel;
	}
	public void setFtel(String ftel) {
		this.ftel = ftel;
	}
	public String getFdbday() {
		return fdbday;
	}
	public void setFdbday(String fdbday) {
		this.fdbday = fdbday;
	}
	
	
}
