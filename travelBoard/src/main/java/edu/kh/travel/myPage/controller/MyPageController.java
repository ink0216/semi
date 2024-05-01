package edu.kh.travel.myPage.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.member.model.dto.Member;
import edu.kh.travel.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"loginMember"})
@Controller
@RequiredArgsConstructor
@RequestMapping("myPage")
public class MyPageController {
	
	private final MyPageService service;

// -----페이지 이동------
	// 사이드 메뉴 페이지 이동 컨트롤러

	// 프로필 페이지 이동
	@GetMapping("profile")
	public String profile(
		@SessionAttribute("loginMember")Member loginMember,
		Model model
		) {
		String memberAddress = loginMember.getMemberAddress();
		
		if(memberAddress !=null) {
			
			String[]arr =memberAddress.split("\\^\\^\\^");
			
			model.addAttribute("address",arr[1]);
			model.addAttribute("detailAddress",arr[2]);
		}
		return "myPage/myPage-profile";
	}
	
	// 회원정보 변경 페이지 이동
	@GetMapping("info")
	public String info(
		@SessionAttribute("loginMember")Member loginMember,
		Model model
		) {
		
		String memberAddress = loginMember.getMemberAddress();
		
		if(memberAddress !=null) {
			
			String[]arr =memberAddress.split("\\^\\^\\^");
			
			model.addAttribute("postcode", arr[0]);
			model.addAttribute("address", arr[1]);
			model.addAttribute("detailAddress", arr[2]);
		}
		
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
		
		int result = service.updateProfile(profileImg,loginMember);
		
		String message = null;
		
		if(result >0) {
			message = "변경 성공";
		}else message="변경 실패";
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:profile";
	}
	
	
	/** 회원정보 수정
	 * @return
	 */
	@PostMapping("info")
	public String updateInfo(
		Member inputMember,
		@SessionAttribute("loginMember")Member loginMember,
		@RequestParam ("memberAddress") String[] memberAddress,
		RedirectAttributes ra
			) {
		
		// 로그인회원번호 추가
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		// 회원 정보 수정 서비스 호출
		int result = service.updateInfo(inputMember,memberAddress);
		
		String message = null;
		
		if(result >0) {
			message = "회원정보 수정 성공";
			
			loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberTel(inputMember.getMemberTel());
			loginMember.setMemberAddress(inputMember.getMemberAddress());
		}else {
			message = "회원정보 수정 실패";
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:info";
	}
	
	
	
	/** 비밀번호 수정
	 * @param nowPw
	 * @param newPw
	 * @param loginMember
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(
		@RequestParam Map<String, Object> map,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra) {
		
		int memberNo = loginMember.getMemberNo();
		
		int result = service.changePw(map,memberNo);
		
		String message = null;
		String path = null;
		
		if(result >0) {
			message = "비밀번호가 변경되었습니다";
			path = "myPage/profile";
		} else {
			message = "비밀번호가 일치하지 않습니다";
			path = "myPage/changePw";
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:/" + path;
		
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
		@RequestParam("memberPw")String memberPw,
		@SessionAttribute("loginMember")Member loginMember,
		RedirectAttributes ra,
		SessionStatus status) {
		
		int result = service.secession(memberId, memberPw, loginMember);

		String message = null;
		
		if(result>0) {
			message = "탈퇴 되었습니다";
			ra.addFlashAttribute("message", message);
			status.setComplete();
			return "redirect:/";
		}else {
			message = "아이디or비밀번호가 일치하지 않습니다";
			ra.addFlashAttribute("message", message);
			return "redirect:/myPage/secession";
		}
		
	}
	

	// 내가쓴 글 목록 조회...??
	@PostMapping("wrting")
	public String wrtingList() {
		
		return null;
	}
	
	
	
	
	
	

}

