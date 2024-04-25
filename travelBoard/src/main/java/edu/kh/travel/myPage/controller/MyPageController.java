package edu.kh.travel.myPage.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;
import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {
	
	private final MyPageService service;

// -----페이지 이동------
	// 사이드 메뉴 페이지 이동 컨트롤러

	// 프로필 페이지 이동
	@GetMapping("profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	// 내정보 변경 페이지 이동
	@GetMapping("info")
	public String info() {
		return "myPage/myPage-info";
	}
	// 비밀번호 변경 페이지 이동
	@GetMapping("changePw")
	public String changePw() {
		return "myPage/myPage-changePw";
	}
	// 회원 탈퇴 페이지 이동
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	// 내가쓴글 목록 이동
	@GetMapping("writing")
	public String writing() {
		return "myPage/myPage-writing";
	}
	
// -----------------------
	
	/** 프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @return
	 */
	@PostMapping("profile")
	public String updateProfile(
		@RequestParam("profileImg") MultipartFile profileImg,
		@SessionAttribute("loginMember")Member loginMember,
		RedirectAttributes ra)throws IllegalStateException, IOException {
		
		// 로그인한 회원번호 얻어오기
		int memberNo = loginMember.getMemberNo();
		
		int result = service.updateProfile(profileImg,loginMember);
		
		String message = null;
		
		if(result >0) {
			message = "변경 성공";
		}else message="변경 실패";
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:profile";
	}
	
	
	/** 내정보 수정
	 * @return
	 */
	@PostMapping("info")
	public String updateInfo(
			@SessionAttribute("loginMember")String logString,
			Model model) {
		
		return null;
	}
	
	
	
	/** 비밀번호 수정
	 * @param nowPw
	 * @param newPw
	 * @param loginMember
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(
		@RequestParam("nowPw")String nowPw,
		@RequestParam("newPw")String newPw,
		@SessionAttribute("lo inMember")Member loginMember) {
		
		
		int result = service.changePw(nowPw,newPw,loginMember);
		
		return null;
	}
	
	
	/** 회원 탈퇴
	 * 
	 * @param memberId
	 * @param memberPw 
	 * @return
	 */
	@PostMapping("secession")
	public String secession(
		@RequestParam("memberId")String memberId,
		@RequestParam("memberPw")String memberPw) {
		
		return null;
	}
	
	

}

