package com.korea.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.service.MemberService;

public class MemberInfoController implements SubController {
	
	MemberService service = MemberService.getInstance();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("💛=== 나의 정보 ===💜");
		
		//view로 이동
		try {
			
			//session 객체에서 email을 꺼내옴
			HttpSession session = req.getSession();
			
			req.getRequestDispatcher("/WEB-INF/member/myinfo.jsp")
			.forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
