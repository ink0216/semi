package edu.kh.travel.main.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{
	
	@Autowired
	private MemberMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	@Override
		public Member selectTelBirth(Map<String, Object> map) {
			
			return mapper.selectTelBirth(map);
		}

	
	
	@Override
	public Member selectEmailTelBirth(Map<String, Object> map) {
		
		return mapper.selectEmailTelBirth(map);
	}
	
	
	
	
	
//	@Override
//	public int memberPwReset(Map<String, Object> map, String memberEmail) {
//		
//		String beforepw = mapper.memberPwReset(memberEmail);
//		
//		String nowPw = (String)map.get("nowPw");
//		
//		if( !bcrypt.matches(nowPw,pw)) {
//			
//			return 0;
//		} else {
//			String newPw = bcrypt.encode((String)map.get("newPw"));
//			
//			map.put("encPw", encPw);
//			map.put("memberNo", memberEmail);
//			
//			return mapper.memberPwReset(paramMap);
//			
//		
//		
//	}
//	
	@Override
	public int memberPwReset(Map<String, Object> map, String memberEmail) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
