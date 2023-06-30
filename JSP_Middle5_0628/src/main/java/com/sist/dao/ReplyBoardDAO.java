package com.sist.dao;
//DBCP
import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class ReplyBoardDAO {
	//연결 객체
	private Connection conn; 
	//송수신 , SQL 전송=결과값 받기
	private PreparedStatement ps;
	//싱글턴
	private static ReplyBoardDAO dao;
	
	public static ReplyBoardDAO newInstance() {
		if(dao==null)
			dao = new ReplyBoardDAO();
		return dao;
	}
	
	//주소값을 얻기
	public void getConnection() {
		try {
			Context init = new InitialContext();
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
	
	//기능수행
	// 1. 목록 출력
	public List<ReplyBoardVO> boardListData(int page) {
		List<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		try {
			getConnection();
			//페이징 >> 인라인뷰 사용
			String sql ="SELECT no,subject,name,TO_CHAR(regdate,'YYYY-MM-DD'),hit,group_tab,num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab,rownum as num "
					+ "FROM (SELECT no,subject,name,regdate,hit,group_tab "
					+ "FROM replyBoard ORDER BY group_id DESC, group_step ASC)) "	//같은 답변(group_id)끼리 모으고 순서대로 출력(group_step ASC)
					+ "WHERE num BETWEEN ? AND ?";
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
				vo.setGroup_tab(rs.getInt(6));
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
	// 1.1 총ㅠㅔ이지
	public int boardRowCount() {
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) FROM replyBoard";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);	/////
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return count;
	}
	// 2. 상세보기
	public ReplyBoardVO boardDetailData(int no) {
		
		ReplyBoardVO vo = new ReplyBoardVO();
		try {
			getConnection();
/////////////////////////////////////////////////////// 조회수 증가
			String sql = "UPDATE replyBoard SET hit=hit+1 "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
		
/////////////////////////////////////////////////////// 상세보기
			sql = "SELECT no,name,subject,content,regdate,hit "
					+ "FROM replyBoard "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
					
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
		
	}
	
	// 3. 추가_글쓰기
	public void boardInsert(ReplyBoardVO vo) {
		try {
			getConnection();
			String sql="INSERT INTO replyBoard(no,name,subject,content,pwd,group_id) "
					+ "VALUES(rb_no_seq.nextval, ?,?,?,?,"
					+ "(SELECT NVL(MAX(group_id)+1,1) FROM replyBoard))";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			//실행
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	// 4. 수정
public ReplyBoardVO boardUpdateData(int no) {
		
		ReplyBoardVO vo = new ReplyBoardVO();
		try {
			getConnection();

			 String sql = "SELECT no,name,subject,content,regdate,hit "
					+ "FROM replyBoard "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
					
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return vo;
		
	}
	//4-1. 수정 _비밀번호체크
	public boolean boardUpdate(ReplyBoardVO vo) {
		
		boolean bCheck=false;
		try {
			getConnection();
			String sql="SELECT pwd FROM replyBoard "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd())) {
				bCheck = true;
				//수정
				sql="UPDATE replyBoard SET "
						+ "name=?,subject=?,content=? "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				ps.executeUpdate();
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return bCheck;
	}
	// 5. 삭제
	public boolean boardDelete(int no, String pwd) {
		boolean bCheck=false;
		try {
			getConnection();
			//비밀번호 처리
			String sql = "SELECT pwd FROM replyBoard "
					+ "WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(pwd)) {	//삭제
				bCheck=true;
				//2. 삭제 가능 여부 확인
				sql="SELECT root,depth FROM replyBoard "
						+ "WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				rs=ps.executeQuery();	//값가져옴
				rs.next();
				int root = rs.getInt(1);
				int depth = rs.getInt(2);
				rs.close();
				
				if(depth==0) {	//depth => 답변
					sql="DELETE FROM replyBoard "
							+ "WHERE no=?";
					ps =conn.prepareStatement(sql);
					ps.setInt(1, no);
					ps.executeUpdate();
				
				} else {
					String msg="관리자가 삭제한 게시물입니다";
					sql = "UPDATE replyBoard SET "
							+ "subject=?,content=? "
							+ "WHERE no=?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, msg);
					ps.setString(2, msg);
					ps.setInt(3, no);
					ps.executeUpdate();	
				}
				
				//비밀번호 맞을 때
				sql = "UPDATE replyBoard SET "
						+ "depth= depth-1 "
						+ "WHERE no=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, root);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return bCheck;
	}
	// 6. 답변
	public void replyInsert(int root,ReplyBoardVO vo) {
		
		//상위 게시물의 정보 => group_id, group_step, group_tab
		//group_step을 증가
		//insert
		//depth 증가
		/*
		 *  					gi  gs  gt(   =>의 간격)
		 *  AAAAAAAA(pno)    	3   0   0	
		 *  	=> BBB  		3   1   1
		 *  	  => CCC  		3   2   2
		 *      => DDD			3   1   1	>> DDD추가시 gs이 1이기 때문에 BBB
		 *      													 DDD
		 *       													  => CCC 가 됨 (답변깨짐)
		 *       AAAAAAAA(pno)  3   0   0
		 *       	=> DDD      3   1   1	>>맨위로 올리고 groupstep을 증가시킴 (맨아래로 내려도됨)
		 *        	=> BBB      3   2   1
		       			=> CCC 	3 	3	2		 
		 *      
		 */	
		try {
			getConnection();
			//댓글 대상의 정보를 가져옴
			String sql ="SELECT group_id,group_step,group_tab "
					+ "FROM replyBoard "
					+ "WHERE no=?";	//root번호
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi = rs.getInt(1);
			int gs = rs.getInt(2);
			int gt = rs.getInt(3);
			rs.close();
		
			///////////////////////가장 위로 올릴 수 있게함
			sql = "UPDATE replyBoard SET "
					+ "group_step = group_step+1 "
					+ "WHERE group_id=? AND group_step>?"; //step 큰거만 올려줌
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			
			/// 추가
			sql = "INSERT INTO replyBoard VALUES(rb_no_seq.nextval,?,?,?,?,SYSDATE,0,?,?,?,?,0)"; //0>>depth가 0으로 초기화
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7, gt+1);
			ps.setInt(8, root);
			ps.executeUpdate();
			
			/// depth를 증가
			sql ="UPDATE replyBoard SET "
					+ "depth=depth+1 "
					+ "WHERE no=?";		//root에 대한 depth를 올려줌
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
	}
	// 7. 검색
	public int boardFindCount(String fs,String ss) {		//list.jsp: 107
		int count=0;
		try {
			getConnection();
			String sql="SELECT COUNT(*) "
					+ "FROM replyBoard "
					+ "WHERE "+fs+" LIKE '%'||?||'%'"; // column/table => 문자열 결합 
			ps = conn.prepareStatement(sql);
			ps.setString(1, ss);	//''
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disConnection();
		}
		return count;
	}
	public List<ReplyBoardVO> boardFindData(String fs,String ss) {		//list.jsp: 107
			List<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		try {
			getConnection();
			String sql="SELECT no,subject,name,regdate,hit "
					+ "FROM replyBoard "
					+ "WHERE "+fs+" LIKE '%'||?||'%'"; // column/table => 문자열 결합 
			ps = conn.prepareStatement(sql);
			ps.setString(1, ss);	//''
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
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
