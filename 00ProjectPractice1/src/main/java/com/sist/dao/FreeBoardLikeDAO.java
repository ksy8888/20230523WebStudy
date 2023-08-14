package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.PartialResultException;

import com.sist.common.CreateDataBase;
import com.sist.vo.FreeBoardLikeVO;

public class FreeBoardLikeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FreeBoardLikeDAO dao;
	
	public static FreeBoardLikeDAO newInstance() {
		if(dao == null)
			dao = new FreeBoardLikeDAO();
		return dao;
	}
	
	public void FreeBoardLikeInsert(FreeBoardLikeVO vo) {		
		try {
			conn = db.getConnection();
			String sql = "INSERT INTO freeboard_like VALUES(fl_no_seq.nextval,?,?)";
		
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getBno());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
		
	}
	
	//삭제
	public void FreeBoardLikeDelete(FreeBoardLikeVO vo) {		
		try {
			conn = db.getConnection();
			//DELETE FROM freeboard_like WHERE no=74;
			String sql = "DELETE FROM freeboard_like WHERE id=? AND bno=?";
		
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getBno());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
		
	}
	
	public int FreeBoardLikeOk(int bno,String id) {
		int count=0;
		try {
			conn = db.getConnection();	
			String sql = "SELECT COUNT(*) FROM freeboard_like WHERE bno=? AND id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ps.setString(2, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
}
