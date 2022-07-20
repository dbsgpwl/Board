package com.korea.controller.board;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.korea.controller.SubController;
import com.korea.dto.BoardDTO;
import com.korea.service.BoardService;

public class BoardPostController implements SubController {
	
	BoardService service = BoardService.getInstance();
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
		
		String flag = req.getParameter("flag");
		
		try { 
			
			if(flag==null) //list.jsp에서 글쓰기 버튼을 누른 경우 
			{
				req.getRequestDispatcher("/WEB-INF/board/post.jsp").forward(req,resp);
			}
			else 		  //post.jsp에서 등록할 내용을 기입하고 글쓰기 버튼을 누른 경우(업로드 위해)
			{
				// 1 파라미터
				// title, content, pwd, writer, ip(filename, filesize)..
				String title=req.getParameter("title");
				String content=req.getParameter("content");
				String pwd=req.getParameter("pwd");
				String ip=req.getRemoteAddr(); //getRemoteAddr() : 주소값을 받아오는 매서드
				
				
				HttpSession session = req.getSession();
				String writer = (String)session.getAttribute("email");
				
				// 2 입력값 검증
				
				if(title==null || content==null ||pwd==null || ip==null) //하나라도 값이 null이면, post.jsp로 포워드
				{
					req.getRequestDispatcher("/WEB-INF/board/post.jsp").forward(req,resp);
					return;
				}
					
				// 3 서비스 실행 -> Dao -> DB
				BoardDTO dto = new BoardDTO(); //Dto에서 값 받아오기
					dto.setTitle(title);
					dto.setContent(content);
					dto.setPwd(pwd);
					dto.setIp(ip);
					dto.setWriter(writer);
				
				//추가 파일 part 전달(Upload)
			
					
				ArrayList<Part> parts=(ArrayList<Part>)req.getParts(); //req.getParts(): -request로부터 part를 꺼내옴
				
				boolean result=false;
				long size = parts.get(3).getSize(); //파일 사이즈가 없는경우 -> 파일이 없음!
				
				if(size==0)	{//파일전달이 안된 경우(파일 미포함 글쓰기 요청)
					result = service.PostBoard(dto);
				}
				else {			//파일이 포함되어 있는 경우 (파일 포함 글쓰기 요청 분기처리)
					result = service.PostBoard(dto,parts);
				}
				// 4 뷰 이동
				if(result)
				{
//					
					resp.sendRedirect("/Board/list.do");
					return ;
				}
				else
				{
					req.getRequestDispatcher("/WEB-INF/board/post.jsp").forward(req,resp);
					//return ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
