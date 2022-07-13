package com.korea.service;

import java.util.List;

import com.korea.dao.BoardDAO;
import com.korea.dto.BoardDTO;

public class BoardService {

	BoardDAO dao = BoardDAO.getInstance();
	
	
	//싱글톤 패턴
	private static BoardService instance=null;
	public static BoardService getInstance() 
	{
		if(instance==null)
			instance = new BoardService();
		return instance;
	}
	private BoardService() {}
	
	//게시물 시작, 끝 번호 받아서 조회
	public List<BoardDTO> getBoardList(int start,int end)
	{
		return dao.Select(start, end);
	}
	
	//게시물 개수
	public int getTotalCnt() 
	{
		return dao.getTotalCount();
	}
	
	//게시물 입력(Insert)
	public boolean PostBoard(BoardDTO dto)
	{
		return dao.Insert(dto);
	}
}
