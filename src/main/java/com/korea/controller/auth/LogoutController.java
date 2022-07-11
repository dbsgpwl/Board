package com.korea.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.filter.authfilter;

public class LogoutController implements SubController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		session.invalidate(); //session 제거 
		
		try {
			
			//재접속시 한번은 session으로 부터 grade를 꺼내지 않는다
			
			
			
			req.setAttribute("MSG","로그아웃완료");
			req.getRequestDispatcher("/").forward(req, resp);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
