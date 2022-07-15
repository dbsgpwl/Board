package com.korea.updownTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download.do")
public class C04DownloadServlet extends HttpServlet{

	@Override //doGet 매서드 활용하여 파일 다운로드 받기
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		//1 경로설정
		String downdir = "c://upload"; //email /date
		String filename = req.getParameter("filename");
		String filepath = downdir+File.separator+filename; //File.separator : 구분자 -> "/" 대체 가능
		
		System.out.println("FILENAME : "+filename);
		//2 헤더설정
		resp.setContentType("application/octet-stream"); //Content-Type 를 application/octet-stream 로 설정
		
		//3 문자셋 설정
		filename=URLEncoder.encode(filename,"utf-8").replaceAll("\\+", "%20");
		resp.setHeader("Content-Disposition", "attachment; fileName="+filename); //fileName="+filename : 다운로드시 적용할 파일 이름
		
		//4 스트림 설정(다운로드 코드)
		byte[] buffer = new byte[4096]; //4byte안의 데이터를 받아서 브라우저에 표시함 //버퍼공간(파일로부터 서블릿으로 받아올 단위공간
		FileInputStream fin = new FileInputStream(filepath);	//입력 스트림(파일->서블릿)
		ServletOutputStream sout = resp.getOutputStream();		//출력 스트림(서블릿(서버) -> 브라우저)
		
		int read;		//read 시 더이상 받을게 있나없나 확인용 //바이트수가 read에 저장됨
		while(true) {
			read = fin.read(buffer,0,buffer.length);	//파일->서블릿으로 buffer공간의
														//0 idx부터 buffer의 길이(buffer.length)만큼 담기
														//read에는 읽어들인 byte수가 저장된다
														//만약에 더 읽을게 없으면 -1이 저장된다.	->반복문 벗어남
		
				if(read==-1) {							//파일로부터 가져올게 없다면
						
					break;								//반복문 종료				
				}
		
				sout.write(buffer,0,read);				//buffer안의 데이터를 0 idx부터 읽어들인 바이트 수(read) //buffer통해 읽어들인 바이트 수만큼 브라우저로 전송
														//만큼 브라우저방향으로 전송
		}
		sout.flush();									//브라우저 방향으로 데이터 전송에 사용된 버퍼 공간 초기화
		sout.close();									//출력 스트림 종료
		fin.close();									//입력 스트림 종료
	}
}
