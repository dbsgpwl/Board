package com.korea.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardUpdateController implements SubController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		BoardService service = BoardService.getInstance();
		
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String pwd = req.getParameter("pwd");
		String nowPage = req.getParameter("nowPage");
		
		HttpSession session = req.getSession();
		BoardDTO dto = (BoardDTO)session.getAttribute("dto");
		
		if(dto.getPwd().equals(pwd))	//읽고있는 게시물의 PW와 변경요청시 전달한 PW 일치시
		{
			//서비스함수 호출 -> DAO -> DB변경
			dto.setTitle(title);	//변경된 타이틀
			dto.setContent(content);//변경된 컨텐츠
			service.UpdateBoard(dto);//업데이트 요청 서비스
			session.setAttribute("dto", dto);//세션 객체에 읽고있는 게시물 변경 저장
			
			//Read.jsp 로 이동(no, nowPage 전달)
			try {
				String MSG="업데이트성공";
				req.setAttribute("MSG", MSG);
				req.getRequestDispatcher("/Board/read.do?no="+dto.getNo()+"&nowPage="+nowPage).forward(req,resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		else
		{
			//Read.jsp로 이동 - MSG 전달
			try {
				String MSG="패스워드불일치";
				req.setAttribute("MSG", MSG);
				req.getRequestDispatcher("/Board/read.do?no="+dto.getNo()+"&nowPage="+nowPage).forward(req,resp);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
