package edu.kh.travel.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
			Model model,
			@RequestParam Map<String, Object> paramMap //파라미터 다 한꺼번에 받음
			) {
		//조회 서비스 호출 후 결과 반환 받기
				//반환돼야 하는 결과가 두 개인데 
				//메서드는 하나씩만 반환할 수 있어서
				Map<String, Object> map =null;
				if(paramMap.get("key")==null) {
					//검색 아니니까 게시글 전체 조회(몇 페이지 분량)
					map=service.boardList(selectContinent,cp);
				}else {
					//검색을 한 경우
					paramMap.put("selectContinent", selectContinent);
					
					//검색 서비스 호출
					map = service.searchList(paramMap,cp);
				}
		//넘어온 selectContinent 값에 맞는 게시판의 게시글 목록 조회하는 서비스 호출
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		return "board/boardList";
	}
}