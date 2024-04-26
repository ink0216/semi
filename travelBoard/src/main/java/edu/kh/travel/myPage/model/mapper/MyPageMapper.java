package edu.kh.travel.myPage.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.travel.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	/** 프로필 이미지 변경
	 * @param temp
	 * @return
	 */
	int updateProfile(Member temp);

	/** 현재 비밀번호 조회
	 * @param memberNo
	 * @return
	 */
	String selectPw(int memberNo);

	/** 비밀번호 변경
	 * @param member
	 * @return
	 */
	int changePw(Member member);

}
