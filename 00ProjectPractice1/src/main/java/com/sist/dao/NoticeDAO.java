package com.sist.dao;

import  java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;

public class NoticeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static NoticeDAO dao;
	
	public static NoticeDAO newInstance() {
		if(dao==null)
			dao = new NoticeDAO();
		return dao;
	}
	
	public List<NoticeVO> noticeListData(int page) {
		List<NoticeVO> list = new ArrayList<NoticeVO>();
		try {
			conn = db.getConnection();
			String sql = "SELECT no,subject,name,type,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit,num "
					+ "FROM (SELECT no,subject,name,type,regdate,hit,rownum as num "
					+ "FROM (SELECT /*+INDEX_DESC(notice notice_no_seq)*/no,subject,name,type,regdate,hit "
					+ "FROM notice ORDER BY no DESC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			int rowSize=10;
			int start = (rowSize*page)-(rowSize-1);
			int end= rowSize*page;
			
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				NoticeVO vo = new NoticeVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setType(rs.getInt(4));
				vo.setDbday(rs.getString(5));
				vo.setHit(rs.getInt(6));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	
	public int noticeTotalPage() {
		int total=0;
		try {
			conn=db.getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10.0) FROM notice";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total= rs.getInt(1);
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	
	//글작성 >> 관리자만'
	public void noticeInsert(NoticeVO vo) {
		try {
			conn = db.getConnection();
			String sql ="INSERT INTO notice VALUES("
					+ "notice_no_seq.nextval,?,?,?,?,?,SYSDATE,0)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getName());
			ps.setInt(3, vo.getType());
			ps.setString(4, vo.getSubject());
			ps.setString(5, vo.getContent());
			// ?갯수 안맞으면 IN,OUT 입출력 오류
			/*
			 * null : URL => server.xml
			 * 실행 => 오류가 없고 화면이 안뜬다 : 오라클에서 commit을 안날린거
			 */
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
	}
	//글수정 (관리자)
	public NoticeVO noticeUpdateData(int no) {
		
		NoticeVO vo = new NoticeVO();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT no,type,subject,content "
					+ "FROM notice "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setType(rs.getInt(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	//이게 수정
		public void noticeUpdate(NoticeVO vo) {
			try {
				conn = db.getConnection();
				String sql = "UPDATE notice "
						+ "SET type=?,subject=?,content=? "
						+ "WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, vo.getType());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
		}
		//삭제
		//삭제
		public void noticeDelete(int no) {
			try {
				conn = db.getConnection();
				String sql = "DELETE FROM notice WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
		}
	
	//공지 상세보기
	public NoticeVO noticeDetailData(int no) {
		NoticeVO vo = new NoticeVO();
		try {
			conn=db.getConnection();
			String sql="UPDATE notice SET hit=hit+1 "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			sql="SELECT no,name,subject,content,type,TO_CHAR(regdate,'YYYY-MM-DD HH24:MI:SS'),hit "
					+ "FROM notice "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setType(rs.getInt(5));
			vo.setDbday(rs.getString(6));
			vo.setHit(rs.getInt(7));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
	//
}	

