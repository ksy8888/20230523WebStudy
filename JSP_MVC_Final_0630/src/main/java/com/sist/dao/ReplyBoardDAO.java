package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;

public class ReplyBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static ReplyBoardDAO dao;
	
	public static ReplyBoardDAO newInstance() {
		if(dao==null)
			dao = new ReplyBoardDAO();
		return dao;
	}

/*
	 try {
			conn = db.getConnection();
			String sql=""
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
*/
	
	//사용자 측
	//목록보기
	public List<ReplyBoardVO> replyBoardListData(int page) {
		List<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		
		try {
			conn = db.getConnection();
			String sql = "SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,group_tab,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab "
					+ "FROM project_replyBoard ORDER BY group_id DESC,group_step ASC)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			int rowSize = 10;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
				list.add(vo);
				//System.out.println(vo.getGroup_tab());
			}
			
			rs.close();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	//총페이지
	public int replyBoardTotalPage() {
		int total=0;
		
		try {
			conn = db.getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10.0) FROM project_replyBoard";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
		return total;
	}
	//묻기 (새글) //pr_no_seq
	/*
	NO         NOT NULL NUMBER         
ID                  VARCHAR2(20)   
NAME       NOT NULL VARCHAR2(51)   
SUBJECT    NOT NULL VARCHAR2(1000) 
CONTENT    NOT NULL CLOB           

REGDATE             DATE       DEFAULT SYSDATE       
HIT                 NUMBER     DEFAULT 0     
GROUP_ID            NUMBER         
GROUP_STEP          NUMBER     DEFAULT 0    
GROUP_TAB           NUMBER     DEFAULT 0    
ISREPLY             NUMBER     DEFAULT 0
	  */
	public void replyBoardInsert(ReplyBoardVO vo) {
		try {
			conn = db.getConnection();
			String sql="INSERT INTO project_replyBoard(no,id,name,subject,content,group_id) "
					+ "VALUES(pr_no_seq.nextval,?,?,?,?,"
					+ "(SELECT NVL(MAX(group_id)+1,1) FROM project_replyBoard))"; //맨처음 groupid를 1번으로
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getSubject());
			ps.setString(4, vo.getContent());
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	
	//삭제 
	public void replyBoardDelete(int no) {
		try {
			conn = db.getConnection();
			String sql="SELECT group_id, isreply "
					+ "FROM project_replyBoard "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi = rs.getInt(1);
			int isreply = rs.getInt(2);
			rs.close();
			
			if(isreply == 1) {	//답변이 있으면 그룹지움 => 답변도 지워버림
				sql = "DELETE FROM project_replyBoard WHERE group_id=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, gi);
				ps.executeUpdate();
			}
			else {
				sql = "DELETE FROM project_replyBoard WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	//내용보기
	public ReplyBoardVO replyBoardDetailData(int no) {
		
		ReplyBoardVO vo = new ReplyBoardVO();
		
		try {
			conn = db.getConnection();
			String sql="UPDATE project_replyBoard SET hit = hit+1 "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			sql = "SELECT no,name,id,subject,content,TO_CHAR(regdate,'YYYY-MM-DD'),hit "
					+ "FROM project_replyBoard "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setId(rs.getString(3));
			vo.setSubject(rs.getString(4));
			vo.setContent(rs.getString(5));
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
	//수정 데이터
	public ReplyBoardVO replyBoardUpdateData(int no) {
		
		ReplyBoardVO vo = new ReplyBoardVO();
		
		try {
			conn = db.getConnection();
			
			String sql = "SELECT no,name,id,subject,content,TO_CHAR(regdate,'YYYY-MM-DD'),hit "
					+ "FROM project_replyBoard "
					+ "WHERE no=?";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setId(rs.getString(3));
			vo.setSubject(rs.getString(4));
			vo.setContent(rs.getString(5));
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
	//실제수정
	public void replyBoardUpdate(ReplyBoardVO vo) {
		 try {
				conn = db.getConnection();
				String sql="UPDATE project_replyBoard "
						+ "SET subject=?,content=? "
						+ "WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, vo.getSubject());
				ps.setString(2, vo.getContent());
				ps.setInt(3, vo.getNo());
				
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
	}
	// 관리자 측
	//목록
	public List<ReplyBoardVO> replyBoardAdminListData(int page) {
		List<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		
		try {
			conn = db.getConnection();
			String sql="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,isreply,group_step,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,isreply,group_step,rownum as num "
					+ "FROM (SELECT no,subject,name,regdate,hit,isreply,group_step "
					+ "FROM project_replyBoard ORDER BY group_id DESC)) "
					+ "WHERE num BETWEEN ? AND ? "
					+ "AND group_step = 0"; //답변 안갖고옴
			ps = conn.prepareStatement(sql);
			int rowSize=10;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setIsreply(rs.getInt(6));
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
	//총페이지
	public int replyBoardAdminTotalPage() {
		int total=0;
		
		try {
			conn = db.getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10.0) FROM project_replyBoard "
					+ "WHERE group_step=0"; //질문만:0		//답변1 >> 안가져옴
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
		return total;
	}
	//답변 (새글)
	public void replyBoardAdminInsert(int pno, ReplyBoardVO vo) {
		
		try {
			conn = db.getConnection();
			conn.setAutoCommit(false); //트잭달기
			
			String sql="SELECT group_id FROM project_replyboard "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi=rs.getInt(1);
			rs.close();
			
			sql= "INSERT INTO project_replyBoard(no,id,name,subject,content,group_id,group_step,group_tab) "
					+ "VALUES(pr_no_seq.nextval, ?,?,?,?,?,1,1)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getSubject());
			ps.setString(4, vo.getContent());
			ps.setInt(5, gi);
			ps.executeUpdate();
			
			sql="UPDATE project_replyBoard SET isreply=1 "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ps.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch(Exception e1) {}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch(Exception e1) {}
			db.disConnection(conn, ps);
		}
	}
	//삭제
	//수정
}
