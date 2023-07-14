package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;

public class FoodJJimLikeDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db = new CreateDataBase();
	private static FoodJJimLikeDAO dao;
	
	public static FoodJJimLikeDAO newInstance() {
		if(dao == null)
			dao = new FoodJJimLikeDAO();
		return dao;
	}
	
	//JJim 저장
	public void foodJJimInsert(FoodJJimVO vo) {
		try {
			conn = db.getConnection();
			String sql = "INSERT INTO food_jjim VALUES("
					+ "fj_no_seq.nextval,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getFno());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	//JJim 확인
	public int foodJJimCount(String id,int fno) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql ="SELECT count(*) "
					+ "FROM food_jjim WHERE fno=? AND id=?";
			ps= conn.prepareStatement(sql);
			ps.setInt(1, fno);
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
	//JJim 목록 => 마이페이지 (찜관리)
	public List<FoodJJimVO> foodJJimListData(String id) {
		List<FoodJJimVO> list = new ArrayList<FoodJJimVO>();
		try {
			conn=db.getConnection();
/*			String sql="SELECT no,fno,"
					+ "(SELECT poster FROM food_house WHERE fno=food_jjim.fno),"  //CREATE OR REPLACE FUNCTION foodGetPoster함수
					+ "(SELECT name FROM food_house WHERE fno=food_jjim.fno)," //CREATE OR REPLACE FUNCTION foodGetName
					+ "(SELECT phone FROM food_house WHERE fno=food_jjim.fno) " //CREATE OR REPLACE FUNCTION foodGetPhone
					+ "FROM food_jjim "
					+ "WHERE id=? "
					+ "ORDER BY no DESC";
*/
			String sql = "SELECT no,fno,foodGetPoster(fno),foodGetName(fno),foodGetPhone(fno) "
					+ "FROM food_jjim "
					+ "WHERE id=? "
					+ "ORDER BY no DESC";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs  = ps.executeQuery();
			while(rs.next()) {
				FoodJJimVO vo = new FoodJJimVO();
				vo.setNo(rs.getInt(1));
				vo.setFno(rs.getInt(2));
				String poster = rs.getString(3);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setName(rs.getString(4));
				vo.setTel(rs.getString(5));
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
	// JJim 취소 //찜마다 고유번호 삭제하면됨
	public void foodJJimCancel(int no) {
		try {
			conn=db.getConnection();
			String sql = "DELETE FROM food_jjim WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	// Like 저장
	public void foodLikeInsert(FoodLikeVO vo) {	//vo에 Id랑 bno
		try {
			conn=db.getConnection();
			String sql = "INSERT INTO food_like VALUES("
					+ "fl_no_seq.nextval,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getId());
			ps.setInt(2, vo.getFno());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	// Like 총갯수 읽기 //맛집번호로
	public int foodLikeCount(int fno) {
		int count=0;
		try {
			conn=db.getConnection();
			String sql = "SELECT COUNT(*) FROM food_like WHERE fno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fno);
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
	//좋아요 눌렀는지 확잉ㄴ
	public int foodLikeOk(int fno,String id) {	//bno,id
		int count=0;
		try {
			conn=db.getConnection();
			String sql = "SELECT COUNT(*) FROM food_like WHERE fno=? AND id=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fno);
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
