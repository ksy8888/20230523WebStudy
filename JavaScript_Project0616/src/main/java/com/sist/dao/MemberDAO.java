package com.sist.dao;
import java.util.*;
import java.sql.*;
public class MemberDAO {
	
	//연결 객체
		private Connection conn;
		//송수신
		private PreparedStatement ps;
		//오라클 URL 주소 설정
		private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
		//싱글턴
		private static MemberDAO dao;
		//1. 드라이버 등록 => 한번 수행 => 시작과 동시에 한번 수행 , 멤버변수 초기화: 생성자
		public MemberDAO() {
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
		public static MemberDAO newInstance() {
			if(dao == null)
				dao = new MemberDAO();
			return dao;
		}
		
		//5. 기능(우편번호검색)
		public List<ZipcodeVO> postfind(String dong) {
			List<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
			
			try {
				getConnection();
				String sql ="SELECT zipcode,sido,gugun,dong, NVL(bunji,' ') "
							+"FROM zipcode "
							+"WHERE dong LIKE '%'||?||'%'";
				ps=conn.prepareStatement(sql);
				ps.setString(1, dong);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					ZipcodeVO vo = new ZipcodeVO();
					vo.setZipcode(rs.getString(1));
					vo.setSido(rs.getString(2));
					vo.setGugun(rs.getString(3));
					vo.setDong(rs.getString(4));
					vo.setBunji(rs.getString(5));
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
		
		public int postfindCount(String dong) { 	//검색결과가 있는지 없는지 체크
			int count=0;
			
			try {
				getConnection();
				String sql ="SELECT COUNT(*) "
							+"FROM zipcode "
							+"WHERE dong LIKE '%'||?||'%'";
				ps=conn.prepareStatement(sql);
				ps.setString(1, dong);
				ResultSet rs = ps.executeQuery();
				rs.next();
				count=rs.getInt(1);
				rs.close();
			
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disConnection();
			}
			return count;
		}

}
