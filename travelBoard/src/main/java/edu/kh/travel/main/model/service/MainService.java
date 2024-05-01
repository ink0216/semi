package edu.kh.travel.main.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.travel.member.model.dto.Member;


public interface MainService {

	Member selectTelBirth(Map<String, Object> map);

	Member selectEmailTelBirth(Map<String, Object> map);

	int memberPwReset(Map<String, Object> paramMap, String memberEmail);

//	int memberPwReset(Map<String, Object> map, String memberEmail);

	







}
