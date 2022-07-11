package com.korea.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



public class authfilter implements Filter{
	
	//Url(키)에 권한(값)부여
	//Key : URL, Value : Grade
	//pageGradeMap : Grade 정보 저장
	Map<String,Integer> pageGradeMap = new HashMap();
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		pageGradeMap.put("/Board/list.do", 0); //모두 이용 가능
		pageGradeMap.put("/Board/post.do", 1); //일반 계정 권한 이상
		
		pageGradeMap.put("/Notice/list.do", 0); //모두 이용 가능
		pageGradeMap.put("/Notice/post.do", 2); //관리자 등급 권한만 작성 가능
	}

	@Override			//HTTPServlet -> ServletRequest(최상위)
	//필터와 매핑된 URL에 요청이 들어올때마다 doFilter()가 호출
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
			//Request 전 처리
			System.out.println("======= Filter 처리 (Request 전)======");
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html; charset=UTF-8");
		
		
				//ServletRequest를 HttpServletRequest로 다운 캐스팅!
				HttpServletRequest request = (HttpServletRequest)req; //다운캐스팅을 해야 웹에 있는 객체 즉, 함수 사용 가능
				
				//Session을 통해서 grade 가져오기
				HttpSession session = request.getSession();
				
				int usergrade =0; //기본권한 0
				if(session.getAttribute("grade")!=null) //처음 접속이 아닐 경우!(session에 grade가 담겨있음)
					 usergrade=(Integer)session.getAttribute("grade");
				//System.out.println("UserGrade: "+usergrade);
				
				//URL pageGrade 확인
				String URL = request.getRequestURI(); 
				//System.out.println("Filter's URL : "+URL);
				
				int pagegrade=0; //기본 권한
				if(pageGradeMap.get(URL)!=null)
					pagegrade=pageGradeMap.get(URL);
				//System.out.println("PageGrade: "+pagegrade);
				
				//게스트 계정이 1이상의 page로 접근 불가
				if(usergrade==0 && pagegrade>=1) {
					//예외처리코드
					throw new ServletException("로그인이 필요한 페이지입니다.");
				}
				
				//관리자 권한 필요
				if(usergrade<2 && pagegrade==2) {
					throw new ServletException("페이지에 접근할 권한이 없습니다.");
				}
		
	
		
		chain.doFilter(req,resp); 
		
		//Response 전 처리
		System.out.println("======= Filter 처리 (Response 전)======");
		
		
		
	}


	
	
}
