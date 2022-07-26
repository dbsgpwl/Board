package com.korea.APITest;

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
		
		String addr = "http://apis.data.go.kr/1613000/ExpBusInfoService/getExpBusTrminlList?serviceKey=x65exaOZS61VnLQHhxKXRSwuxWKWsffgQAUDaMD3d08VgH1DbGzxGci333aObjILv0op2HesJFJicCYrWW5fww%3D%3D&pageNo=1&numOfRows=230&_type=json";
		
		URL url = new URL(addr);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		StringBuffer sb = new StringBuffer();
		String str = null;
		
		while(true) {
			str=br.readLine();
			if(str==null)
				break;
			sb.append(str);
		}
		br.close();
	
		System.out.println(sb.toString());
		resp.getWriter().write(sb.toString());
		
	}

	
}