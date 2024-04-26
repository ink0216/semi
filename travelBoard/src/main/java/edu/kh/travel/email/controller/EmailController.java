package edu.kh.travel.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.kh.travel.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"authKey"})
@Controller
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {
	
	private final EmailService service;
	
	
	
	/**
	 * 회원가입을 하기 위한 이메일 인증
	 * @param email
	 * @return
	 */
	@ResponseBody
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		
		String authKey = service.sendEmail("signup",email);
		
		// 이메일 전송 성공하면 signup.html에 1을 리턴함
		
		if(authKey != null) {
			
			
			
			return 1;
		}
		else {
			
			return 0;
		}
		
		
		
		
		
	}
	

	 /**
	  * 입력된 인증번호가 맞는지 확인하는 기능
	  * @param map
	  * @return
	  */
	@PostMapping("checkAuthKey")
	@ResponseBody
	public int checkAuthKey(
			
			@RequestBody Map<String, Object> map) {
		
		
		return service.checkAuthKey(map);
		
		
	}
	

}
