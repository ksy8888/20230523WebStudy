package com.sist.dao;
import java.util.*;
import java.sql.*;
/*
 * 장바구니 >> 재고수량 랜덤생성
 * 
 * */
public class GoodsDAO2 {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	public GoodsDAO2() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {}
	}
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {}
	}
	public void disConnection() {
		try {
			if(ps!=null)
				ps.close();
			if(conn!=null)
				conn.close();
		} catch (Exception e) {}
	}
	public List<Integer> goodsGetNoData(String tab) {
		List<Integer> list = new ArrayList<Integer>();
		try {
			getConnection();
			String sql = "SELECT no FROM "+tab+" ORDER BY no ASC";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int no = rs.getInt(1);
				list.add(no);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	public void goodsAccountInsert(int account,int no,String tab) {
		try {
			getConnection();
			String sql = "UPDATE "+tab+" SET"+" account=? "+"WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, account);
			ps.setInt(2, no);
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	public static void main(String[] args) {
		GoodsDAO2 dao = new GoodsDAO2();
		List<Integer> list = dao.goodsGetNoData("goods_special");
		for(int no:list) {
			int account = (int)(Math.random()*50)+11;	//account랜덤
			dao.goodsAccountInsert(account, no, "goods_special");	//best,new,all,special
			
		}
		System.out.println("완료");
	}
}
