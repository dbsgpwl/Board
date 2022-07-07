package com.korea.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;



public class authfilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		//Request 전 처리
		System.out.println("======= Filter 처리 (Request 전)======");
		chain.doFilter(req,resp);
		
		//Request 전 처리
		System.out.println("======= Filter 처리 (Request 전)======");
	}


	
	
}
