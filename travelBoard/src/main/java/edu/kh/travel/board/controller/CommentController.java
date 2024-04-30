package edu.kh.travel.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.travel.board.model.dto.Comment;
import edu.kh.travel.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("comment")
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
	private final CommentService service;
	
	@GetMapping("")
	public List<Comment> select(@RequestParam("boardNo") int boardNo){
		return service.select(boardNo); 
	}
	
	/**댓글 등록
	 * @return result
	 */
	@PostMapping("")
	public int insert(
	//@RequestParam : 파라미터  
	//@RequestMapping : 요청 주소 매핑
	//@RequestBody : 비동기 요청 시 body에 담겨져서 오는 것 받기
	@RequestBody Comment comment 
	//JSON이면 String인데
	//Comment DTO를 쓰면
	//요청 데이터가 JSON 데이터로 명시됨({"Content-Type" : "application/json"})
	
	//ArgumentsResolver가 JSON을 DTO로 자동 변경해준다 : 얘는 받아올 때 작동하는 것!!
	//	(내장돼있는 JACKSON 라이브러리가 기능함)
	// HttpMessageConverter : 얘는 내보낼 때 작동하는 것!!
			) {
		return service.insert(comment);
	}
	
	/**댓글 수정
	 * @param comment(번호, 내용 담겨있다)
	 * @return result
	 */
	@PutMapping("")
	public int update(
			//두 개 받아오니까 Map으로 받아도 되고
			//Comment DTO로 받아도 된다
			@RequestBody Comment comment
			) {
		return service.update(comment);
	}
	
	/**댓글 삭제
	 * @return result
	 */
	@DeleteMapping("")
	public int delete(
			@RequestBody int commentNo
			//안전하게 하고 싶으면 세션에서 회원 번호도 받아오면 된다
			) {
		return service.delete(commentNo);
	}
	
	
	
}
