package edu.kh.travel.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.member.model.mapper.MemberMapper;

@Transactional
@Service
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
		//테스트(디버그모드로)
		//bcrypt.encode(문자열) : 문자열을 암호화시켜서 반환해줌!
		//String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		//아이디랑 비밀번호 둘다 qwer로 했다!! 이걸로 샘플 데이터 집어넣기
		//암호화된 비밀번호
		// $2a$10$EvNgaFSGQUygb2DdiiiPS.8JGdJJBx.mcZ3txZ8Hvgo9d2JmFkfTe
		
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		//아이디랑 비밀번호 둘 다 qwer로 했다!!
		if(loginMember == null) {
			
			return null;
		}
		if(!bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null;
		}
		loginMember.setMemberPw(null);
		return loginMember; //원래 이거였다
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
		// TODO Auto-generated method stub
		return 0;
	}
}
