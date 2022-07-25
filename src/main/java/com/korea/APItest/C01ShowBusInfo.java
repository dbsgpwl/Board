package com.korea.APItest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ShowBus.Info")
public class C01ShowBusInfo extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=utf-8");
		
		String addr ="http://apis.data.go.kr/1613000/ExpBusInfoService/getExpBusTrminlList?serviceKey=x65exaOZS61VnLQHhxKXRSwuxWKWsffgQAUDaMD3d08VgH1DbGzxGci333aObjILv0op2HesJFJicCYrWW5fww%3D%3D&pageNo=1&numOfRows=230&_type=json";
		
		URL url = new URL(addr);	//주소 URL을 자바로 가져오는 스트림 생성

		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8")); //보조스트림/ InputStreamReader:1->2바이트로 변환하여 받아오는 스트림 /BufferedReader : 버퍼공간을 추가하여 빠른 전달 //기본 스트림의 인자를 보조스트림으로 전달하여 
		StringBuffer sb = new StringBuffer();	//json방식으로 받아오는 데이터를 스트링 버퍼에 계속 저장
		
		String str = null;
		
		while(true) {
			str=br.readLine();	//보조스트림에서 readLine(라인 단위로 가지고 옴)
			if(str==null)	//반복문을 벗어남
				break;
			sb.append(str);	//str을 sb(스트링 버퍼)에 append(추가)
			
		}
		br.close();
		//System.out.println(sb.toString()); //받아온 데이터 콘솔창에서 확인
		//getWriter : 아웃 내장 객체
		resp.getWriter().write(sb.toString());
	}
	
	
}
