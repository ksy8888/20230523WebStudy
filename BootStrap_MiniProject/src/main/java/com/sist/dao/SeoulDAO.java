package com.sist.dao;
import java.util.*;
import java.sql.*;
import com.sist.dbcom.*;

public class SeoulDAO {
	private String[] tables = {
		"",	
		"seoul_location",
		"seoul_nature",
		"seoul_shop",
	};
	private Connection conn;
	private PreparedStatement ps;
	private CreateDataBase db =new CreateDataBase();
	private static SeoulDAO dao;
	
	//1. 기능
	public List<SeoulVO> seoulListData(int type) {
		List<SeoulVO> list = new ArrayList<SeoulVO>();
		
		try {
			conn = db.getConnection();
			String sql="SELECT no,title,poster,rownum "
						+"FROM "+ tables[type]
						+" WHERE rownum<=20";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SeoulVO vo = new SeoulVO();
				vo.setNo(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				
				list.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}
		
		
		return list;
	}
	//싱글톤
	public static SeoulDAO newInstance() {
		if(dao==null)
			dao = new SeoulDAO();
		return dao;
	}
	//2. 총페이지 구하기
	public int seoulTotalpage(int type) {
		int total = 0;
		
		try {
			conn = db.getConnection();
			String sql = "SELECT CEIL(COUNT(*)/12.0) "
					+ "FROM "+ tables[type];
			//ps.setSring(1,tables[type])
			//FROM 'seoul_location'
			/*
			 	INSERT INTO table_name VALUES(?,?)...
			 	홍길동 남자 집어넣을 시
			 	=> INSERT INTO table_name VALUES('홍길동','남자');
			 	ps.setString(1, "홍길동") >> setString썼을 때, 작은따옴표''자동으로 들어감
			 	==> FROM 절에는 문자열결합만. ? 못씀
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.disConnection(conn, ps);
		}

		return total;
	}
	
	//3. 상세보기
	public SeoulVO seoulDetailData(int no, int type) {
		SeoulVO vo = new SeoulVO();
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		return vo;
	}
}
