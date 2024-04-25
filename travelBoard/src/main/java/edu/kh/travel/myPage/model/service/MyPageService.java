package edu.kh.travel.myPage.model.service;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.travel.member.model.dto.Member;

public interface MyPageService {

	
	/** 비밀번호 수정
	 * @param nowPw
	 * @param newPw
	 * @param loginMember
	 * @return
	 */
	int changePw(String nowPw, String newPw, Member loginMember);

	/** 프로필 이미지 수정
	 * @param profileImg
	 * @param loginMember
	 * @return
	 */
	int updateProfile(MultipartFile profileImg, Member loginMember);

}
