package edu.kh.travel.myPage.model.service;

import edu.kh.travel.member.model.dto.Member;

public interface MyPageService {

	
	/** 비밀번호 수정
	 * @param nowPw
	 * @param newPw
	 * @param loginMember
	 * @return
	 */
	int changePw(String nowPw, String newPw, Member loginMember);

}
