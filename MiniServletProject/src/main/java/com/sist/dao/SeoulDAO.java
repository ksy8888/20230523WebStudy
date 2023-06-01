package com.sist.dao;
import java.util.*;
import java.sql.*;

public class SeoulDAO {
	//연결 객체
		private Connection conn;
		//송수신
		private PreparedStatement ps;
		//오라클 URL 주소 설정
		private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
		//싱글턴
		private static SeoulDAO dao;
		//1. 드라이버 등록 => 한번 수행 => 시작과 동시에 한번 수행 , 멤버변수 초기화: 생성자
		public SeoulDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				// ClassNotFoundException => 체크 예외처리 => 반드시 예외처리 한다
				// java.io, java.net , java.sql => 체크 예외처리
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//2. 오라클 연결
		public void getConnection() {
			try {
				conn = DriverManager.getConnection(URL, "hr", "happy");
				//conn hr/happy => 명령어를 오라클 전송
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//3. 오라클 해제
		public void disConnection()  {
			try {
				if(ps!=null) ps.close();	// 통신이 열려있으면 닫는다
				if(conn!=null) conn.close();
				// exit => 오라클 닫기	
			} catch (Exception e) { }
		}
		//4. 싱글턴설정 => static은 메모리 공간이 1개만 가지고 있다
		// 메모리누수 현상을 방지 ...
		// DAO => new를 이용해서 생성 => 사용하지 않는 DAO가 오라클을 연결하고 있다
		// 싱글턴은 데이터베이스에서는 필수 조건
		public static SeoulDAO newInstance() {
			if(dao == null)
				dao = new SeoulDAO();
			return dao;
		}
		
		//5. 기능
		// 목록 => SQL(인라인뷰 => 페이징 기법)
		public List<SeoulVO> seoulListData(int page) {
			List<SeoulVO> list = new ArrayList<SeoulVO>();
			
			try {
				getConnection();
				String sql ="SELECT no,title,poster,num "
							+"FROM (SELECT no,title,poster,rownum as num "
							+"FROM (SELECT no,title,poster "
							+"FROM seoul_location ORDER BY no ASC)) "
							+"WHERE num BETWEEN ? AND ?";
				// rownum => 가상 컬럼 (오라클에 지원)
				// 단점 => 중간에 데이터를 추출 할 수 없다 => Top-N
				// SQL문장 전송
				ps = conn.prepareStatement(sql);
			
				int rowSize=12;
				int start =(rowSize*page)-(rowSize-1);
				int end = rowSize*page;
				
				// ?에 값을 채운다 => IN,OUT 입출력 에러
				ps.setInt(1, start);
				ps.setInt(2, end);
				
				
				// 실행요청
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					SeoulVO vo = new SeoulVO();
					vo.setNo(rs.getInt(1));
					vo.setTitle(rs.getString(2));
					vo.setPoster(rs.getString(3));
					
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
		//자연
		public List<SeoulVO> SeoulNatureData(int page) {
			List<SeoulVO> list2 = new ArrayList<SeoulVO>();
			
			try {
				getConnection();
				String sql = "SELECT no,title,poster,num "
							+"FROM (SELECT no,title,poster,rownum as num "
							+"FROM (SELECT no,title,poster "
							+"FROM seoul_nature ORDER BY no ASC)) "
							+"WHERE num BETWEEN ? AND ?";
				
				ps = conn.prepareStatement(sql);
				
				//페이지 나누기
				int rowSize=12;	//한페이지에 12개
				int start = (rowSize*page)-(rowSize-1);
				int end = rowSize*page;	//12 24 36
				
				//?값채우기			
				ps.setInt(1, start);
				ps.setInt(2, end);			
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					SeoulVO vo1 = new SeoulVO();
					vo1.setNo(rs.getInt(1));
					vo1.setTitle(rs.getString(2));
					vo1.setPoster(rs.getString(3));
					
					list2.add(vo1);
				}			
				rs.close();
				
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				disConnection();
			}

			return list2;
			
		}
}
