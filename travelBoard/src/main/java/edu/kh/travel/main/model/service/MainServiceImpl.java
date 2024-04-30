package edu.kh.travel.main.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{
	
	@Autowired
	private MemberMapper mapper;
	
	@Override
		public Member selectTelBirth(Map<String, Object> map) {
			
			return mapper.selectTelBirth(map);
		}

	
	
	@Override
	public Member selectEmailTelBirth(Map<String, Object> map) {
		
		return mapper.selectEmailTelBirth(map);
	}
	
	
	
	
}
