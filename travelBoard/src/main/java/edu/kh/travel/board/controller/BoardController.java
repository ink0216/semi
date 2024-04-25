package edu.kh.travel.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.board.model.service.BoardService;
import edu.kh.travel.member.model.dto.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {
	
	private final BoardService service;
//	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}") // /board/1/1998?cp=1 이런 식으로 요청 온다(상세 조회 요청 주소 모양)
//	@GetMapping("/{selectContinent:[A-Z]{2}}")
	
	/**해당 게시판 전체 게시글 조회+검색 게시글 조회
	 * @param selectContinent
	 * @param cp
	 * @param model
	 * @param paramMap
	 * @return
	 */
	@GetMapping("{selectContinent:[A-Z]{2}}")
	public String afterLogin(
			@PathVariable("selectContinent") String selectContinent,
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			Model model,
			@RequestParam Map<String, Object> paramMap //파라미터 다 한꺼번에 받음(key, query 포함)
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
//	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}") // /board/1/1998?cp=1 이런 식으로 요청 온다(상세 조회 요청 주소 모양)
//	@GetMapping("/{selectContinent:[A-Z]{2}}")
	//게시글 상세 조회
	@GetMapping("{contiCode:[A-Z]{2}}/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("contiCode") String contiCode,
			@PathVariable("boardNo") int boardNo,
			Model model,
			RedirectAttributes ra,
			@SessionAttribute(value="loginMember", required=false) Member loginMember,
			//해당 회원이 해당 글에 좋아요 눌렀는 지 알기 위해(하트채우기)
			HttpServletRequest req, //요청에 담긴 쿠키 얻어오기(조회 수 에 이용)
			HttpServletResponse resp // 새로운 쿠키를 만들어서 내보내기(응답하기) (쿠키는 서버가 만들어서 클라이언트에 주면 클라이언트가 저장하고
			//서버 통신 때 보내줄 수 있다 서버로)
			) {
		return null;
	}
}