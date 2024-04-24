package edu.kh.travel.myPage.model.service;

import org.springframework.stereotype.Service;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{
	
	private final MyPageMapper mapper;
	
	
	// 비밀번호 수정
	@Override
	public int changePw(String nowPw, String newPw, Member loginMember) {
		// TODO Auto-generated method stub
		return 0;
	}

}
