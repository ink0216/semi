package edu.kh.travel.myPage.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.member.model.dto.Member;

public interface MyPageService {

		

	/** 프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @return
	 */
	int updateProfile(MultipartFile profileImg, Member loginMember)throws IllegalStateException, IOException;



	/** 회원 탈퇴
	 * @param memberId
	 * @param memberPw
	 * @param memberNo
	 * @return
	 */
	int secession(String memberId, String memberPw, Member loginMember);


	/** 비밀번호 수정
	 * @param map
	 * @param memberNo
	 * @return
	 */
	int changePw(Map<String, Object> map, int memberNo);



	/** 내정보 수정
	 * @param inputMember
	 * @param memberAddress
	 * @return
	 */
	int updateInfo(Member inputMember, String[] memberAddress);



	/** 내가쓴 글 조회
	 * @param memberNo
	 * @return
	 */
	List<Board> writing(int memberNo);







}
