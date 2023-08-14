package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.common.CreateDataBase;
import com.sist.vo.*;
import com.sist.dao.*;

public class HeartDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static HeartDAO dao;
	
	public static HeartDAO newInstance() {
		if(dao==null)dao= new HeartDAO();
		return dao;
	}
 public void HeartInsert(HeartVO vo)
 {
	 try
	 {
		 conn=db.getConnection();
		 String sql="INSERT INTO HEART VALUES(ht_no_seq.nextval,?,?,?)"; //type id cno
		 ps=conn.prepareStatement(sql);
		 ps.setInt(1, vo.getType());
		 ps.setString(2, vo.getId());
		 ps.setInt(3, vo.getCno());
		 ps.executeUpdate();
	 }catch(Exception ex)
	 {
		 ex.printStackTrace();
	 }
	 finally
	 {
		 db.disConnection(conn, ps);
	 }
 }
 public int HeartCountTotal(int type, int cno)
 {
	 int total=0;
	 try
	 {
		 conn=db.getConnection();
		 String sql="SELECT COUNT(*) FROM heart WHERE type=? and cno=?";
		 ps=conn.prepareStatement(sql);
		 ps.setInt(1, type);
		 ps.setInt(2, cno);
		 ResultSet rs=ps.executeQuery();
		 rs.next();
		 total=rs.getInt(1);
		 rs.close();
	 }catch(Exception ex)
	 {
		 ex.printStackTrace();
	 }
	 finally
	 {
		 db.disConnection(conn, ps);
	 }
	 return total;
 }
 public int heartNo(String id, int cno,int type) {
		int no=0;
		try {
			conn=db.getConnection();
			String sql ="select no "
					+ "from heart "
					+ "where cno=? "
					+ "and id=? "
					+ "and type=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ps.setString(2, id);
			ps.setInt(3, type);
			ResultSet rs = ps.executeQuery();
			rs.next();
			no = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
//			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return no;
	}
 public void heartCancel(int no) {
		try {
			conn = db.getConnection();
			String sql ="delete from heart "
					+ "where no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
}
