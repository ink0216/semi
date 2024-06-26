package edu.kh.travel.member.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	
	
	
	/**
	 * 로그인 서비스
	 */
	@Override
	public Member login(Member inputMember) {
		
		
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		if(loginMember == null) {
			
			return null;
		}
		if(!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		
		loginMember.setMemberPw(null);
		return loginMember;
	}

	
	
	
	/**
	 * 회원가입 서비스
	 */
	@Override
	public int signup(Member inputMember, String[] memberAddress) {
		
		// 주소가 입력이 된 경우
		if(!inputMember.getMemberAddress().equals(",,")) {
			
			String address = String.join("^^^", memberAddress);
			
			inputMember.setMemberAddress(address);
				
			
		}
		else {
			// 주소가 입력이 안되어있으면 null로 처리하도록 함
			inputMember.setMemberAddress(null);
		}
		
		
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		
		inputMember.setMemberPw(encPw);
		
		return mapper.signup(inputMember);

	}
		
	
	
	
	
	@Override
	public int checkEmail(String memberEmail) {
		
		return mapper.checkEmail(memberEmail);
	}
	
	
	
	
	@Override
	public int checkNickname(String memberNickname) {
		
		return mapper.checkNickname(memberNickname);
	}
	
	
	

	

	
	
	
}
