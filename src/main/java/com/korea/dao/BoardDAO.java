package com.korea.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.korea.dto.BoardDTO;

public class BoardDAO {
	//DB 연결
			private String url ="jdbc:oracle:thin:@localhost:1521:xe";
			private String id = "book_ex";
			private String pw = "1234";
			
			private Connection conn=null;
			private PreparedStatement pstmt =null;
			private ResultSet rs=null;
			
			//싱글톤 패턴
			private static BoardDAO instance;
			public static BoardDAO getInstance() {
				if(instance==null);
					instance=new BoardDAO();
				return instance;
			}
			
			private BoardDAO() {
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					conn=DriverManager.getConnection(url,id,pw);
					System.out.println("DBConnected...");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			//시작페이지, 끝페이지번호 받아서 조회
			public List<BoardDTO> Select(int start, int end)
			{
				
				ArrayList<BoardDTO> list = new ArrayList();
				BoardDTO dto = null; //참조변수
				try {
					
					String sql=
							"select rn, no, title, content, writer, regdate,pwd,count,ip,filename,filesize"
							+ " from"
							+ "("
							+ "    select /*+ INDEX_DESC (tbl_board PK_NO) */"
							+ "    rownum rn, no, title, content, writer, regdate,pwd,count,ip,filename,filesize"
							+ "    from tbl_board where rownum<=?"
							+ ")"
							+ " where rn>=?";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, end);//마지막 rownum
					pstmt.setInt(2, start);//시작 rownum
					rs=pstmt.executeQuery();
					
					while(rs.next())
					{
						dto=new BoardDTO();
						dto.setNo(rs.getInt("no"));
						dto.setTitle(rs.getString("title"));
						dto.setContent(rs.getString("content"));
						dto.setWriter(rs.getString("writer"));
						dto.setRegdate(rs.getString("regdate"));
						dto.setPwd(rs.getString("pwd"));
						dto.setIp(rs.getString("ip"));
						dto.setFilename(rs.getString("filename"));
						dto.setFilesize(rs.getString("filesize"));
						dto.setCount(rs.getInt("count"));
						list.add(dto);
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}finally{
					try {rs.close();}catch(Exception e) {e.printStackTrace();}//자원제거
					try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				return list; //리턴 : Service
			}

			
			//모든 게시물 개수 조회
			public int getTotalCount() { //Count 로 게시물 개수 조회
				int result=0;
				try {
					pstmt = conn.prepareStatement("select count(*) from tbl_board"); //500개
					rs = pstmt.executeQuery();
					rs.next(); 
					result = rs.getInt(1); //전체 게시물 건수 받아오기
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {rs.close();}catch(Exception e) {e.printStackTrace();}//자원제거
					try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
				}
				return result;
				
			}
			
}


//try {
//	
//} catch (Exception e) {
//	e.printStackTrace();
//}finally {
//	
//}
//
//return null;