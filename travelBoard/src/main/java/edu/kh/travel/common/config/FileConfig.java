package edu.kh.travel.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;


// @PropertySource("classpath:/config.properties")
//-> config.properties에 작성된 내용을 해당 클래스에서 사용하겠다
@Configuration
@PropertySource("classpath:/config.properties")
public class FileConfig implements WebMvcConfigurer {
	
	// config.properties에 작성된 파일 업로드 임계값 얻어와 필드
	@Value("${spring.servlet.multipart.file-size-threshold}")
	private long filedSizeThredhod;

	@Value("${spring.servlet.multipart.max-request-size}")
	private long maxRequestSize; // 요청당 파일 최대 크기
	
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private long maxFileSize; // 개별 파일당 최대 크기
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private String locating; // 입계값 초과 시 임시 저장 폴더 경로
	
	@Value("${my.profile.resource-handler}")
	private String profileResourceHandler; // 프로필 이미지 요청 주소
	
	@Value("${my.profile.resource-location}")
	private String profileResourceLocation; // 프로필 이미지 요청 시 연결할 서버폴더 경로
	
	// 게시글 이미지 요청 주소
	@Value("${my.board.resource-handler}")
	private String boardResourceHandler; 
	
	// 게시글 이미지 요청시 연결할 서버 폴더 경로
	@Value("${my.board.resource-location}")
	private String boardResourceLocation;
	
	
	
	// 요청 주소에 따라
	// 서버 컴퓨터의 어떤 경로에 접근할지 설정
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry
		.addResourceHandler("/myPage/file/**") // 클라이언트 요청 주소 패턴 :: 이주소로 오면
		.addResourceLocations("file:///C:\\uploadFiles\\test\\"); // :: 해당 폴더로 요청 - 이 경로에서 가져오기
		
		
		// 프로필 이미지 요청 = 서버 폴더 연결 추가
		registry
		 .addResourceHandler(profileResourceHandler) // /myPage/profile
		 .addResourceLocations(profileResourceLocation); 
		
		// 게시글 이미지 요청 - 서버 폴더 연결 추가
		registry
		.addResourceHandler(boardResourceHandler)
		.addResourceLocations(boardResourceLocation);
	}
	
	
	/* MultipartResolver 설정 :: 조건 */
	@Bean
	public MultipartConfigElement configElement() {
		
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		factory.setFileSizeThreshold(DataSize.ofBytes(filedSizeThredhod));
		
		factory.setMaxFileSize(DataSize.ofBytes(maxFileSize));
		
		factory.setMaxRequestSize(DataSize.ofBytes(maxFileSize));
		
		factory.setLocation(locating);
		
		
		
		return factory.createMultipartConfig();
	}
	
	// MultipartResolver 객체를 bean으로 추가
	// -> 추가 후 위에서 만든 mulipartConfig를 자동으로 이용
	
	
	@Bean
	public MultipartResolver multipartResolver() {
		
		StandardServletMultipartResolver multipartResolver
			= new StandardServletMultipartResolver();
		
		return multipartResolver;
		
	}
	
	

}//
