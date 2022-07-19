package com.korea.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardDownloadAllController implements SubController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {

		BoardService service = BoardService.getInstance();
		//읽고 있는 게시물 꺼내기
		HttpSession session = req.getSession();
		BoardDTO dto = (BoardDTO)session.getAttribute("dto");
		
		//서비스 실행(ZIP압축 매서드)
		boolean result = service.downloadZIP(dto,resp);
	}

}
