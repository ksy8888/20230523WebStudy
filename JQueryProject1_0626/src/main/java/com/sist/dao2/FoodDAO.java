package com.sist.dao2;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private static FoodDAO dao;
	
	//미리 생성된 객체 읽기 => Connection 관련 (DBCP) => 연결에 소모되는 시간을 절역
	//DBCP는 웹에서만 사용이 가능 => 자바애플리케이션에서는 사용 불가능
	public void getConnection() {
		try {
			Context init=new InitialContext();
			Context c = (Context)init.lookup("java://comp/env");
			DataSource ds = (DataSource)c.lookup("jdbc/oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//반환
	public void disConnection() {
		try {
			if(ps!=null) ps.close();
			if(conn!=null) conn.close();
		} catch (Exception e) {}
	}
	//싱글턴
	public static FoodDAO newInstance() {
		if(dao==null)
			dao = new FoodDAO();
		return dao;
	}
	//기능
	public List<FoodBean> foodAllData() {
		List<FoodBean> list = new ArrayList<FoodBean>();
		try {
			getConnection();
			String sql ="SELECT poster,name,phone FROM food_house";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FoodBean vo = new FoodBean();
				vo.setName(rs.getString(2));
				vo.setTel(rs.getString(3));
				String poster = rs.getString(1);
				poster = poster.substring(0, poster.indexOf("^"));
				poster = poster.replace("#", "&");
				vo.setPoster(poster);
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return list;
	}
}
