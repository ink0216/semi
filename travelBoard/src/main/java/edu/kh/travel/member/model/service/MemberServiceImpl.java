package edu.kh.travel.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {
	
	
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
}
	

