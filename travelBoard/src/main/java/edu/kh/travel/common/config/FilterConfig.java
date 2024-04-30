package edu.kh.travel.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.travel.common.config.filter.LoginFilter;

@Configuration
public class FilterConfig {

	
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {
		
		
		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		
		
		filter.setFilter(new LoginFilter());
		
		
		// 게시판 기능이 작동 끝나면 "/editBoard/*"도 추가하기
		String[] filteringUrl = {"/myPage/*","/board/*"};
		
		
		filter.setUrlPatterns(Arrays.asList(filteringUrl));
		
		
		filter.setName("loginFilter");
		
		
		filter.setOrder(1);
		
		return filter;
		
		
		
		
	}
	

}
