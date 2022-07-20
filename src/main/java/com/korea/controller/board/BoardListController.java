package com.korea.controller.board;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardListController implements SubController{

	BoardService service = BoardService.getInstance();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		 
		try {
			
			//파라미터
			String tmpstart=req.getParameter("start");
			String tmpend=req.getParameter("end");
			String nowPage=req.getParameter("nowPage");
			
			int start=0;
			int end=0;
			
			if(tmpstart==null || tmpend==null) //최조로 접근한 상태라면(게시판에 처음 접속시)
			{
				start=1; //시작 페이지 번호
				end=10;  //끝 페이지 번호	
			}
			else
			{
				start=Integer.parseInt(tmpstart);
				end=Integer.parseInt(tmpend);
				
			}
					
			//입력값
			//서비스실행
			
			List<BoardDTO> list = service.getBoardList(start, end); //게시물 시작, 끝번호 조회
			int tcnt = service.getTotalCnt(); //게시물 총건수 조회
			
			req.setAttribute("list", list);
			req.setAttribute("tcnt", tcnt);
			
			//페이징 처리
			//req.setAttribute("nowPage", nowPage);
			
			
			//쿠키생성(게시글읽기 새로고침시 중복Count방지)
			Cookie init = new Cookie("init","true");
			resp.addCookie(init);
			
			
			
			req.getRequestDispatcher("/WEB-INF/board/list.jsp?nowPage="+nowPage).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

}
