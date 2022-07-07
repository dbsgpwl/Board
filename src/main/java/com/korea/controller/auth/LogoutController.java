package com.korea.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;

public class LogoutController implements SubController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		session.invalidate(); //session 제거 
		
		try {
			req.setAttribute("MSG","로그아웃완료");
			req.getRequestDispatcher("/").forward(req, resp);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
