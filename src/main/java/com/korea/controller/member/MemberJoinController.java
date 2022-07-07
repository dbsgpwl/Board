package com.korea.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.SubController;
import com.korea.dto.MemberDTO;
import com.korea.service.MemberService;

public class MemberJoinController implements SubController{
	private MemberService service = MemberService.getInstance(); //서비스-컨트롤러 
	
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) { //매개변수들을 각각의 컨트롤러에 처리 
		System.out.println("💛===MemberJoin Controller===💜");
		
		String flag=req.getParameter("flag");
		
		try {
			if(flag==null) //Login.jsp => 회원가입버튼 누름
			{
				req.getRequestDispatcher("/WEB-INF/member/join.jsp").forward(req, resp);
			}
			else
			{
				//1 파라미터 받기
				String email = req.getParameter("email");
				String pwd = req.getParameter("pwd");
				String addr1 = req.getParameter("addr1");
				String addr2 = req.getParameter("addr2");
				
				System.out.println(email+","+pwd+","+addr1+","+addr2);
			
			
				//2 입력값 검증
				//3 서비스 실행
				MemberDTO dto = new MemberDTO();
				dto.setEmail(email);
				dto.setPwd(pwd);
				dto.setAddr1(addr1);
				dto.setAddr2(addr2);
				
				boolean result = service.MemberInsert(dto);
				//4 View 이동(Login.jsp로)
				resp.sendRedirect("/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
