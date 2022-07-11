package com.korea.controller.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardListController implements SubController {
	BoardService sevice = BoardService.getInstance();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		try {
			//파라미터확인
			int start=0;
			int end=0;
			//입력값 검증
			
			//서비스 실행
			
			List<BoardDTO> list = service.getBoardList(start, end);
			req.setAttribute("list", list);
			//뷰 이동
			req.getRequestDispatcher("/WEB-INF/board/list.jsp").forward(req,resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
