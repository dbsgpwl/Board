package com.korea.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.MemberDTO;
import com.korea.service.MemberService;

public class LoginController implements SubController{
	
	MemberService service = MemberService.getInstance(); //MemberService의 객체 주소 받기
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("💛===Login Controller===💜");
		
			//파라미터 받기
			String email=req.getParameter("email");
			String pwd=req.getParameter("pwd");
			//입력값 검증
		try {
			if(email==null || pwd==null)
			{
				resp.sendRedirect("/");
			}
			//서비스 실행
			//MemberService -> DAO -> email 있는지 DB로 확인(해당 email의 pw를 꺼내오기)
			MemberDTO dto = service.MemberSearch(email);
			if(dto!=null) //값을 가져왔을 때 ( 해당 email 계정이 있음! )
			{
				//pw 일치여부 확인
				//if(pwd.equals(dto.getPwd()))
				
				//암호화된 pw 일치여부 확인!
				if(service.passwordEncoder.checkpw(pwd, dto.getPwd())) //원문 pw(입력한) = DB pw(암호화) -> boolean 반환
				{
					//패스워드 일치
					
					//세션에 특정옵션 부여(예를 들면 계정의 grade를 저장한다거나~!)
					HttpSession session=req.getSession();
					session.setAttribute("grade", dto.getGrade());
					session.setAttribute("email",dto.getEmail()); //나의정보 클릭시 세션으로부터 이메일정보를 꺼내서 DAO로 전달하여 계정 전체 정보를 가져옴
					session.setMaxInactiveInterval(60*5);
					
					//View 이동
					resp.sendRedirect("/main.jsp"); //Login.jsp에서 LOGIN 클릭 시 main.jsp로 리다이렉션!
					
					
				}
				else
				{
					//패스워드 불일치
					req.setAttribute("MSG", "패스워드 불일치");
					req.getRequestDispatcher("/").forward(req,resp);
				}
				
			}
			else
			{
				//아이디 조회 실패.. 해당 아이디가 없습니다.
				req.setAttribute("MSG", "해당 아이디가 없습니다.");
				req.getRequestDispatcher("/").forward(req,resp);
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
