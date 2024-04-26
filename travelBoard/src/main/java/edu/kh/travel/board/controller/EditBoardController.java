package edu.kh.travel.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("editBoard")
public class EditBoardController {
	@GetMapping("{contiCode:[A-Z]{2}}/insert")
	public String insertBoard() {
		return "board/boardInsert";
	}
}
