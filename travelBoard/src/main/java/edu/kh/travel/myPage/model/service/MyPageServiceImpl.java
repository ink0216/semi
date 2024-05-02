package edu.kh.travel.myPage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.Pagination;
import edu.kh.travel.common.util.Utility;
import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService{
	
	private final MyPageMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath; //  /myPage/profile/
		
	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // C:/uploadFiles/profile/
	
	
	// 프로필 이미지 변경
	@Override
	public int updateProfile(MultipartFile profileImg, Member loginMember) 
			throws IllegalStateException, IOException {
		
		// 수정 경로
		String updatePath = null;
		
		
		String rename = null;
		
		// 업로드 이미지 있을경우
		if(!profileImg.isEmpty()) {
			
			// updatePath 조합
			// 파일명 변경
			rename = Utility.fileRename(profileImg.getOriginalFilename());
			// /myPage/profile/변경된파일명.jpg
			updatePath = profileWebPath + rename;
		}

		// 수정된 프로필에 이미지 경로 + 회원 번호를 저장할 DTO 객체
		Member temp = Member.builder()
						.memberNo(loginMember.getMemberNo())
						.profileImg(updatePath)
						.build();
		
		int result = mapper.updateProfile(temp);
		
		if(result > 0) { // 수정 성공
			
			if(!profileImg.isEmpty()) {
				
				// 파일 서버 지정폴더에 저장
				profileImg.transferTo(new File(profileFolderPath + rename));
			}
			
			// 세션업데이트
			loginMember.setProfileImg(updatePath);
		}
		
		
		return result;
	}
	
	//회원 탈퇴
	@Override
	public int secession(String memberId, String memberPw, Member loginMember) {
		
		int memberNo = loginMember.getMemberNo();
		String memberEmail = loginMember.getMemberEmail();
		String pw = mapper.selectPw(memberNo);
		
		//   입력된 비번과 원래 비번이 맞지 않음   &&   원아이디랑 입력 아이디랑 안맞음 
		if( !bcrypt.matches(memberPw, pw) && memberId != memberEmail) {
			return 0;
		} else {
			return mapper.secession(memberNo);
		}
		
	}
	
	// 비밀번호 수정
	@Override
	public int changePw(Map<String, Object> map, int memberNo) {
		
		// 현재 비밀번호 DB조회
		String pw = mapper.selectPw(memberNo);
		
		String nowPw = (String)map.get("nowPw");
		
		if( !bcrypt.matches(nowPw,pw)) {
			return 0;
		} else {
			String newPw = bcrypt.encode((String)map.get("newPw"));
			
			Member member = new Member();
			
			member.setMemberNo(memberNo);
			member.setMemberPw(newPw);
			
			return mapper.changePw(member);
		}
	}

	
	// 회원정보 수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		// 입력 주소 있을 경우 형태 가공
		
		// 주소 입력 x
		if(inputMember.getMemberAddress().equals(",,")) {
			inputMember.setMemberAddress(null);
		} else {
			String address = String.join("^^^", memberAddress);
			
			inputMember.setMemberAddress(address);
		}
		return mapper.updateInfo(inputMember);
	}
	
	
	
	// 내가쓴글 조회
	@Override
	public Map<String, Object> writing(int memberNo, int cp) {
		
		int listCount = mapper.getListCount(memberNo);
		
		Pagination pagination = new Pagination(cp,listCount);
		
		int limit = pagination.getLimit(); //limit 얻어오기
		int offset = (cp-1)*limit;
		//3페이지 보고싶으면 앞의 20개는 건너뛰고 열 개 조회하면 됨
		
		RowBounds rowBounds = new RowBounds(offset, limit); 
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		List<Board> boardList = mapper.writing(memberNo,rowBounds);
		map.put("boardList", boardList);
		return map;
		
	}
	
}
