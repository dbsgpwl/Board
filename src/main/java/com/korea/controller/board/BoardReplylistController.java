package com.korea.controller.board;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.dto.ReplyDTO;
import com.korea.service.BoardService;

public class BoardReplylistController implements SubController {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		BoardService service = BoardService.getInstance();
		
		//읽고 있는 게시물의 모든 댓글 출력
		HttpSession session = req.getSession(); //사용자가 현재 읽고 있는 페이지의 세션 가져오기
		BoardDTO dto  = (BoardDTO)session.getAttribute("dto");
		
		ArrayList<ReplyDTO> list = service.getReplaylist(dto.getNo());
		try {
			PrintWriter out = resp.getWriter();	//내장객체 
			
			for(int i=0; i<list.size();i++){
				out.println("<div class=\"form-control\">");
				out.println("<span style=font-size:0.5rem>"+list.get(i).getWriter()+"</span>&nbsp;&nbsp;");
				out.println("<span style=font-size:0.5rem>"+list.get(i).getRegdate()+"</span><br>");
				out.println("<span>"+list.get(i).getContent()+"</span>");
				out.println("</div>");
				
			}
			
			
			
			
			
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
