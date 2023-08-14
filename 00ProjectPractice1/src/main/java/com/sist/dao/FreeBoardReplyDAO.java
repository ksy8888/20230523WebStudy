package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;

public class FreeBoardReplyDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FreeBoardReplyDAO dao;
	
	//싱글턴
	public static FreeBoardReplyDAO newInstance() {
		if(dao==null)
			dao = new FreeBoardReplyDAO();
		return dao;
	}
/*
    no NUMBER,
	bno NUMBER,
	id VARCHAR2(20),
	name VARCHAR2(51) CONSTRAINT pfr_name_nn NOT NULL,
	msg CLOB CONSTRAINT fr_msg_nn NOT NULL,
	filename VARCHAR2(400),
	filesize NUMBER,
	regdate DATE DEFAULT SYSDATE,
	group_id NUMBER,
	group_step NUMBER DEFAULT 0,
	group_tab NUMBER DEFAULT 0,
	root NUMBER DEFAULT 0,
	depth NUMBER DEFAULT 0,	
 */
	//목록출력 //bno:게시물번호, no:댓글고유번호
	public List<FreeBoardReplyVO> replyListData(int bno) {
		List<FreeBoardReplyVO> list = new ArrayList<FreeBoardReplyVO>();
		try {
			conn = db.getConnection();
			String sql = "SELECT no,bno,id,name,msg,TO_CHAR(regdate,'yyyy-MM-dd HH24:MI:SS'),filename,filesize,group_tab "
					+ "FROM freeboard_reply "
					+ "WHERE bno=? "
					+ "ORDER BY group_id DESC, group_step ASC";
			//group_id DESC 최신순, group_step ASC답변순
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FreeBoardReplyVO vo = new FreeBoardReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setBno(rs.getInt(2));
				vo.setId(rs.getString(3)); //세션
				vo.setName(rs.getString(4)); //세션
				vo.setMsg(rs.getString(5));
				vo.setDbday(rs.getString(6));
				vo.setFilename(rs.getString(7));
				vo.setFilesize(rs.getInt(8));
				vo.setGroup_tab(rs.getInt(9));
				
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
	
	public void replyInsert(FreeBoardReplyVO vo) {
		try {
			conn = db.getConnection();
			String sql = "INSERT INTO freeboard_reply(no,bno,id,name,msg,filename,filesize,group_id) "
					+ "VALUES(fr_no_seq.nextval,?,?,?,?,?,?,"
					+ "(SELECT NVL(MAX(group_id)+1,1) FROM freeboard_reply))";
			        //처음댓글 올릴 때 하나씩 올려줌
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());  //session
			ps.setString(3, vo.getName()); //session
			ps.setString(4, vo.getMsg());
			ps.setString(5, vo.getFilename());
			ps.setInt(6, vo.getFilesize());
			
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	
	public void replyDelete(int no) {
		try {
			conn=db.getConnection();
			conn.setAutoCommit(false);
			
			String sql = "SELECT root,depth "
					+ "FROM freeboard_reply "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int root = rs.getInt(1);
			int depth = rs.getInt(2);
			rs.close();
			
			if(depth==0) {	//댓글이없다면
			sql ="DELETE FROM freeboard_reply WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			}
			else {
				String msg="관리자가 삭제한 대댓글입니다.";
				sql = "UPDATE freeboard_reply "
						+ "SET msg=? "
						+ "WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, msg);
				ps.setInt(2, no);
				ps.executeUpdate();
			}
			//depth감소
			sql = "UPDATE freeboard_reply "
					+ "SET depth=depth-1 "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root); //자신이 아니라 상위것에서 감소해야함
			ps.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {}
			e.printStackTrace();
		} finally {		
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
				db.disConnection(conn, ps);
			}	
		}
	}
	
	public void replyUpdate(int no, String msg) {
		try {
			conn = db.getConnection();
			String sql ="UPDATE freeboard_reply "
					+  "SET msg=? "
					+  "WHERE no=?";
			ps =conn.prepareStatement(sql);
			
			ps.setString(1, msg);
			ps.setInt(2, no);
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	
	//대댓글
	/*
GROUP_ID      답변끼리 모아주는 역할
GROUP_STEP    답변출력 순서
GROUP_TAB     어느 댓글에 대해 표시 _누구에대한댓글
-------------------------------------------- 댓글
ROOT          상위 댓글번호 _삭제할때
DEPTH         댓글 갯수	 
	 */
	public void replyReplyInsert(int pno, FreeBoardReplyVO vo) {
		try {
			conn= db.getConnection();
			//autocommit해제
			conn.setAutoCommit(false);
			
			//select 댓글번호no에 대한 관련 정보 select 
			String sql = "SELECT group_id,  group_step, group_tab "
					+ "FROM freeboard_reply "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi = rs.getInt(1);
			int gs = rs.getInt(2);
			int gt = rs.getInt(3);
			rs.close();
			
			//group_step+1	=> update //group_step은 답변 출력순서
			sql="UPDATE freeboard_reply SET "
					+ "group_step=group_step+1 "
					+ "WHERE group_id=? AND group_step>?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			
			////insert
			sql="INSERT INTO freeboard_reply VALUES("
					+ "fr_no_seq.nextval,?,?,?,?,?,?,SYSDATE,?,?,?,?,0)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getName());
			ps.setString(4, vo.getMsg());
			ps.setString(5, vo.getFilename());
			ps.setInt(6, vo.getFilesize());
			ps.setInt(7, gi);
			ps.setInt(8, gs+1);
			ps.setInt(9, gt+1);
			ps.setInt(10, pno);
			
			ps.executeUpdate();
			
			//depth(댓글갯수)  => update 
			sql = "UPDATE freeboard_reply SET "
					+ "depth=depth+1 "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, pno);
			ps.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {}
			e.printStackTrace();
		} finally {
			//conn의 원래 기능으로 설정
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
				db.disConnection(conn, ps);
			}
		}
		
	}
	
	public int replyCount(int bno) {
		int count=0;
		try {
			conn = db.getConnection();
			String sql="SELECT COUNT(*) FROM freeboard_reply WHERE bno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, bno);
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