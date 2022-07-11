package com.korea.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.korea.dto.MemberDTO;

public class MemberDAO {
	
	
		//DB 연결
		private String url ="jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "book_ex";
		private String pw = "1234";
		
		private Connection conn=null;
		private PreparedStatement pstmt =null;
		private ResultSet rs=null;
		
		//싱글톤 패턴
		private static MemberDAO instance;
		public static MemberDAO getInstance() {
			if(instance==null);
				instance=new MemberDAO();
			return instance;
		}
		
		private MemberDAO() {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection(url,id,pw);
				System.out.println("DBConnected...");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		//Insert 함수
		public boolean insert(MemberDTO dto) {
			try {
				pstmt=conn.prepareStatement("insert into tbl_member values(?,?,?,?,?)");
				pstmt.setString(1,dto.getEmail());
				pstmt.setString(2,dto.getPwd());
				pstmt.setString(3,dto.getAddr1());
				pstmt.setString(4,dto.getAddr2());
				pstmt.setInt(5,dto.getGrade());
				int result = pstmt.executeUpdate();
				if(result>0) { //행이 추가가 된다면!
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try{pstmt.close();}catch(Exception e1) {e1.printStackTrace();}
			}
			
			return false;
			
			
		}
		
		//Login.jsp에서 로그인 정보 입력시 
		//나의 정보 조회
		public MemberDTO Select(String email) 
		{
			MemberDTO dto = new MemberDTO(); //DTO 객체 생성(DB에 계정 정보 담기 위해)
			try {
				pstmt=conn.prepareStatement("select * from tbl_member where email=?");
				pstmt.setString(1, email);
				
				rs = pstmt.executeQuery(); //실행한 결과물이 rs에 담긴다.
				
				if(rs.next())//한 행씩 넘어가며 확인하기!
				{
					dto.setEmail(rs.getString("email"));
					dto.setPwd(rs.getString("pwd"));	//계정정보 Get 해서 일치하는지 확인
					dto.setAddr1(rs.getString("addr1"));
					dto.setAddr2(rs.getString("addr2"));
					dto.setGrade(rs.getInt("grade"));
					return dto;
				}
					
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {rs.close();} catch (Exception e2) {e2.printStackTrace();}
				try {pstmt.close();} catch (Exception e2) {e2.printStackTrace();}
				
			}
			return null;
		}
		
//		public MemberDTO InfoSelect(String email) 
//		{
//			MemberDTO dto = new MemberDTO();
//			
//			try {
//				pstmt=conn.prepareStatement("select * from tbl_member email=?");
//				pstmt.setString(1, email);
//				
//				rs = pstmt.executeQuery();
//				
//				if(rs.next()) {
//					dto.setEmail(rs.getString("email"));
//					dto.setAddr1(rs.getString("addr1"));
//					dto.setAddr2(rs.getString("addr2"));
//					return dto;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}finally {
//				try {rs.close();} catch (Exception e2) {e2.printStackTrace();}
//				try {pstmt.close();} catch (Exception e2) {e2.printStackTrace();}
//				
//			}
//			
//			return null;
//			
//		}
		
		public boolean Update(MemberDTO dto) {
			
			try {
				
			
				
				pstmt=conn.prepareStatement("update tbl_member set addr1=?, addr2=?, grade=?, pwd=? where email=?");
				pstmt.setString(1, dto.getAddr1());
				pstmt.setString(2, dto.getAddr2());
				pstmt.setInt(3, dto.getGrade());
				pstmt.setString(4, dto.getPwd());
				pstmt.setString(5, dto.getEmail());
				
				int result = pstmt.executeUpdate();
				
				if(result>0) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
}
