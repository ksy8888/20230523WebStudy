package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.common.CreateDataBase;
import com.sist.vo.*;

public class FoodDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static FoodDAO dao;
	private static String tt[]= {"","한식","양식","일식","중식","디저트"};
	public static FoodDAO newInstance()
	{
		if(dao==null)
			dao=new FoodDAO();
		return dao;
	}
	
	// random page
	public List<FoodVO> randomFoodList()
	{
		List<FoodVO> flist=new ArrayList<FoodVO>();
		try
		{
			conn=db.getConnection();
			// 메인페이지 더보기 거기에 랜덤하게 출력해주기
			String sql="SELECT poster,title,score,price,fdno "
					+ "FROM (SELECT poster,title,score,price,fdno FROM food_detail "
					+ "ORDER BY DBMS_RANDOM.RANDOM)"
					+ "WHERE rownum <=4 ";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				String poster= rs.getString(1);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setPrice(rs.getString(4));
				vo.setFdno(rs.getInt(5));
				flist.add(vo);
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
	
		return flist;
	}
	public List<FoodVO> foodListData(int page,int type)
	{
		List<FoodVO> flist=new ArrayList<FoodVO>();
		try
		{
			
			conn=db.getConnection();
			
			
			String sql="SELECT poster,title,score,price,fdno,num "
					+ "FROM (SELECT poster,title,score,price,fdno,rownum as num "
					+ "FROM (SELECT /*+INDEX_ASC(food_detail fd_fdno_pk)*/poster,title,score,price,fdno "
					+ "FROM food_detail WHERE type LIKE '%'||?||'%')) "
					+ "WHERE num BETWEEN ? AND ?";
			
		
				ps=conn.prepareStatement(sql);
				int rowSize=12;
				int start=(rowSize*page)-(rowSize-1);
				int end=rowSize*page;
				
				ps.setString(1, tt[type]);
				
				ps.setInt(2, start);
				ps.setInt(3, end);
				
				ResultSet rs=ps.executeQuery();
				while(rs.next())
				{
					FoodVO vo=new FoodVO();
					String poster= rs.getString(1);
					poster=poster.substring(0,poster.indexOf("^"));
					poster=poster.replace("#", "&");
					vo.setPoster(poster);
					vo.setTitle(rs.getString(2));
					vo.setScore(rs.getDouble(3));
					vo.setPrice(rs.getString(4));
					vo.setFdno(rs.getInt(5));
					flist.add(vo);
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
		return flist;
	}
	public int foodTotalPage(int type)
	{
		int totalpage=0;
		String sql="";
		try
		{
			conn=db.getConnection();
			
			sql="SELECT CEIL(COUNT(*)/12.0) FROM food_detail WHERE type LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			ps.setString(1, tt[type]);
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
	public List<FoodVO> findFood(int page,String fd)
	{
		List<FoodVO> list=new ArrayList<>();
		try
		{
			conn=db.getConnection();
			String sql="SELECT poster,title,score,price,fdno,num "
					+ "FROM (SELECT poster,title,score,price,fdno,rownum as num "
					+ "FROM (SELECT /*+INDEX_ASC(food_detail fd_fdno_pk)*/poster,title,score,price,fdno "
					+ "FROM food_detail WHERE title LIKE '%'||?||'%' or "
					+ "address  LIKE '%'||?||'%' or "
					+ "type  LIKE '%'||?||'%' or "
					+ "menu  LIKE '%'||?||'%'))"
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int start=(rowSize*page)-(rowSize-1);
			int end=rowSize*page;
			ps.setString(1, fd);
			ps.setString(2, fd);
			ps.setString(3, fd);
			ps.setString(4, fd);
			
			ps.setInt(5, start);
			ps.setInt(6, end);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				FoodVO vo=new FoodVO();
				String poster= rs.getString(1);
				poster=poster.substring(0,poster.indexOf("^"));
				poster=poster.replace("#", "&");
				vo.setPoster(poster);
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setPrice(rs.getString(4));
				vo.setFdno(rs.getInt(5));
				list.add(vo);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return list;
	}
	public int foodSearchTotalPage(String fd)
	{
		int totalpage=0;
		String sql="";
		try
		{
			conn=db.getConnection();
			
			sql="SELECT CEIL(COUNT(*)/12.0) FROM food_detail WHERE title LIKE '%'||?||'%' or "
					+ "address LIKE '%'||?||'%' or type  LIKE '%'||?||'%' or menu  LIKE '%'||?||'%'";
			ps=conn.prepareStatement(sql);
			
		    ps.setString(1, fd);
		    ps.setString(2, fd);
		    ps.setString(3, fd);
		    ps.setString(4, fd);
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
	public FoodVO foodDetailData(int fdno) {
		FoodVO vo=new FoodVO();
		try
		{
			conn=db.getConnection();
			String sql="SELECT * from food_detail "
					+ "WHERE fdno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, fdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
			vo.setFdno(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setScore(rs.getDouble(3));
			vo.setPoster(rs.getString(4));
			vo.setHit(rs.getInt(5));
			vo.setAddress(rs.getString(6));
			vo.setTel(rs.getString(7));
			vo.setType(rs.getString(8));
			vo.setPrice(rs.getString(9));
			vo.setParking(rs.getString(10));
			vo.setMenu(rs.getString(11));
			vo.setRday(rs.getString(12));
			vo.setTime(rs.getString(13));
			rs.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.disConnection(conn, ps);
		}
		return vo;
	}
}
