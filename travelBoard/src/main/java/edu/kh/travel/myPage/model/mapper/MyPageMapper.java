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

}
