package edu.kh.travel.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.main.model.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService service;
	
	
	@RequestMapping("/")
	public String mainPage() {
		
		return "/main";
		
	}
	
	
	
	/**
	 * 로그인 안하면 마이페이지나 게시판 진입 못함
	 * @return
	 */
	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		
		ra.addFlashAttribute("message","로그인 후 이용하실 수 있는 페이지입니다.");
		
		return "redirect:/";
		
		
	
		
		
	}
		
		
	
	
	


}
