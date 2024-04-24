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

}
