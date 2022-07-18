package com.korea.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.korea.controller.SubController;
import com.korea.service.BoardService;

public class BoardDownloadController implements SubController {

	BoardService service = BoardService.getInstance();
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) {
	     
		
		//파라미터
        String filename = req.getParameter("filename");
        String flag = req.getParameter("flag");
        
        //입력값

        //서비스
        boolean result= false;
        if(flag==null) { //단일파일 다운로드
        	result = service.download(filename, req, resp);
        	
        }else {			//전체파일 다운로드
        	result = service.download( req, resp);
        }

        //View
    }

}
