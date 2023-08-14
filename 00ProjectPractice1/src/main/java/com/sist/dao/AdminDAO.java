package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sist.common.CreateDataBase;
import com.sist.vo.FreeBoardVO;
import com.sist.vo.MemberVO;
import com.sist.vo.ProductVO;
import com.sist.vo.ReserveFoodVO;
import com.sist.vo.ReserveShareVO;

import java.util.*;

public class AdminDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static AdminDAO dao;
	public static AdminDAO newInstance()
	{
		if(dao==null)
			dao=new AdminDAO();
		return dao;
	}
//	public List<MemberVO> userInfo(int page)
	public List<MemberVO> userInfo(int page, String fd)
	{
		List<MemberVO> list=new ArrayList<MemberVO>();
		try
		{
			conn=db.getConnection();
			String sql="";
			if(fd==null)
			{
				sql="SELECT id,name,nickname,sex,birthday,email,post,addr1,addr2,phone,num "
					+ "(SELECT id,name,nickname,sex,birthday,email,post,addr1,addr2,phone,rownum as num "
					+ "FROM project_member)"
					+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=20;
				int startPage=(rowSize*page)-(rowSize-1);
				int endPage=rowSize*page;
				ps.setInt(1, startPage);
				ps.setInt(2, endPage);
			}
			else
			{
				sql= "SELECT id,name,nickname,sex,birthday,email,post,addr1,addr2,phone,num "
						+ "FROM (SELECT id,name,nickname,sex,birthday,email,post,addr1,addr2,phone,rownum as num "
						+ "FROM project_member WHERE id LIKE '%'||?||'%' or name LIKE '%'||?||'%')"
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=20;
				int startPage=(rowSize*page)-(rowSize-1);
				int endPage=rowSize*page;
				ps.setString(1, fd);
				ps.setString(2, fd);
				ps.setInt(3, startPage);
				ps.setInt(4, endPage);
			}
			
//			int rowSize = 20;
//			int start = (rowSize*page)-(rowSize-1);
//			int end = rowSize*page;
//			
//			ps.setInt(1, start);
//			ps.setInt(2, end);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				MemberVO vo=new MemberVO();
				vo.setId(rs.getString(1));
				vo.setName(rs.getString(2));
				vo.setNickname(rs.getString(3));
				vo.setSex(rs.getString(4));
				vo.setBirthday(rs.getString(5));
				vo.setEmail(rs.getString(6));
				vo.setPost(rs.getString(7));
				vo.setAddr1(rs.getString(8));
				String addr2=rs.getString(9);
				if(addr2==null)
				 addr2="없음";
				vo.setAddr2(addr2);
				String phone=rs.getString(10);
				if(phone==null)
				 phone="없음";
				vo.setPhone(phone);
	//			vo.setAddr2(rs.getString(9));
	//			vo.setPhone(rs.getString(10));
				list.add(vo);
			}
			rs.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return list;
	}
	public int userinfoTotalPage(String fd)
	{
		int totalpage=0;
		try
		{
			conn=db.getConnection();
			
			String sql="SELECT CEIL(COUNT(*)/20.0) FROM project_member WHERE id LIKE '%'||?||'%' or name LIKE '%'||?||'%'";	
			
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			ps.setString(2, fd);
			ResultSet rs=ps.executeQuery();
			rs.next();
			totalpage=rs.getInt(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return totalpage;
	}
	
	public void userinfoUpdate(MemberVO vo)
	{
		try
		{
			conn=db.getConnection();
			String sql="UPDATE project_member SET "
					+  "name=?,nickname=?,birthday=?,email=?,sex=?,post=?,addr1=?,addr2=?,phone=? "
					+ "WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getNickname());
			ps.setString(3, vo.getBirthday());
			ps.setString(4, vo.getEmail());
			ps.setString(5, vo.getSex());
			ps.setString(6, vo.getPost());
			ps.setString(7, vo.getAddr1());
			ps.setString(8, vo.getAddr2());
			ps.setString(9, vo.getPhone());
			
			ps.setString(10, vo.getId());
			
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	public void userinfoDelete(String id)
	{
		try
		{
			conn=db.getConnection();
			String sql="DELETE FROM project_member "
					+ "WHERE id=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			
			
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public List<ProductVO> ProductListManager(int page,String fd)
	{
		List<ProductVO> plist=new ArrayList<ProductVO>();
		try
		{
			conn=db.getConnection();
			// 메인페이지 더보기 거기에 랜덤하게 출력해주기
			String sql="";
			if(fd==null)
			{
				sql="SELECT pdno,title,poster,subject,sale,priced_sale,original_pri,first_pri,score,delivery_pri,goods_count "
						+ "FROM (SELECT pdno,title,poster,subject,sale,priced_sale,original_pri,first_pri,score,delivery_pri,goods_count,rownum as num "
						+ "FROM (SELECT /*+INDEX_ASC(product_detail PK_PRODUCT_DETAIL)*/pdno,title,poster,subject,sale,priced_sale,original_pri,first_pri,score,delivery_pri,goods_count "
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=20;
				int startPage=(rowSize*page)-(rowSize-1);
				int endPage=rowSize*page;
				ps.setInt(1, startPage);
				ps.setInt(2, endPage);
			}
			else
			{
				sql= "SELECT pdno,title,poster,subject,sale,priced_sale,original_pri,first_pri,score,delivery_pri,goods_count,num "
						+ "FROM (SELECT pdno,title,poster,subject,sale,priced_sale,original_pri,first_pri,score,delivery_pri,goods_count,rownum as num "
						+ "FROM (SELECT /*+INDEX_ASC(product_detail PK_PRODUCT_DETAIL)*/pdno,title,poster,subject,sale,priced_sale,original_pri,first_pri,score,delivery_pri,goods_count "
						+ "FROM product_detail WHERE title LIKE '%'||?||'%'))"
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=20;
				int startPage=(rowSize*page)-(rowSize-1);
				int endPage=rowSize*page;
				ps.setString(1, fd);
				ps.setInt(2, startPage);
				ps.setInt(3, endPage);

			}
			
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ProductVO vo=new ProductVO();
				vo.setPdno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setSubject(rs.getString(4));
				vo.setSale(rs.getString(5));
				vo.setPriced_sale(rs.getString(6));
				vo.setOriginal_pri(rs.getString(7));
				vo.setFirst_pri(rs.getString(8));
				vo.setScore(rs.getDouble(9));
				vo.setDelivery_pri(rs.getString(10));
				vo.setGoods_count(rs.getInt(11));
				plist.add(vo);
			}
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	
		return plist;
	}
	public int adminProductTotalPage(String fd)
	{
		int totalpage=0;
		try
		{
			conn=db.getConnection();
			
			String sql="SELECT CEIL(COUNT(*)/20.0) FROM product_detail WHERE title LIKE '%'||?||'%'";	
			
			ps=conn.prepareStatement(sql);
			ps.setString(1, fd);
			
			ResultSet rs=ps.executeQuery();
			rs.next();
			totalpage=rs.getInt(1);
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return totalpage;
	}
	
	
	public void product_update(ProductVO vo)
	{
		try
		{
			conn=db.getConnection();
			String sql="UPDATE product_detail SET "
					+  "title=?,subject=?,sale=?,priced_sale=?,original_pri=?,first_pri=?,delivery_pri=?,goods_count=? "
					+ "WHERE pdno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getSale());
			ps.setString(4, vo.getPriced_sale());
			ps.setString(5, vo.getOriginal_pri());
			ps.setString(6, vo.getFirst_pri());
			ps.setString(7, vo.getDelivery_pri());
			ps.setInt(8, vo.getGoods_count());
			
			
			ps.setInt(9, vo.getPdno());
			
			
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	
	public void productDelete(int pdno)
	{
		try
		{
			conn=db.getConnection();
			String sql="DELETE FROM product_detail "
					+ "WHERE pdno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, pdno);
			
			
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	
	public void product_insert(ProductVO vo)
	{
		try
		{
			conn=db.getConnection();
			String sql="INSERT INTO product_detail(pdno,title,poster,subject,sale,priced_sale,original_pri,first_pri,score,delivery_pri,goods_count) VALUES("
					+ "(SELECT NVL(MAX(pdno)+1,1) FROM product_detail),?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getPoster());
			ps.setString(3, vo.getSubject());
			ps.setString(4, vo.getSale());
			ps.setString(5, vo.getPriced_sale());
			ps.setString(6, vo.getOriginal_pri());
			ps.setString(7, vo.getFirst_pri());
			ps.setDouble(8, vo.getScore());
			ps.setString(9, vo.getDelivery_pri());
			ps.setInt(10, vo.getGoods_count());
			
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<FreeBoardVO> boardManagerListData(int page,String fd) {
		List<FreeBoardVO> list = new ArrayList<FreeBoardVO>();
				
				try {
					conn=db.getConnection();
					String sql="";
					if(fd==null)
					{
						sql = "SELECT bno,name,subject,content,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest,rownum "
								+ "FROM (SELECT bno,name,subject,content,regdate,hit,suggest,rownum as num "
								+ "FROM (SELECT /*+ INDEX_DESC(yori_freeboard yf_bno_pk)*/bno,name,subject,content,regdate,hit,suggest "
								+ "FROM yori_freeboard)) "
								+ "WHERE num BETWEEN ? AND ?";
						ps = conn.prepareStatement(sql);
						int rowSize = 20;
						int start = (rowSize*page)-(rowSize-1);
						int end = rowSize*page;
						ps.setInt(1, start);
						ps.setInt(2, end);
					}
					else
					{
						sql = "SELECT bno,name,subject,content,TO_CHAR(regdate,'yyyy-mm-dd'),hit,suggest,rownum "
								+ "FROM (SELECT bno,name,subject,content,regdate,hit,suggest,rownum as num "
								+ "FROM (SELECT /*+ INDEX_DESC(yori_freeboard yf_bno_pk)*/bno,name,subject,content,regdate,hit,suggest "
								+ "FROM yori_freeboard WHERE content LIKE '%'||?||'%' or name LIKE '%'||?||'%' or subject LIKE '%'||?||'%')) "
								+ "WHERE num BETWEEN ? AND ?";
						ps = conn.prepareStatement(sql);
						
						int rowSize = 20;
						int start = (rowSize*page)-(rowSize-1);
						int end = rowSize*page;
						ps.setString(1, fd);
						ps.setString(2, fd);
						ps.setString(3, fd);
						ps.setInt(4, start);
						ps.setInt(5, end);
					}
					ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						FreeBoardVO vo = new FreeBoardVO();
						vo.setBno(rs.getInt(1));
						vo.setName(rs.getString(2));
						vo.setSubject(rs.getString(3));
						vo.setContent(rs.getString(4));
						vo.setDbday(rs.getString(5));
						vo.setHit(rs.getInt(6));
						vo.setSuggest(rs.getInt(7));
						vo.setRownum(rs.getInt(8));
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
	
	//1.1 총페이지
	public int boardManagerTotalPage(String fd) {
		int total=0;
		try {
			conn=db.getConnection();
			String sql = "SELECT CEIL(COUNT(*)/20.0) FROM yori_freeboard WHERE content LIKE '%'||?||'%' or name LIKE '%'||?||'%' or subject LIKE '%'||?||'%'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, fd);
			ps.setString(2, fd);
			ps.setString(3, fd);
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
	
	public void adminBoardUpdate(FreeBoardVO vo)
	{
		try
		{
			conn=db.getConnection();
			String sql="UPDATE yori_freeboard SET "
					 + "subject=?,content=? "
					 + "WHERE bno=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getSubject());
			ps.setString(2, vo.getContent());
			
			ps.setInt(3, vo.getBno());
			
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	public void adminBoardDelete(int bno)
	{
		try
		{
			conn=db.getConnection();
			String sql="DELETE FROM yori_freeboard "
					+ "WHERE bno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, bno);
			
			
			ps.executeUpdate();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
	}
	// 검색
			public List<ReserveFoodVO> adminreserveFind(int page, String fd){
				List<ReserveFoodVO> rlist=new ArrayList<ReserveFoodVO>();
				try {
					conn=db.getConnection();
					String sql="SELECT fno,fdno,frday,frtime,finwon,TO_CHAR(fregdate,'yyyy-MM-dd hh24:mi:ss'),"
							+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid, num "
							+ "FROM (SELECT fno,fdno,frday,frtime,finwon,fregdate, "
							+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid, rownum as num "
							+ "FROM (SELECT /*+ INDEX_DESC(reserve_info_food fd_fno_pk)*/ fno,fdno,frday,frtime,finwon,fregdate, "
							+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid "
							+ "FROM reserve_info_food where foodGetTitle(fdno) LIKE '%'||?||'%')) "
							+ "WHERE num BETWEEN ? AND ?";
					ps=conn.prepareStatement(sql);
					ps.setString(1, fd);
					System.out.println(fd);
					int rowSize=10;
					int startpage=(page-1)*rowSize+1;
					int endpage=page*rowSize;
					ps.setInt(2, startpage);
					ps.setInt(3, endpage);
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
						rlist.add(vo);
					}
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.disConnection(conn, ps);
				}
				return rlist;
			}
			//검색 총 페이지
			public int adminreserveFindTotalpage(String fd) {
				int totalpage=0;
				try {
					conn=db.getConnection();
					String sql="SELECT CEIL(COUNT(*)/10.0) "
							+ "FROM reserve_info_food "
							+ "WHERE foodGetTitle(fdno) LIKE '%'||?||'%'";
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					db.disConnection(conn, ps);
				}
				return totalpage;
			}
	///////////////////////////////////////////////////////////////////////////////
	//예약페이징
	//맛집 페이지 출력
		public List<ReserveFoodVO> reserveFoodAdminListData(int page){
			List<ReserveFoodVO> list=new ArrayList<ReserveFoodVO>();
			try {
				conn=db.getConnection();
				String sql="SELECT fno,fdno,frday,frtime,finwon,TO_CHAR(fregdate,'yyyy-MM-dd hh24:mi:ss'),"
						+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid, rownum "
						+ "FROM (SELECT fno,fdno,frday,frtime,finwon,fregdate, "
						+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid, rownum as num "
						+ "FROM (SELECT /*+ INDEX_DESC(reserve_info_food fd_fno_pk)*/ fno,fdno,frday,frtime,finwon,fregdate, "
						+ "foodGetPoster(fdno),foodGetTitle(fdno),foodGetTel(fdno),frok,fid "
						+ "FROM reserve_info_food)) "
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=10;
				int start=(page-1)*rowSize+1;
				int end=page*rowSize;
				ps.setInt(1, start);
				ps.setInt(2, end);
				
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
		//총페이지
		public int reserveFoodAdmintotalpage() {
			int total=0;
			try {
				conn=db.getConnection();
				String sql="SELECT CEIL(COUNT(*)/10.0) "
						+ "FROM reserve_info_food";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				rs.next();
				total=rs.getInt(1);
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
			return total;
		}
		//예약페이징
		//공유주방 페이지 출력
		public List<ReserveShareVO> reserveShareAdminListData(int page){
			List<ReserveShareVO> list=new ArrayList<ReserveShareVO>();
			try {
				conn=db.getConnection();
				String sql="SELECT sno,skdno,srday,srtime,sinwon,TO_CHAR(sregdate,'yyyy-MM-dd hh24:mi:ss'),"
						+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid, rownum "
						+ "FROM (SELECT sno,skdno,srday,srtime,sinwon,sregdate, "
						+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid, rownum as num "
						+ "FROM (SELECT /*+ INDEX_DESC(reserve_info_share skd_sno_pk)*/ sno,skdno,srday,srtime,sinwon,sregdate, "
						+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid "
						+ "FROM reserve_info_share)) "
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				int rowSize=10;
				int start=(page-1)*rowSize+1;
				int end=page*rowSize;
				ps.setInt(1, start);
				ps.setInt(2, end);
				
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
		//총페이지
		public int reserveShareAdmintotalpage() {
			int total=0;
			try {
				conn=db.getConnection();
				String sql="SELECT CEIL(COUNT(*)/10.0) "
						+ "FROM reserve_info_share";
				ps=conn.prepareStatement(sql);
				ResultSet rs=ps.executeQuery();
				rs.next();
				total=rs.getInt(1);
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
			return total;
		}
		
		public List<ReserveShareVO> adminreserveshareFind(int page,String fd){
			List<ReserveShareVO> slist=new ArrayList<ReserveShareVO>();
			try {
				conn=db.getConnection();
				String sql="SELECT sno,skdno,srday,srtime,sinwon,TO_CHAR(sregdate,'yyyy-MM-dd hh24:mi:ss'),"
						+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid, num "
						+ "FROM (SELECT sno,skdno,srday,srtime,sinwon,sregdate, "
						+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid, rownum as num "
						+ "FROM (SELECT /*+ INDEX_DESC(reserve_info_share skd_sno_pk)*/ sno,skdno,srday,srtime,sinwon,sregdate, "
						+ "shareGetPoster(skdno),shareGetTitle(skdno),shareGetPrice(skdno),srok,sid "
						+ "FROM reserve_info_share where shareGetTitle(skdno) LIKE '%'||?||'%')) "
						+ "WHERE num BETWEEN ? AND ?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, fd);
				System.out.println(fd);
				int rowSize=10;
				int startpage=(page-1)*rowSize+1;
				int endpage=page*rowSize;
				ps.setInt(2, startpage);
				ps.setInt(3, endpage);
				
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
					slist.add(vo);
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
			return slist;
		}
		//검색 총 페이지
		public int adminreserveShareTotalpage(String fd) {
			int totalpage=0;
			try {
				conn=db.getConnection();
				String sql="SELECT CEIL(COUNT(*)/10.0) "
						+ "FROM reserve_info_share "
						+ "WHERE shareGetTitle(skdno) LIKE '%'||?||'%'";
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.disConnection(conn, ps);
			}
				return totalpage;
			}
}