package edu.kh.travel.email.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import edu.kh.travel.email.model.mapper.EmailMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	private final SpringTemplateEngine templateEngine;
	
	private final EmailMapper mapper;

	/*
	 * 회원가입을 위한 이메일 인증
	 */
	@Override
	public String sendEmail(String htmlName, String email) {
		
		String authKey = createAuthKey();
		
		
		
		try {
			
			String subject = null;
			
			
			switch(htmlName) {
			
			case "signup" : subject = "이메일 인증(travelBoard)";
			
			break;
			}
			
			
			
			
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
			
			helper.setTo(email); 
			helper.setSubject(subject);
			helper.setText(loadHtml(authKey,htmlName),true);
			helper.addInline("logo",new ClassPathResource("static/images/logo.jpg"));
			
			
			mailSender.send(mimeMessage);
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}

		
		Map<String, String> map = new HashMap<>();
		map.put("authKey", authKey);
		map.put("email", email);
		
		
		int result = mapper.updateAuthKey(map);
		
		
		if(result == 0) {
			result = mapper.insertAuthKey(map);
			
		}
		
		if(result == 0) return null;
		
	
		
		
		
		return authKey;
		
		
	}
	
	
	// HTML 파일을 읽어와 String으로 변환시킴
	public String loadHtml(String authKey, String htmlName) {
		
		Context context = new Context();
		
		context.setVariable("authKey", authKey);
		
		return templateEngine.process("email/" + htmlName,context);
	}

	
	
	
	
	
	/**
	 * 인증번호 생성 (랜덤한 숫자 6개)
	 * 
	 * @return authKey
	 */
	public String createAuthKey() {

		String key = "";

		
		/*
		 * for (int i = 0; i < 6; i++) {
		 * 
		 * int sel1 = (int) (Math.random() * 3); // 0:숫자 / 1,2:영어
		 * 
		 * if (sel1 == 0) {
		 * 
		 * int num = (int) (Math.random() * 10); // 0~9 key += num;
		 * 
		 * } else {
		 * 
		 * char ch = (char) (Math.random() * 26 + 65); // A~Z
		 * 
		 * int sel2 = (int) (Math.random() * 2); // 0:소문자 / 1:대문자
		 * 
		 * if (sel2 == 0) { ch = (char) (ch + ('a' - 'A')); // 대문자로 변경
		 * 
		 * key += ch; }
		 * 
		 * }
		 */
		 

		for (int i = 0; i < 6; i++) {

			int num = (int) (Math.random() * 10);
			
			key += num;
		}
		
		return key;
	}
	
	
	/**
	 * 이메일 입력한 인증번호가 맞는지 확인
	 */
	@Override
	public int checkAuthKey(Map<String, Object> map) {
		
		return mapper.checkAuthKey(map);
	}

}
