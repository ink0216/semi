package edu.kh.travel.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.main.model.service.MainService;
import edu.kh.travel.member.model.dto.Member;
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
	
	
	
	/**
	 * 아이디 찾기 페이지로 이동
	 * @return
	 */
	@GetMapping("searchId")
	public String searchId() {
		
		return "/searchId";
	}
	

	
	
	
	/**
	 * 전화번호, 생년월일을 입력해서 아이디 찾기 서비스
	 * @param memberTel
	 * @return
	 */
	@GetMapping("selectTelBirth")
	public String selectTelBirth(
			@RequestParam("inputTel") String inputTel,
			@RequestParam("inputBirth") String inputBirth, Model model) {
		
		
		
		String path = null;
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberTel", inputTel);
		map.put("memberBirth", inputBirth);
		
		Member member = service.selectTelBirth(map);
		
		
	
		
		if(member == null) {
			
			path = "member/idSearchFail";
			
		} else {
			
			path = "member/idResult";
			model.addAttribute("member",member);
		
		
		
		}
		
	
		return path;
	}
	
	
		
		
	}
		
	
	
	



