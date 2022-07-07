package com.korea.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//SubController 상속 관계 인터페이스
public interface SubController {

				//모든 서브 컨트롤러의 내장객체
		//execute 함수 재정의 사용해야함
	void execute(HttpServletRequest req, HttpServletResponse resp);
}
