package com.korea.service;

import org.mindrot.bcrypt.BCrypt;

import com.korea.dao.MemberDAO;
import com.korea.dto.MemberDTO;

public class MemberService {
	
	MemberDAO dao = MemberDAO.getInstance();
	public BCrypt passwordEncoder = new BCrypt(); //salt table이 포함 /패스워드 암호화
	
	//싱글톤 패턴
	private static MemberService instance=null;
	public static MemberService getInstance() {
		if(instance==null)
			instance=new MemberService();
		return instance;
	}
	private MemberService() {
		
	}
	
	//Join 시 member 정보 insert
	public boolean MemberInsert(MemberDTO dto) {
		//패스워드 암호화
		String pwd=passwordEncoder.hashpw(dto.getPwd(),passwordEncoder.gensalt()); //salt 값이 매번 달라짐
		System.out.println("PWD(EN) : "+pwd);
		dto.setPwd(pwd);
		
		
		return dao.insert(dto);
	}
	
	//memberDTO에 저장된 member 계정 정보 Select
	public MemberDTO MemberSearch(String email) {
		return dao.Select(email);
	}
}
