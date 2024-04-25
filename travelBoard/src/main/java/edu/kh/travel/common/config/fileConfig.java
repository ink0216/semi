package edu.kh.travel.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class fileConfig implements WebMvcConfigurer{
	
	@Value("${my.profile.resource-handler}")
	private String profileResourceHandler; // 프로필 이미지 요청 주소
	
	@Value("${my.profile.resource-location}")
	private String profileResourceLocation; // 프로필 이미지 요청 시 연결할 서버폴더 경로

	
	
}
