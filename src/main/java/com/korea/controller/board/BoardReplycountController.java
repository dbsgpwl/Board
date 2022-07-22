package com.korea.controller.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardReplycountController implements SubController {
	BoardService service = BoardService.getInstance();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		HttpSession session = req.getSession();
		
		BoardDTO dto = (BoardDTO)session.getAttribute("dto"); //읽고 있는 게시물 꺼내기
		
		int totalcount = service.getTotalReplyCount(dto.getNo());	//전체 게시물의 댓글 받아오기

		
		try {
			PrintWriter out = resp.getWriter();
			
			out.println(totalcount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
