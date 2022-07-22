package com.korea.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardDeleteController implements SubController {

	BoardService service = BoardService.getInstance();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {


		//파라미터
		String pwd = req.getParameter("pwd");
		String nowPage = req.getParameter("nowPage");
		
		//읽고 있는 게시물 꺼내기
		HttpSession session = req.getSession();
		BoardDTO dto = (BoardDTO)session.getAttribute("dto");
		
		//입력값
		if(dto.getPwd().equals(pwd)) //패스워드 일치 여부
		{
			//서비스 함수 실행(게시물 dto-no / 파일삭제)
			service.BoardRemove(dto);
		}
		
		//서비스
		
		//뷰
		
		try {
			int numPerPage=10;
			int np = Integer.parseInt(nowPage);
			int start=(np*numPerPage)-numPerPage+1;
			int end=(np*numPerPage);
			req.getRequestDispatcher("/Board/list.do?nowPage="+nowPage+"&start="+start+"&end="+end)
			.forward(req, resp);

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
