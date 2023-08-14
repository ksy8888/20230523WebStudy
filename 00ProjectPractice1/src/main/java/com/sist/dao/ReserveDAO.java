package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;
public class ReserveDAO {
  private Connection conn;
  private PreparedStatement ps;
  private CreateDataBase db=new CreateDataBase();
  private static ReserveDAO dao;
  
  // 싱글턴
  public static ReserveDAO newInstance()
  {
	  if(dao==null)
		  dao=new ReserveDAO();
	  return dao;
  }
  // 맛집 읽기 
  //예약 가능한 날짜 => 맛집마다 예약 가능한 날을 일거 온다
  public String foodReserveDay(int fdno) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT reserve_day FROM food_detail "
					+ "WHERE fdno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	public String reserve_day_time(int rno) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT time FROM reserve_day "
					+ "WHERE rno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, rno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	public String reserve_get_time(int tno) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT time FROM reserve_time "
					+ "WHERE tno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, tno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	//예약 등록
	public void reserve_ok(ReserveFoodVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO reserve_info_food VALUES("
					+ "FD_FDNO_SEQ.nextval,?,?,?,?,?,'n',SYSDATE)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getFid());
			ps.setInt(2, vo.getFdno());
			ps.setString(3, vo.getFrday());
			ps.setString(4, vo.getFrtime());
			ps.setString(5, vo.getFinwon());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	//예약현황 출력
	public List<ReserveFoodVO> reserveFoodInfoData(String fid){
		List<ReserveFoodVO> list=new ArrayList<ReserveFoodVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT fno,fdno,frday,frtime,finwon,TO_CHAR(fregdate,'yyyy-MM-dd hh24:mi:ss'),"
					+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid "
					+ "FROM reserve_info_food "
					+ "WHERE fid=? "
					+ "ORDER BY fno DESC";
			ps=conn.prepareStatement(sql);
			ps.setString(1, fid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReserveFoodVO vo=new ReserveFoodVO();
				vo.setFno(rs.getInt(1));
				vo.setFdno(rs.getInt(2));
				vo.setFrday(rs.getString(3));
				vo.setFrtime(rs.getString(4));
				vo.setFinwon(rs.getString(5));
				vo.setFdbday(rs.getString(6));
				String poster=rs.getString(7);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setFposter(poster);
				vo.setFtitle(rs.getString(8));
				vo.setFtel(rs.getString(9));
				vo.setFrok(rs.getString(10));
				vo.setFid(rs.getString(11));
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
	public List<ReserveFoodVO> reserveFoodAdminData(){
		List<ReserveFoodVO> list=new ArrayList<ReserveFoodVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT fno,fdno,frday,frtime,finwon,TO_CHAR(fregdate,'yyyy-MM-dd hh24:mi:ss'),"
					+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid "
					+ "FROM reserve_info_food "
					+ "ORDER BY fno DESC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReserveFoodVO vo=new ReserveFoodVO();
				vo.setFno(rs.getInt(1));
				vo.setFdno(rs.getInt(2));
				vo.setFrday(rs.getString(3));
				vo.setFrtime(rs.getString(4));
				vo.setFinwon(rs.getString(5));
				vo.setFdbday(rs.getString(6));
				String poster=rs.getString(7);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setFposter(poster);
				vo.setFtitle(rs.getString(8));
				vo.setFtel(rs.getString(9));
				vo.setFrok(rs.getString(10));
				vo.setFid(rs.getString(11));
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
	
	public void reserveOk(int fno) {
		try {
			conn=db.getConnection();
			String sql="UPDATE reserve_info_food SET "
					+ "frok='y' "
					+ "WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fno);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	// 예약 정보
	public ReserveFoodVO reserveInfoData(int fdno) {
		ReserveFoodVO vo=new ReserveFoodVO();
		try {
			conn=db.getConnection();
			String sql="SELECT fno,frday,frtime,finwon,TO_CHAR(fregdate,'YYYY-MM-DD HH24:MI:SS') "
				     +"FROM reserve_info_food "
				     +"WHERE fno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFno(rs.getInt(1));
			vo.setFrday(rs.getString(2));
			vo.setFrtime(rs.getString(3));
			vo.setFinwon(rs.getString(4));
			vo.setFdbday(rs.getString(5));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 맛집 정보
	// public FoodVO findByFno(int fno);
	public FoodVO reserveFoodInfoData(int fdno) {
		FoodVO vo=new FoodVO();
		try {
			conn=db.getConnection();
			String sql="SELECT title,poster,address,tel,type,price,parking,menu,score "
					+ "FROM food_detail "
					+ "WHERE fdno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setTitle(rs.getString(1));
			vo.setPoster(rs.getString(2));
			vo.setAddress(rs.getString(3));
			vo.setTel(rs.getString(4));
			vo.setType(rs.getString(5));
			vo.setPrice(rs.getString(6));
			vo.setParking(rs.getString(7));
			vo.setMenu(rs.getString(8));
			vo.setScore(rs.getDouble(9));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	
	
	
	
	//주방
	public String shareReserveDay(int skdno) {
		String result="";
		try {
			conn=db.getConnection();
			String sql="SELECT reserve_day FROM SHARE_KITCHEN_DETAIL "
					+ "WHERE skdno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, skdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			result=rs.getString(1);
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return result;
	}
	//예약 등록
	public void sreserve_ok(ReserveShareVO vo) {
		try {
			conn=db.getConnection();
			String sql="INSERT INTO reserve_info_share VALUES("
					+ "sno_sno_seq.nextval,?,?,?,?,?,'n',SYSDATE)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getSid());
			ps.setInt(2, vo.getSkdno());
			ps.setString(3, vo.getSrday());
			ps.setString(4, vo.getSrtime());
			ps.setString(5, vo.getSinwon());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	//예약현황 출력
	public List<ReserveShareVO> reserveShareInfoData(String sid){
		List<ReserveShareVO> list=new ArrayList<ReserveShareVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT sno,skdno,srday,srtime,sinwon,TO_CHAR(sregdate,'yyyy-MM-dd hh24:mi:ss'),"
					+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid "
					+ "FROM reserve_info_share "
					+ "WHERE sid=? "
					+ "ORDER BY sno DESC";
			ps=conn.prepareStatement(sql);
			ps.setString(1, sid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReserveShareVO vo=new ReserveShareVO();
				vo.setSno(rs.getInt(1));
				vo.setSkdno(rs.getInt(2));
				vo.setSrday(rs.getString(3));
				vo.setSrtime(rs.getString(4));
				vo.setSinwon(rs.getString(5));
				vo.setSdbday(rs.getString(6));
				String poster=rs.getString(7);
				poster=poster.replaceAll("#", "&");
				vo.setSposter(poster);
				vo.setStitle(rs.getString(8));
				vo.setSprice(rs.getString(9));
				vo.setSrok(rs.getString(10));
				vo.setSid(rs.getString(11));
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
	public List<ReserveShareVO> reserveShareAdminData(){
		List<ReserveShareVO> list=new ArrayList<ReserveShareVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT sno,skdno,srday,srtime,sinwon,TO_CHAR(sregdate,'yyyy-MM-dd hh24:mi:ss'),"
					+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid "
					+ "FROM reserve_info_share "
					+ "ORDER BY sno DESC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				ReserveShareVO vo=new ReserveShareVO();
				vo.setSno(rs.getInt(1));
				vo.setSkdno(rs.getInt(2));
				vo.setSrday(rs.getString(3));
				vo.setSrtime(rs.getString(4));
				vo.setSinwon(rs.getString(5));
				vo.setSdbday(rs.getString(6));
				String poster=rs.getString(7);
				poster=poster.replaceAll("#", "&");
				vo.setSposter(poster);
				vo.setStitle(rs.getString(8));
				vo.setSprice(rs.getString(9));
				vo.setSrok(rs.getString(10));
				vo.setSid(rs.getString(11));
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
	public void sreserveOk(int sno) {
		try {
			conn=db.getConnection();
			String sql="UPDATE reserve_info_share SET "
					+ "srok='y' "
					+ "WHERE sno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, sno);
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			db.disConnection(conn, ps);
		}
	}
	// 예약 정보
	public ReserveShareVO sreserveInfoData(int skdno) {
		ReserveShareVO vo=new ReserveShareVO();
		try {
			conn=db.getConnection();
			String sql="SELECT sno,srday,srtime,sinwon,TO_CHAR(sregdate,'YYYY-MM-DD HH24:MI:SS') "
				     +"FROM reserve_info_share "
				     +"WHERE sno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, skdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setSno(rs.getInt(1));
			vo.setSrday(rs.getString(2));
			vo.setSrtime(rs.getString(3));
			vo.setSinwon(rs.getString(4));
			vo.setSdbday(rs.getString(5));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
	// 맛집 정보
	// public FoodVO findByFno(int fno);
	public ShareVO reserveShareInfoData(int skdno) {
		ShareVO vo=new ShareVO();
		try {
			conn=db.getConnection();
			String sql="SELECT * FROM share_kitchen_detail WHERE skdno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, skdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setSkdno(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setSub_title(rs.getString(3));
			vo.setIntro(rs.getString(4));
			vo.setRun(rs.getString(5));
			vo.setHoli(rs.getString(6));
			//svo.setInfo(rs.getString(7));
			String info=rs.getString(7);
			info=info.substring(info.indexOf("1")+1);
			vo.setInfo(info);
			vo.setCaution(rs.getString(8));
			vo.setAddress(rs.getString(9));
			vo.setHs_tag(rs.getString(10));
			vo.setMax_mem(rs.getString(11));
			String poster=rs.getString(12);
			poster=poster.replaceAll("#", "&");
			vo.setPoster(poster);
			vo.setPrice(rs.getString(13));
			vo.setRefund(rs.getString(14));
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		return vo;
	}
}








