package com.korea.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

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
			
			//
			public List<BoardDAO> Select(int start, int end){
				try {
							String sql=
				            "select rn, no, title, content, writer, regdate,pwd,count,ip,filename,filesize"
				            + "from"
				            + "("
				            + "    select /*+ INDEX_DESC (tbl_board PK_NO) */"
				            + "    rownum rn, no, title, content, writer, regdate,pwd,count,ip,filename,filesize"
				            + "    from tbl_board where rownum <= 10\r\n"
				            + ")"
				            + "where rn>1;";
							
							pstmt = conn.prepareStatement(sql);
							pstmt.setInt(1,end);
							pstmt.setInt(2, start);
							rs=pstmt.executeQuery();
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					
				}
				
				return null;
				
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