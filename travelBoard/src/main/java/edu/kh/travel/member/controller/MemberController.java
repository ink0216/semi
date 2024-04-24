package edu.kh.travel.member.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes
@Controller
@RequestMapping("member")
@Slf4j
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	
	
	
//	회원 로그인 서비스
	
	@PostMapping("login")
	public String login(
			Member inputMember, 
			RedirectAttributes ra,
			Model model,
			@RequestParam(value = "saveId", required = false) String saveId,
			HttpServletResponse resp) {
		
		Member loginMember = service.login(inputMember);
		
		

		
		
		// 로그인 정보가 일치하지 않을 경우
		if(loginMember == null) {
			
			
		
			ra.addFlashAttribute("message","아이디와 비밀번호를 다시 확인해 주시거나 비회원으로 접속해주세요.");
			
			
		}
		
	
		
		
			if(loginMember != null) {
				
					
					model.addAttribute("loginMember", loginMember);
					ra.addFlashAttribute("message","로그인성공");
					
			}
					
		
			
			
			
		// 아이디 저장
		Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
		
		
		cookie.setPath("/");
		
		if(saveId != null ) {
			
			cookie.setMaxAge(30*24*60*60);
			
		} else {
			
			cookie.setMaxAge(0);
			
		}
		
		resp.addCookie(cookie);
		
		
		
		return "redirect:/";
		
	

		
}
	
	@GetMapping("signup")
	public String signupPage()	{
		return "member/signup";
	}
	

}
