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

@WebServlet("/ReqBus.Info")
public class C01RequestBusInfo extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json; charset=utf-8");
		
		//파라미터 받기
		String serviceKey="x65exaOZS61VnLQHhxKXRSwuxWKWsffgQAUDaMD3d08VgH1DbGzxGci333aObjILv0op2HesJFJicCYrWW5fww%3D%3D";
		String numOfRows="100";
		String pageNo="1";
		String _type="json";
		String depTerminalId=req.getParameter("depTerminalId");	//출발터미널ID
		String arrTerminalId=req.getParameter("arrTerminalId");	//목적지터미널ID
		String depPlandTime=req.getParameter("depPlandTime");	//출발날짜
		depPlandTime=depPlandTime.replaceAll("-", "");
		String busGradeId=req.getParameter("busGradeId");		//버스등급
		
		//경로 설정
		String addr="http://apis.data.go.kr/1613000/ExpBusInfoService/getStrtpntAlocFndExpbusInfo?"
				+"serviceKey="+serviceKey
				+"&depTerminalId="+depTerminalId
				+"&arrTerminalId="+arrTerminalId
				+"&depPlandTime="+depPlandTime
				+"&busGradeId="+busGradeId
				+"&numOfRows="+numOfRows
				+"&pageNo="+pageNo
				+"&_type=json";
		
		//System.out.println(addr);
		
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

