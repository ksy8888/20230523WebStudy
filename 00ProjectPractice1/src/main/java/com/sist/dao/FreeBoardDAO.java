package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.vo.*;

import oracle.net.aso.f;

import com.sist.common.*;

public class FreeBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FreeBoardDAO dao;
	
	//싱글턴
	public static FreeBoardDAO newInstance() {
		if(dao==null) {
			dao = new FreeBoardDAO();
		}
		return dao;
	}
	
	//기능
	//카테고리명 읽기
	public FreeBoardCategoryVO freeboardCategoryData(int cno) {
		FreeBoardCategoryVO vo = new FreeBoardCategoryVO();
		try {
			conn = db.getConnection();
			String sql ="SELECT no FROM board_category WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cno);
			
			ResultSet rs = ps.executeQuery();			
			rs.next();
			vo.setCname(rs.getString(1));
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
				
	}
	//fd: 검색내용 type: 검색카테고리
	public List<FreeBoardVO> boardFindData(String fd, String type,int page) {
	      List<FreeBoardVO> list = new ArrayList<FreeBoardVO>();
	      try {
	         conn = db.getConnection();
	         
	         
	         //SELECT bno,name,subject,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest FROM YORI_FREEBOARD WHERE subject LIKE '%자유%';
	         String sql = "SELECT bno,name,subject,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest,id,rownum "
	               + "FROM (SELECT bno,name,subject,regdate,hit,suggest,id,rownum as num "
	               + "FROM (SELECT /*+ INDEX_DESC(yori_freeboard yf_bno_pk)*/bno,name,subject,regdate,hit,suggest,id "               
	               + "FROM yori_freeboard WHERE "+type+" LIKE '%'||?||'%')) "
	               + "WHERE num BETWEEN ? AND ?";

	         ps = conn.prepareStatement(sql);
	         
	         int rowSize = 5;
	         int start = (rowSize*page)-(rowSize-1);
	         int end = rowSize*page;
	         
	         //ps.setString(1, type);           
	         ps.setString(1, fd);
	         ps.setInt(2, start);
	         ps.setInt(3, end);
	         
	         System.out.println(type);
	         System.out.println(fd);
	         ResultSet rs = ps.executeQuery();
	         while(rs.next()) {
	            FreeBoardVO vo = new FreeBoardVO();
	            vo.setBno(rs.getInt(1));
	            vo.setName(rs.getString(2));
	            vo.setSubject(rs.getString(3));
	            vo.setDbday(rs.getString(4));
	            vo.setHit(rs.getInt(5));
	            vo.setSuggest(rs.getInt(6));
	         
	            vo.setId(rs.getString(7));
	            vo.setRownum(rs.getInt(8));
	            list.add(vo);
	            //System.out.println(vo.getSubject());
	         }
	         rs.close();   
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         db.disConnection(conn, ps);
	      }
	      return list;
	   }
	   
	   public int freeboardFindTotalPage(String fd, String type) {
	      int total=0;
	      try {
	         conn=db.getConnection();
	         
	            String sql = "SELECT CEIL(COUNT(*)/5.0) FROM yori_freeboard WHERE "+type+" LIKE '%'||?||'%'";
	            ps = conn.prepareStatement(sql);
	            
	            ps.setString(1, fd);
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
	
	//1. 목록 출력
	public List<FreeBoardVO> freeboardListData(int page, int cno) {
		List<FreeBoardVO> list = new ArrayList<FreeBoardVO>();
		
		try {
			conn=db.getConnection();
			
			int rowSize = 8;
			int start = (rowSize*page)-(rowSize-1);
			int end = rowSize*page;
			
			String sql ="";
			if(cno==0) {
				 sql = "SELECT bno,name,subject,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest,rownum,cno,id "
						+"FROM (SELECT bno,name,subject,regdate,hit,suggest,rownum as num,cno,id "
						+ "FROM (SELECT /*+ INDEX_DESC(yori_freeboard yf_bno_pk)*/bno,name,subject,regdate,hit,suggest,cno,id "
						+ "FROM yori_freeboard)) "
						+ "WHERE num BETWEEN ? AND ?";
				 ps = conn.prepareStatement(sql);
				 ps.setInt(1, start);
				 ps.setInt(2, end);
				  
			} else {
				 sql = "SELECT bno,name,subject,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest,rownum,cno,id "
						+ "FROM (SELECT bno,name,subject,regdate,hit,suggest,rownum as num,cno,id "
						+ "FROM (SELECT /*+ INDEX_DESC(yori_freeboard yf_bno_pk)*/bno,name,subject,regdate,hit,suggest,cno,id "
						+ "FROM yori_freeboard WHERE cno=?)) "
						+ "WHERE num BETWEEN ? AND ?";
				 ps = conn.prepareStatement(sql);
				 ps.setInt(1, cno);
				 ps.setInt(2, start);
				 ps.setInt(3, end);
			}

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				FreeBoardVO vo = new FreeBoardVO();
				vo.setBno(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setDbday(rs.getString(4));
				vo.setHit(rs.getInt(5));
				vo.setSuggest(rs.getInt(6));
				vo.setRownum(rs.getInt(7));
				vo.setCno(rs.getInt(8));
				vo.setId(rs.getString(9));
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
	

	
	
	  public FreeBoardCategoryVO Category(int cno) { 
		  FreeBoardCategoryVO vo = new FreeBoardCategoryVO();
		  try { 			  				
			  conn = db.getConnection(); 
			  String sql = "SELECT category_name FROM board_category WHERE no=?"; 
			  ps =  conn.prepareStatement(sql);
			  ps.setInt(1, cno); 
			  ResultSet rs = ps.executeQuery(); 
			  rs.next(); 
			  vo.setCname(rs.getString(1));
			  rs.close();
	  
	  } catch (Exception e) { e.printStackTrace(); } finally {
	  db.disConnection(conn, ps); } return vo; }
	 
	

	//1.1 총페이지
	public int freeboardTotalPage(int cno) {
		int total=0;
		try {
			conn=db.getConnection();
			if(cno==0) {
				String sql = "SELECT CEIL(COUNT(*)/10.0) FROM yori_freeboard";
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				rs.next();
				total = rs.getInt(1);
				rs.close();
			}
			else {
				String sql = "SELECT CEIL(COUNT(*)/10.0) FROM yori_freeboard WHERE cno="+cno;
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				rs.next();
				total = rs.getInt(1);
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return total;
	}
	
	//상세
	public FreeBoardVO freeboardDetailData(int bno) {
		FreeBoardVO vo = new FreeBoardVO();
		
		try {
			conn = db.getConnection();
			
			//hit
			String sql = "UPDATE yori_freeboard SET hit=hit+1 "
					+ "WHERE bno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ps.executeUpdate();
			
			//
			sql = "SELECT bno,name,subject,content,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest,filename,id "
					+ "FROM yori_freeboard "
					+ "WHERE bno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setBno(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			vo.setSuggest(rs.getInt(7));
			vo.setFilename(rs.getString(8));
			vo.setId(rs.getString(9));
//			System.out.println("c:\\download\\"+vo.getFilename());

			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
//	
	public void freeboardInsert(FreeBoardVO vo) {
		try {
			conn=db.getConnection();
			String sql = "INSERT INTO yori_freeboard(bno,name,subject,content,filename,filesize,pwd,cno,id) VALUES("
					+ "(SELECT NVL(MAX(bno)+1,1) FROM yori_freeboard),?,?,?,?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getFilename());
			ps.setInt(5, vo.getFilesize());		
			ps.setString(6, vo.getPwd());
			ps.setInt(7, vo.getCno());
			ps.setString(8, vo.getId());   //session
			
			ps.executeUpdate();
/*			
			System.out.println(vo.getName());
			System.out.println(vo.getFilename());
			System.out.println(vo.getFilesize());
			System.out.println(vo.getSubject());
*/		
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	
	public String freeboardDelete(int bno, String pwd) {
		String res = "NO";
		try {
			conn=db.getConnection();
			String sql = "SELECT pwd FROM yori_freeboard WHERE bno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(pwd)) {
				res = "YES";
				sql="DELETE FROM freeboard_like WHERE bno=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, bno);
				ps.executeUpdate();
				
				sql="DELETE FROM freeboard_reply WHERE bno=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, bno);
				ps.executeUpdate();
				
				sql="DELETE FROM yori_freeboard WHERE bno=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, bno);
				ps.executeUpdate();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return res;
	}
	
	public FreeBoardVO freeboardUpdateData(int bno) {
		FreeBoardVO vo = new FreeBoardVO();
		try {
			conn = db.getConnection();
			String sql = "SELECT bno,name,subject,content,regdate "
					+ "FROM yori_freeboard WHERE bno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setBno(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
	public boolean freeboardUpdate(FreeBoardVO vo) {
		boolean bCheck = false;
		
		try {
			conn = db.getConnection();
			String sql = "SELECT pwd FROM yori_freeboard "
					+ "WHERE bno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getBno());
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd())) {
				bCheck=true;
				sql = "UPDATE yori_freeboard "
						+ "SET name=?,subject=?,content=?, regdate=sysdate "
						+ "WHERE bno=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getBno());
				
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return bCheck;
	}
}
