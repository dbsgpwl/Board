package com.korea.test;

import org.junit.Test;

import com.korea.dao.MemberDAO;
import com.korea.dto.MemberDTO;
//juit : server 비활성화 상태에서 특정 테스트 진행 
public class DaoTest {

//	@Test
//	public void test() {
//		MemberDTO dto = new MemberDTO();
//		dto.setEmail("example@example.com");
//		dto.setPwd("1234");
//		dto.setAddr1("대구");
//		dto.setAddr2("아파트아파트");
//		
//		MemberDAO dao = MemberDAO.getInstance();
//		boolean result=dao.insert(dto);
//		
//		if(result)
//			System.out.println("Insert 성공");
//		else
//			System.out.println("Insert 실패");
//	}
	
	@Test
	public void test2() {
		//MemberDAO의 Select(email) test
		MemberDAO dao=MemberDAO.getInstance();
		MemberDTO dto=dao.Select("dbsgpwl97@naver.com");
		System.out.println("결과 : "+dto.toString());
	}
	

}
