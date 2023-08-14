package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sist.common.CreateDataBase;

import com.sist.vo.*;

public class ProductDAO {
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db=new CreateDataBase();
	private static ProductDAO dao;
	public static ProductDAO newInstance()
	{
		if(dao==null)
			dao=new ProductDAO();
		return dao;
	}
	
	// random page
	public List<ProductVO> randomProductList()
	{
		List<ProductVO> plist=new ArrayList<ProductVO>();
		try
		{
			conn=db.getConnection();
			// 메인페이지 더보기 거기에 랜덤하게 출력해주기
			String sql="SELECT poster,title,score,first_pri,pdno "
					+ "FROM (SELECT poster,title,score,first_pri,pdno FROM product_detail "
					+ "ORDER BY DBMS_RANDOM.RANDOM)"
					+ "WHERE rownum <=4 ";
			ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ProductVO vo=new ProductVO();
				
				
				vo.setPoster(rs.getString(1));
				vo.setTitle(rs.getString(2));
				vo.setScore(rs.getDouble(3));
				vo.setFirst_pri(rs.getString(4));
				vo.setPdno(rs.getInt(5));
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
	public List<ProductVO> productListData(int type,int page)
	{
		List<ProductVO> plist=new ArrayList<ProductVO>();
		String sql="";
		try
		{
			conn=db.getConnection();
			if(type==0)
			{
			sql="SELECT pdno,poster,title,score,first_pri,nvl(TO_NUMBER(REPLACE(sale,'%')),0) as sale,num "
					+ "FROM(SELECT pdno,poster,title,score,first_pri,sale,rownum as num "
					+ "FROM(SELECT/*+INDEX_ASC(product_detail pd_pdno_pk)*/pdno,poster,title,score,first_pri,sale "
					+ "FROM product_detail))"
					+ "WHERE num BETWEEN ? AND ?";
			}
			else if(type==1) // 특가 
			{
				
			 
			 sql="SELECT pdno,poster,title,score,first_pri,nvl(TO_NUMBER(REPLACE(sale,'%')),0) as sale,num "
			 		+"FROM(SELECT pdno,poster,title,score,first_pri,sale,rownum as num "
			 		+ "FROM(SELECT/*+INDEX_ASC(product_detail pd_pdno_pk)*/pdno,poster,title,score,first_pri,sale "
			 		+ "FROM product_detail WHERE nvl(TO_NUMBER(REPLACE(sale,'%')),0) >=68)) "
			 		+ "WHERE num BETWEEN ? AND ?";
			}
			else if(type==2) // 신상
			{
//				SELECT pdno,poster,title,score,first_pri,num
//				FROM(SELECT pdno,poster,title,score,first_pri,rownum as num
//				FROM(SELECT/*+INDEX_ASC(product_detail pd_pdno_pk)*/pdno,poster,title,score,first_pri 
//				FROM product_detail WHERE score=0));
			  sql="SELECT pdno,poster,title,score,first_pri,nvl(TO_NUMBER(REPLACE(sale,'%')),0) as sale,num "
			  		+"FROM(SELECT pdno,poster,title,score,first_pri,sale,rownum as num "
			  		+"FROM(SELECT/*+INDEX_ASC(product_detail pd_pdno_pk)*/pdno,poster,title,score,first_pri,sale "
			  		+"FROM product_detail WHERE score=0)) "
			  		+ "WHERE num BETWEEN ? AND ?";
			}
			else if(type==3) // 베스트 
			{
				 sql="SELECT pdno,poster,title,score,first_pri,nvl(TO_NUMBER(REPLACE(sale,'%')),0) as sale,num "
					  		+"FROM(SELECT pdno,poster,title,score,first_pri,sale,rownum as num "
					  		+"FROM(SELECT/*+INDEX_ASC(product_detail pd_pdno_pk)*/pdno,poster,title,score,first_pri,sale "
					  		+"FROM product_detail WHERE score>=4.9)) "
					  		+ "WHERE num BETWEEN ? AND ?";
			}
			ps=conn.prepareStatement(sql);
			int rowSize=20;
			int startPage=(rowSize*page)-(rowSize-1);
			int endPage=rowSize*page;
			ps.setInt(1, startPage);
			ps.setInt(2, endPage);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ProductVO vo=new ProductVO();
				vo.setPdno(rs.getInt(1));
				vo.setPoster(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setScore(rs.getDouble(4));
				vo.setFirst_pri(rs.getString(5));
				vo.setSale(rs.getString(6));
				plist.add(vo);
			}
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
	public int productTotalPage(int type)
	{
		int totalpage=0;
		String sql="";
		try
		{
			conn=db.getConnection();
			if(type==0)
			{
			sql="SELECT CEIL(COUNT(*)/20.0) FROM product_detail";	
			}
			else if(type==1)
			{
				sql="SELECT CEIL(COUNT(*)/20.0) FROM product_detail WHERE nvl(TO_NUMBER(REPLACE(sale,'%')),0) >=68";	
			}
			else if(type==2)
			{
				sql="SELECT CEIL(COUNT(*)/20.0) FROM product_detail  WHERE score=0";
			}
			else if(type==3)
			{
				sql="SELECT CEIL(COUNT(*)/20.0) FROM product_detail WHERE score>=4.9 ";
			}
			ps=conn.prepareStatement(sql);
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
	public List<ProductVO> productFindData(int page,String fd)
	{
		List<ProductVO> list=new ArrayList<ProductVO>();
		try
		{
			conn=db.getConnection();
			String sql="SELECT pdno,poster,title,score,first_pri,sale,num "
					+ "FROM(SELECT pdno,poster,title,score,first_pri,sale,rownum as num "
					+ "FROM(SELECT/*+INDEX_ASC(product_detail pd_pdno_pk)*/pdno,poster,title,score,first_pri,sale "
					+ "FROM product_detail WHERE title LIKE '%'||?||'%')) "
					+ "WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			int rowSize=12;
			int startPage=(rowSize*page)-(rowSize-1);
			int endPage=rowSize*page;
			ps.setString(1, fd);
			ps.setInt(2, startPage);
			ps.setInt(3, endPage);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				ProductVO vo=new ProductVO();
				vo.setPdno(rs.getInt(1));
				vo.setPoster(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setScore(rs.getDouble(4));
				vo.setFirst_pri(rs.getString(5));
				vo.setSale(rs.getString(6));
				list.add(vo);
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
		return list;
	}
	public int productFindTotalPage(String fd)
	{
		int totalpage=0;
		try
		{
			conn=db.getConnection();
			String sql="SELECT CEIL((SELECT COUNT(*) FROM product_detail WHERE title LIKE '%'||?||'%')/12.0) FROM recipe_d";
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
	public ProductVO productDetailData(int pdno)
	{
		ProductVO vo=new ProductVO();
		try
		{
			conn=db.getConnection();
			String sql="SELECT * FROM product_detail WHERE pdno=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, pdno);
			ResultSet rs=ps.executeQuery();
			rs.next();
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
