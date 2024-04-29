package edu.kh.travel.member.model.service;

import java.util.Map;

import edu.kh.travel.member.model.dto.Member;

public interface MemberService {

	
	/**
	 * 로그인 서비스
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);

	
	/**
	 * 회원가입 서비스
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int signup(Member inputMember, String[] memberAddress);

	
	/**
	 * 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);


	int checkNickname(String memberNickname);










}
