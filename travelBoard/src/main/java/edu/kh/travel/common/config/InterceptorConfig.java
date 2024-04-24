package edu.kh.travel.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.travel.common.interceptor.ContiNameInterceptor;
import edu.kh.travel.common.interceptor.ContiTypeInterceptor;

//우리가 만든 BoardTypeInterceptor가 어떤 요청을 가로채서 동작을 할 지 지정하는 이 클래스를 만들어야 함!!!
@Configuration //서버가 켜지면 내부 메서드를 모두 수행해서 설정 적용함 (설정용 어노테이션)
public class InterceptorConfig implements WebMvcConfigurer{
	
	
	//ContiTypeInterceptor가 아직 bean등록이 안돼있어서 bean 등록해주기
	
	@Bean //개발자가 만들어서 반환하는 객체를 bean으로 등록(관리는 Spring Container에게 넘긴다)
	public ContiTypeInterceptor contiTypeInterceptor() {
		return new ContiTypeInterceptor();
		//이거 기본생성자로 ContiTypeInterceptor객체 만드는데 
		//ContiTypeInterceptor클래스에서 @RequiredArgsConstructor 사용하면 private final을 쓰면 
		// ContiTypeInterceptor 참고하기
	} 

	//게시판 이름 출력하는 인터셉터를 Bean으로 등록
	@Bean
	public ContiNameInterceptor contiNameInterceptor() {
		return new ContiNameInterceptor(); //이것만 쓰면 동작 못하고 밑의 addInterceptors에 어떤 주소에 동작할 지 작성하기
	}
	
	//WebMvcConfigurer : 각종 오버라이딩 메서드 제공해주는 인터페이스
	//alt shift s -> override unimplemented method -> 오버라이딩 된 안의 내용들 다 지우면 된다
	// addInterceptors : 동작할 인터셉터 객체를 추가하는 메서드
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(contiTypeInterceptor())
		//생성되고 bean 으로 등록된 boardTypeInterceptor를 얻어와서 매개변수로 전달
		.addPathPatterns("/**") //얘는 모든 요청을 가로채서 일을 하라고 시킴
		//하는 일 : boardTypeInterceptor에 적혀있음 
		//전처리 -> application scope 객체를 얻어와서 boardTypeList가 없으면 서비스를 이용해서 다 조회해와서
		//application scope에 세팅하는 것 (최초 접속했을 때임)
		//가로챌 요청 주소를 지정(어떤 요청이 오는 것들만 가로챌 지)
		//여러 주소 작성 시 "","" -> 콤마로 구분해서 여러 개 쓰면 된다
		// /** : /(최상위 주소)로 시작하는 모든 요청 주소!! -> 모든 요청을 가로채게 된다!
		.excludePathPatterns("/css/**","/js/**",
						"/images/**", "/favicon.ico"); 
		//화면 만들 때 필요한 css 요청도 하는데, css로 시작하는 이하 요청(**)은 가로채지 않을거야
		//모든 요청 가로챌 건데 얘네들은 빼고 가로챌거야
		//가로 채지 않을 예외 주소 지정
		//include <-> exclued(미포함)
		
		//---------------------------------------------------------------------
		registry.addInterceptor(contiNameInterceptor()) 
		//boardNameInterceptor호출하면 Bean으로 등록된 boardNameInterceptor 객체가 반환되어 의존성 주입된다
		.addPathPatterns("/board/**","/editBoard/**"); //board라고 시작하는 모든주소랑 /editBoard로 시작하는 모든 주소
		
	}
	
	 
}
