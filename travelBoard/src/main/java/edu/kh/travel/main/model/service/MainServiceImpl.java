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
	
	
	
	
	
	@Override
	public int memberPwReset(Map<String, Object> paramMap, String memberEmail) {
		
		
		
		
		
		String memberPwReset =  bcrypt.encode((String)paramMap.get("memberPwReset"));
		
		paramMap.put("memberPwReset", memberPwReset);
		paramMap.put("memberEmail", memberEmail);
		
		
		
		
		
		return mapper.memberPwReset(paramMap);
	
	
	


	
	}
	
}
