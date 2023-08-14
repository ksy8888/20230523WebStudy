package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.common.CreateDataBase;
import com.sist.vo.*;
import com.sist.dao.*;

public class JjimDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static JjimDAO dao;
	
	public static JjimDAO newInstance() {
		if(dao==null)dao= new JjimDAO();
		return dao;
	}
	
	
	public void JjimInsert(JjimVO vo) {
		try {
			conn = db.getConnection();
			String sql = "insert into jjim values("
					+ "jj_no_seq.nextVal,?,?,?)"; // no, type,id,cno(1,2,3,4)
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getType());
			ps.setString(2, vo.getId());
			ps.setInt(3, vo.getCno());
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	public int JjimCount(String id, int cno,int type) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql ="select count(*) "
					+ "from jjim "
					+ "where cno=? "
					+ "and id=? "
					+ "and type=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ps.setString(2, id);
			ps.setInt(3, type);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return count;
	}
	public List<JjimVO> jjimListData(String id,int type,int page){
		List<JjimVO> list = new ArrayList<>();
		try {
			conn = db.getConnection();
//			레시피에서 -> 찜 -> 이름,사진,쉐프
//		getposter,gettitle,getchef(****)
//-------------------------------------------
//			맛집 - >찜 -> 사진, 이름, 전화
//			                     getTel(*****)
//-------------------------------------------
//		상품 ->사진, 이름,  가격? getprice
//		--------------------------------------
//		공유주방 -> 이름 , 사진, 가격 getPrice;
			
//			String sql = "select no,cno,"
//					+ "nvl(recipegettitle(cno),'no'), "
//					+ "nvl(recipegetPoster(cno),'no'), "
//					+ "nvl(recipegetchef(cno),'no'), "
//					+ "nvl(foodgettitle(cno),'no'),"
//					+ "nvl(foodgetposter(cno),'no'),"
//					+ "nvl(foodgettel(cno),'no'), "
//					+ "nvl(productgettitle(cno),'no'),"
//					+ "nvl(productgetposter(cno),'no'),"
//					+ "nvl(productgetprice(cno),'no'),"
//					+ "nvl(sharegettitle(cno),'no'),"
//					+ "nvl(sharegetposter(cno),'no'),"
//					+ "nvl(sharegetprice(cno),'no') "
//					+ "FROM jjim "
//					+ "where id=? and type=? "
//					+ "order by no desc";
			String sql = "select no,cno,"
					+ "nvl(recipegettitle(cno),'no'), "
					+ "nvl(recipegetPoster(cno),'no'), "
					+ "nvl(recipegetchef(cno),'no'), "
					+ "nvl(foodgettitle(cno),'no'),"
					+ "nvl(foodgetposter(cno),'no'),"
					+ "nvl(foodgettel(cno),'no'), "
					+ "nvl(productgettitle(cno),'no'),"
					+ "nvl(productgetposter(cno),'no'),"
					+ "nvl(productgetprice(cno),'no'),"
					+ "nvl(sharegettitle(cno),'no'),"
					+ "nvl(sharegetposter(cno),'no'),"
					+ "nvl(sharegetprice(cno),'no'),num "
					+ "FROM (SELECT no,cno,"
					+ "nvl(recipegettitle(cno),'no'), "
					+ "nvl(recipegetPoster(cno),'no'), "
					+ "nvl(recipegetchef(cno),'no'), "
					+ "nvl(foodgettitle(cno),'no'),"
					+ "nvl(foodgetposter(cno),'no'),"
					+ "nvl(foodgettel(cno),'no'), "
					+ "nvl(productgettitle(cno),'no'),"
					+ "nvl(productgetposter(cno),'no'),"
					+ "nvl(productgetprice(cno),'no'),"
					+ "nvl(sharegettitle(cno),'no'),"
					+ "nvl(sharegetposter(cno),'no'),"
					+ "nvl(sharegetprice(cno),'no'),rownum as num "
					+ "FROM (SELECT no,cno,"
					+ "nvl(recipegettitle(cno),'no'), "
					+ "nvl(recipegetPoster(cno),'no'), "
					+ "nvl(recipegetchef(cno),'no'), "
					+ "nvl(foodgettitle(cno),'no'),"
					+ "nvl(foodgetposter(cno),'no'),"
					+ "nvl(foodgettel(cno),'no'), "
					+ "nvl(productgettitle(cno),'no'),"
					+ "nvl(productgetposter(cno),'no'),"
					+ "nvl(productgetprice(cno),'no'),"
					+ "nvl(sharegettitle(cno),'no'),"
					+ "nvl(sharegetposter(cno),'no'),"
					+ "nvl(sharegetprice(cno),'no') "
					+ "FROM JJIM where id=? and type=?)) "
					+ "WHERE NUM BETWEEN ? AND ? "
					+ "order by no desc";
			ps = conn.prepareStatement(sql);
			int rowSize=10;
			int start=(rowSize*page)-(rowSize-1);
			int end= rowSize*page;
			ps.setString(1, id);
			ps.setInt(2, type);
			ps.setInt(3, start);
			ps.setInt(4, end);
			ResultSet rs =ps.executeQuery();
			while(rs.next()) {
				JjimVO vo = new JjimVO();
				vo.setNo(rs.getInt(1));
				vo.setCno(rs.getInt(2));
				if(type == 1) { // recipe
					vo.setTitle(rs.getString(3));
					vo.setPoster(rs.getString(4));
					vo.setChef(rs.getString(5));
				}
				else if(type == 2) { // food
					vo.setTitle(rs.getString(6));
					String poster = rs.getString(7);
					poster=poster.substring(0,poster.indexOf("^"));
					poster = poster.replace("#","&");
					vo.setPoster(poster);
					vo.setTel(rs.getString(8));
				}
				else if(type == 3) {
					vo.setTitle(rs.getString(9));
					vo.setPoster(rs.getString(10));
					vo.setPrice(rs.getString(11));
				}
				else if(type == 4){
					vo.setTitle(rs.getString(12));
				String poster = rs.getString(13);
//					poster=poster.substring(0,poster.indexOf("^"));
				poster = poster.replace("#","&");
					vo.setPoster(poster);
					vo.setPrice(rs.getString(14));
				}
				
//				String poster = rs.getString(3);
//				poster=poster.substring(0,poster.indexOf("^"));
//				poster = poster.replace("#","&");
//				vo.setPoster(poster);
//				vo.setName(rs.getString(4));
//				vo.setTel(rs.getString(5));
				list.add(vo);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return list;
	}
	public int jjimTotalPage(String id,int type)
	{
		int total=0;
		try
		{
			conn=db.getConnection();
			String sql="SELECT CEIL(COUNT(*)/10.0) FROM JJIM WHERE id=? and type=? ";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, type);
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
	// 찜 취소
	public void JjimCancel(int no) {
		try {
			conn = db.getConnection();
			String sql ="delete from jjim "
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
	public int jjimNo(String id, int cno,int type) {
		int no=0;
		try {
			conn=db.getConnection();
			String sql ="select no "
					+ "from jjim "
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
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return no;
	}
}
