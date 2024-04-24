package edu.kh.travel.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.core.model.Model;
import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
	
	private final BoardService service;
//	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}") // /board/1/1998?cp=1 이런 식으로 요청 온다(상세 조회 요청 주소 모양)
//	@GetMapping("/{selectContinent:[A-Z]{2}}")
	@GetMapping("{selectContinent:[A-Z]{2}}")
	public String afterLogin(
			@PathVariable("selectContinent") String selectContinent,
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			Model model
			
			) {
		List<Board> boardList = service.boardList(selectContinent);
		
		return null;
	}
}