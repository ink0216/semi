package edu.kh.travel.member.model.mapper;

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

}
