package edu.kh.travel.board.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.travel.member.model.dto.Member;

@Mapper
public interface MainMapper {
	
	public Member selectTelBirth(Map<String, Object> map);

}
