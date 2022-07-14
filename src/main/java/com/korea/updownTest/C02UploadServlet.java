package com.korea.updownTest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload1")
@MultipartConfig //멀티파트 리퀘스트 기본 설정
(
		fileSizeThreshold=1024*1024*10,  //10Mb
		maxFileSize=1024*1024*50,		 //50Mb
		maxRequestSize=1024*1024*100	 //100Mb
)


public class C02UploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//request로부터 파일정보(Part)를 가져옴 "test"는 폼에 지정된 name
		
		for(Part part : req.getParts())
		{
			System.out.println("Part name : "+part.getName());
			System.out.println("File Size : "+part.getSize()+" byte");
			System.out.println("Header : "+part.getHeaderNames());
			System.out.println("content-disposition : "+part.getHeader("content-disposition"));
			System.out.println("FILENAME : "+getFileName(part));
			System.out.println("---------------------");
		}
	}
	
	//DAO 저장용 함수 생성
	private String getFileName(Part part) {
		
		String contentDisp=part.getHeader("content-disposition");
		String[] arr = contentDisp.split(";"); //배열화
		
		String Filename=arr[2].substring(11,arr[2].length()-1);
		return Filename;
	}
	
}
