package edu.kh.travel.myPage.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.travel.common.util.Utility;
import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{
	
	private final MyPageMapper mapper;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath; //  /myPage/profile/
		
	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // C:/uploadFiles/profile/
	
	
	// 프로필 이미지 변경
	@Override
	public int updateProfile(MultipartFile profileImg, Member loginMember) {
		
		// 수정 경로
		String updatePath = null;
		
		String rename = null;
		
		// 업로드 이미지 있을경우
		if(!profileImg.isEmpty()) {
			
			// 파일명 변경
			rename = Utility.fileRename(profileImg.getOriginalFilename());
			
			updatePath = profileWebPath + rename;
		}
		
		Member temp = Member.builder()
						.memberNo(loginMember.getMemberNo())
						.profileImg(updatePath)
						.build();
		
		int result = mapper.updateProfile(temp);
		
		if(result > 0) {
			
			loginMember.setProfileImg(updatePath);
		}
		
		
		return result;
	}
	
	
	
	
	// 비밀번호 수정
	@Override
	public int changePw(String nowPw, String newPw, Member loginMember) {
		// TODO Auto-generated method stub
		return 0;
	}

}
