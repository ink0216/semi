package edu.kh.travel.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.mapper.BoardMapper;
import edu.kh.travel.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	private final BoardMapper mapper;
	//인터셉터로 게시판 종류 얻어오기
	@Override
		public List<Map<String, Object>> selectBoardTypeList() {
			return mapper.selectBoardTypeList();
		}
	
	//해당 대륙 게시판으로 이동
	@Override
	public List<Board> boardList(String selectContinent) {
		return mapper.boardList(selectContinent);
	}
	
}
