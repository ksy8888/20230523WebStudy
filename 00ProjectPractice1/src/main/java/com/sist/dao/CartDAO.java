package com.sist.dao;

import java.util.*;
import java.sql.*;
import com.sist.common.*;
import com.sist.vo.*;
public class CartDAO {
	private Connection conn; // 데이터소스만 넘김 (driver,username,password,url)
	private PreparedStatement ps; // SQL문장만 넘김 ==> ResultSet => VO,List  // MyBatis에서
	private CreateDataBase db=new CreateDataBase();
	private static CartDAO dao; //Spring => Sington이 기본
	
	public static CartDAO newInstance() {
		if(dao==null)
			dao=new CartDAO();
		return dao;
	}
	//pdsno ==> 제품번호
	public void cartInsert(CartVO vo) {
		try {
			conn=db.getConnection();
			String sql="SELECT count(*) "
					 + "FROM cart "
					 + "WHERE pdsno=? AND issale<>1";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getPdsno());
			ResultSet rs=ps.executeQuery();
			rs.next();
			int count=rs.getInt(1);
			rs.close();
			
			if(count!=0) {
				sql="UPDATE cart SET "
				  + "amount=amount+"+vo.getAmount()
				  + " WHERE pdsno=? AND id=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, vo.getPdsno());
				ps.setString(2, vo.getId());
				ps.executeUpdate();
			}else {
				sql="INSERT INTO cart(cart_no,pdsno,amount,price,id) "
						 + "VALUES(c_cno_seq.nextval,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, vo.getPdsno());
				ps.setInt(2, vo.getAmount());
				ps.setInt(3, vo.getPrice());
				ps.setString(4, vo.getId());
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	
	public List<CartVO> mypageCartListData(String id){
		List<CartVO> list=new ArrayList<CartVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT cart_no,pdsno,"
					 + "(SELECT title FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as title,"
					 + "(SELECT poster FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as poster,"
					 + "(SELECT PRICED_SALE FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as price,"
					 + "amount,regdate,issale,ischeck,price "
					 + "FROM cart c "
					 + "WHERE id=? AND issale<>1 "//구매가 안된내역
					 + "ORDER BY cart_no DESC";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				CartVO vo=new CartVO();
				vo.setCart_no(rs.getInt(1));
				vo.setPdsno(rs.getInt(2));
				vo.setTitle(rs.getString(3));
				vo.setPoster(rs.getString(4));
				vo.setPRICED_SALE(rs.getString(5));
				vo.setAmount(rs.getInt(6));
				vo.setRegdate(rs.getDate(7));
				vo.setIssale(rs.getInt(8));
				vo.setIscheck(rs.getInt(9));
				vo.setPrice(rs.getInt(10));
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
	
	public List<CartVO> adminpageCartListData(){
		List<CartVO> list=new ArrayList<CartVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT cart_no,pdsno,"
					 + "(SELECT title FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as title,"
					 + "(SELECT poster FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as poster,"
					 + "(SELECT	PRICED_SALE FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as price,"
					 + "amount,regdate,issale,ischeck,price "
					 + "FROM cart c "
					 + "WHERE id=? AND issale<>1 "//구매가 안된내역
					 + "ORDER BY cart_no DESC";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				CartVO vo=new CartVO();
				vo.setCart_no(rs.getInt(1));
				vo.setPdsno(rs.getInt(2));
				vo.setTitle(rs.getString(3));
				vo.setPoster(rs.getString(4));
				vo.setPRICED_SALE(rs.getString(5));
				vo.setAmount(rs.getInt(6));
				vo.setRegdate(rs.getDate(7));
				vo.setIssale(rs.getInt(8));
				vo.setIscheck(rs.getInt(9));
				vo.setPrice(rs.getInt(10));
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
	// AdminOk
	public void cartBuy(int no) {
		try {
			conn=db.getConnection();
			String sql="UPDATE cart SET "
					 + "issale=1 "
				 	 + "WHERE cart_no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	// 구매취소
	public void cartCancel(int no) {
		try {
			conn=db.getConnection();
			String sql="DELETE FROM cart "
				 	 + "WHERE cart_no=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
	}
	
	public List<CartVO> mypageCartBuyListData(String id){
		List<CartVO> list=new ArrayList<CartVO>();
		try {
			conn=db.getConnection();
			String sql="SELECT cart_no,pdsno,"
					 + "(SELECT title FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as title,"
					 + "(SELECT poster FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as poster,"
					 + "(SELECT PRICED_SALE FROM PRODUCT_DETAIL WHERE pdno=c.pdsno) as price,"
					 + "amount,regdate,issale,ischeck,price "
					 + "FROM cart c "
					 + "WHERE id=? AND issale=1 "
					 + "ORDER BY cart_no DESC";
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next()) {
				CartVO vo=new CartVO();
				vo.setCart_no(rs.getInt(1));
				vo.setPdsno(rs.getInt(2));
				vo.setTitle(rs.getString(3));
				vo.setPoster(rs.getString(4));
				vo.setPRICED_SALE(rs.getString(5));
				vo.setAmount(rs.getInt(6));
				vo.setRegdate(rs.getDate(7));
				vo.setIssale(rs.getInt(8));
				vo.setIscheck(rs.getInt(9));
				vo.setPrice(rs.getInt(10));
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
}