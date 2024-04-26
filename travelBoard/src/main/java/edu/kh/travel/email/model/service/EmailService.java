package edu.kh.travel.email.model.service;

import java.util.Map;

public interface EmailService {

	/**
	 * 회원가입을 위한 이메일 보내기
	 * @param string
	 * @param email
	 * @return
	 */
	String sendEmail(String string, String email);

	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);





}
