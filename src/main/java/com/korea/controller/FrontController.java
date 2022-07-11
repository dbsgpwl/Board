package com.korea.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.auth.LoginController;
import com.korea.controller.auth.LogoutController;
import com.korea.controller.board.BoardListController;
import com.korea.controller.board.BoardPostController;
import com.korea.controller.member.MemberInfoController;
import com.korea.controller.member.MemberJoinController;
import com.korea.controller.member.MemberUpdateController;
import com.korea.controller.notice.NoticeListController;
import com.korea.controller.notice.NoticePostController;


//@WebServlet
public class FrontController extends HttpServlet{
	//Url정보와 서브 컨트롤러 객체를 저장하는 컬렉션 생성
	//URL : SubController객체주소
	HashMap <String,SubController> list = null;	//SubController 상위클래스 참조변수(업캐스팅)
	
	//url주소 확인하는 이유: 해당 url에 대응하는 subcontroller를 꺼내 실행하기 위해 (subcontoller를 찾기 위한 Key값)
	
	
	@Override
	public void init() throws ServletException {
		list = new HashMap();
		//회원관련
		list.put("/MemberJoin.do", new MemberJoinController());
		list.put("/MemberInfo.do", new MemberInfoController());
		list.put("/MemberUpdate.do", new MemberUpdateController());
		
		//인증관련
		list.put("/Login.do", new LoginController()); //로그인
		list.put("/Logout.do", new LogoutController()); //로그아웃
		
		//게시판관련
		list.put("/Board/list.do", new BoardListController());
		list.put("/Board/post.do", new BoardPostController());
		
		//공지사항
		list.put("/Notice/list.do", new NoticeListController());
		list.put("/Notice/post.do", new NoticePostController());
	}


	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//유니코드 한글문자 변환 : request 할 때마다 실행되어야하므로, Front request전 작동
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		String url = req.getRequestURI();
		System.out.println("url : "+url);
		SubController sub = list.get(url);
		if(sub!=null)
		{
			sub.execute(req, resp); //req,resp 내장객체 주소를 SubConroller에 함께 전달하며 SubConroller에서 처리
		}
	}

	
}
