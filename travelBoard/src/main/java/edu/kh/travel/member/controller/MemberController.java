package edu.kh.travel.member.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
@Slf4j
@SessionAttributes({"loginMember"}) //모델 중에서 같은 키값가지는 거 있으면 세션으로 올려라
 //모델 중에서 같은 키값가지는 거 있으면 세션으로 올려라
//->Model객체 : 이렇게 해서 request랑 session scope 둘 다 커버 가능!!!!!!
//자바에서의 중괄호 == 배열 ->스트링 배열로 여러 키 등록해놓을 수 있다 ->밑의 모델이 세션으로 된다 
public class MemberController {
	
	@Autowired
	private MemberService service;
//	회원 로그인 서비스
	
	@PostMapping("login")
	public String login(
			Member inputMember, //일단은 로그인 해야만 게시판 들어갈 수 있도록
			RedirectAttributes ra,
			Model model,
			@RequestParam(value = "saveId", required = false) String saveId,
			@RequestParam("contiCode") String contiCode,
			HttpServletResponse resp) {
		
		
		Member loginMember = service.login(inputMember);
		
		String path = null;
		
		// 로그인 정보가 일치하지 않을 경우
		if(loginMember == null) {
			
			ra.addFlashAttribute("message","로그인 정보가 일치하지 않습니다");
			
			path = "/";
			
		}
		if(loginMember != null) {
		
			model.addAttribute("loginMember", loginMember); 
			
			
			// 아이디 저장 옵션
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			
		
			cookie.setPath("/");
			
			if(saveId != null ) {
				
				cookie.setMaxAge(30*24*60*60);
				
			} else {
				
				cookie.setMaxAge(0);
				
			}
			resp.addCookie(cookie);
			//ra.addFlashAttribute("message","해당 회원이 존재합니다!!!!");
			
			
			path = "/board/"+contiCode;
			
		}
	
//		return "redirect:/board/"+selectContinent; //selectContinent로 해당 게시판으로 이동되도록 PathVariable
		return "redirect:" + path;
		
		
	}
	
	/**
	 * 회원가입 페이지로 이동
	 * @return
	 */
	@GetMapping("signup")
	public String signupPage() {
		
		return "member/signup";
	}
	
	
	
	/**
	 * 로그인 전용 페이지로 이동
	 * 
	 */
	@GetMapping("login")
	public String loginPage() {
		return "member/login"; 
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 비밀번호 찾기 페이지로 이동
	 * @return
	 */
	@GetMapping("searchPw")
	public String searchPw() {
		
		return "member/searchPw";
	}
	
	
	
	
	/**
	 * 회원가입 서비스
	 * @return
	 */
	@PostMapping("signup")
	public String signup(@ModelAttribute Member inputMember, 
			@RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra) {
		
		int result = service.signup(inputMember, memberAddress);
		
		String path = null;
		String message = null;
		
		
		
		if(result > 0) {
			
			message = inputMember.getMemberNickname() + "님의 회원 가입이 완료되었습니다.";
			
			path = "/";
		} else {
			
			message = "회원 가입 실패";
			path = "signup";
		}
			
		ra.addFlashAttribute("message",message);
	
		return "redirect:" + path;
	}
	
	
	
	
	/**
	 * 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		
		
		
		return service.checkEmail(memberEmail);
		
		
	}
	
	
	/**
	 * 닉네임 중복 검사
	 * @param memberNickname
	 * @return
	 */
	@ResponseBody 
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname") String memberNickname) {
		
		return service.checkNickname(memberNickname);
	}
	
	
	
	
	
	/**
	 * 로그아웃
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		
		return "redirect:/";
		
	}
	
	
	
	
	
	
	
	
	

	
	
	
	

}