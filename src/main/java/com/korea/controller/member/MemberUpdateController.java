package com.korea.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.MemberDTO;
import com.korea.service.MemberService;

public class MemberUpdateController implements SubController {
// 나의정보 주소 수정하기
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		MemberService service = MemberService.getInstance();
		
		//파라미터 받기
		String flag = req.getParameter("flag");
		
		
		try {
			//입력값 확인
			if(flag==null)//myinfo.jsp에서 요청받음
			{
				req.getRequestDispatcher("/WEB-INF/member/password.jsp").forward(req, resp);
			}
			else //password.jsp에서 요청받음
			{
				//패스워드 검증 일치하다면 정보수정처리
				String pwd=req.getParameter("pwd");
				HttpSession session = req.getSession(); //세션 객체 받아와서 email에 Service 실행하여 계정정보를 가져옴
				String email=(String)session.getAttribute("email");
				MemberDTO dto = service.MemberSearch(email);
				
				//서비스 실행
				if(service.passwordEncoder.checkpw(pwd, dto.getPwd())){ //입력한 원문 pwd 와 dto에 저장된 암호화된 pwd 일치여부확인
					//패스워드 일치한 경우
					
					//->수정된 값 파라미터 받기
					String addr1= req.getParameter("addr1");
					String addr2= req.getParameter("addr2");
					
					//수정된 값 dto에 다시 담기 (set!)
					dto.setAddr1(addr1);
					dto.setAddr2(addr2);
					
					//비밀번호 변경하기
					String newpwd = req.getParameter("newpwd");
					newpwd=service.passwordEncoder.hashpw
					(newpwd, 
							service.passwordEncoder.gensalt()
							); 	//암호화된 pw servic로 전달
					
					dto.setPwd(newpwd);
					//->dto 단위로 담아서 service 함수로 전달
					service.MemberUpdate(dto);
					
					//View 이동
					req.setAttribute("dto", dto); //dto가 초기화되므로 수정된 값을 dto에 다시 setAttribute!
					req.getRequestDispatcher("/WEB-INF/member/myinfo.jsp").forward(req, resp);
					
				}else {
					//패스워드 불일치 경우
					req.setAttribute("MSG", "패스워드가 일치하지 않습니다.");
					req.getRequestDispatcher("/WEB-INF/member/password.jsp").forward(req, resp);
				}
				
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
