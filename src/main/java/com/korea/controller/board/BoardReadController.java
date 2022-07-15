package com.korea.controller.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardReadController implements SubController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		BoardService service = BoardService.getInstance();
		
		//파라미터	
		String no = req.getParameter("no");
		String nowPage = req.getParameter("nowPage");
		
		//서비스 실행
		int num = Integer.parseInt(no);
		BoardDTO dto = service.getBoardDTO(num);
		
		//뷰로 이동
		try {
			req.setAttribute("dto", dto);
			req.setAttribute("nowPage", nowPage); //수정후 다시 읽고 있던 페이지 위치로 돌아오기
			req.getRequestDispatcher("/WEB-INF/board/read.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
