package edu.kh.travel.common.config.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		
		HttpSession session = req.getSession();
		
		
		// session에 담긴 로그인 인원이 없으면, loginError를 리다이렉트
		if(session.getAttribute("loginMember") == null) {
			
			resp.sendRedirect("/loginError");
			
			
		}else {
			
			chain.doFilter(request, response);
			
		}
		
		
		
	}
	
}
