package com.korea.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.MemberDTO;
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
			String email = (String)session.getAttribute("email");
			//service를 이용해서 email 접속 중인 사용자의 정보를 가져옴
			MemberDTO dto = service.MemberSearch(email);
			
			//request에 dto 저장
			req.setAttribute("dto",dto);
			
			//forward 방식으로 myinfo로 이동하여 myinfo에서 참조변수 받음
			req.getRequestDispatcher("/WEB-INF/member/myinfo.jsp")
			.forward(req, resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
