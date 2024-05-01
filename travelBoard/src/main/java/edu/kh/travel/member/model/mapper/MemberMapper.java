package edu.kh.travel.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.travel.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/**
	 * 로그인 서비스
	 * @param memberEmail
	 * @return
	 */
	public Member login(String memberEmail);

	
	/** 
	 * 회원가입 서비스
	 * @param inputMember
	 * @return
	 */
	public int signup(Member inputMember);


	
	/**
	 * 이메일 중복검사
	 * @param memberEmail
	 * @return
	 */
	public int checkEmail(String memberEmail);


	/**
	 * 닉네임 중복 검사
	 * @param memberNickname
	 * @return
	 */
	public int checkNickname(String memberNickname);


	public Member selectTelBirth(Map<String, Object> map);


	
	public Member selectEmailTelBirth(Map<String, Object> map);


	public Member login(Map<String, Object> map);


	public int memberPwReset(Map<String, Object> paramMap);


	public String selectPw(String memberEmail);
	









	


	








}
