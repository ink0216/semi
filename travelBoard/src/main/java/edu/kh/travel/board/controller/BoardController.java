package edu.kh.travel.board.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.BoardImg;
import edu.kh.travel.board.model.service.BoardService;
import edu.kh.travel.member.model.dto.Member;
import jakarta.servlet.http.Cookie;
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
		// 해당 게시판 나라 목록 조회하는 서비스 호출 수 model에 추가로 세팅해서 보내기
		List<String> countryList = service.countryList(selectContinent);
		//넘어온 selectContinent 값에 맞는 게시판의 게시글 목록 조회하는 서비스 호출
		model.addAttribute("countryList", countryList);
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList", map.get("boardList"));
		model.addAttribute("contiCode", selectContinent);
		return "board/boardList";
	}
//	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}") // /board/1/1998?cp=1 이런 식으로 요청 온다(상세 조회 요청 주소 모양)
//	@GetMapping("/{selectContinent:[A-Z]{2}}")
	//게시글 상세 조회
	// /board/AS/6 상세 조회 요청 주소!
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
			) throws ParseException {
		//게시글 상세 조회 서비스 호출
		//묶어서 보내기 가장 쉬운 것은 Map!!
		Map<String, Object> map = new HashMap<>();
		map.put("boardNo", boardNo);
		map.put("contiCode", contiCode);
		
		Board board = service.selectOne(map);
		String path=null;
		if(board ==null) {//조회 결과가 없는 경우
			//글을 즐겨찾기 해놨는데 게시글 작성자가 그 글을 삭제한 경우
			//조회 결과가 없어짐 
			//리스트는 isEmpty로 하는데, board는 하나니까 null로 함
			path="redirect:/board/"+contiCode; //해당 게시판 목록으로 다시 날려버리겠다
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다.");
		}else {//조회 결과가 있을 경우
			
			/************* 쿠키를 이용한 조회수 증가(시작) **************/
			// 1. 비회원(로그인 안 한 회원)이 조회했거나 로그인한 회원의 글이 아닌 경우
			// 자신이 쓴 글을 읽을 때에는 조회수 안늘리게 만들겠다
			// 1번은 글쓴이를 뺀 다른 사람인 경우임!!!!
			if(loginMember == null || 
					loginMember.getMemberNo() !=board.getMemberNo()
					//게시글 쓴 사람의 회원번호(board.getMemberNo())랑 로그인한 회원의 회원 번호(loginMember.getMemberNo())가 다를 때
					) {
				//요청에 담겨있는 모든 쿠키 얻어오기
				Cookie[] cookies = req.getCookies(); //요청에 담겨있는 쿠키들을 다 가져옴 -> 여러 개여서 배열 형태로 반환된다
				Cookie c = null;
				for(Cookie temp : cookies) {
					//쿠키에서 이름이 있어서 그 이름을 꺼내왔을 때 
					if(temp.getName().equals("readBoardNo")) { // getName 하면 name값 얻어옴
						//요청에 담긴 쿠키에 "readBoardNo"라는 쿠키가 존재할 때
						c = temp; //존재할 때에만 그 값을 c에 저장해놓겠다
						break;
					}
				}
				int result=0; //조회 수 증가 결과를 저장할 변수
				
				if(c ==null) {
					// "readBoardNo"가 요청 받은 쿠키에 없을 때 
					//없으니까 새로 만들기
					//쿠키는 name, value로 넣어줄 수 있다
					
					//새 쿠키 생성("readBoardNo", [내가 오늘 읽은 게시글 번호 나열해서 쓴 것])
					c= new Cookie("readBoardNo", "["+boardNo+"]"); //@PathVariable로 넣어준 boardNo
					result = service.updateReadCount(boardNo);
					
					
				}else {
					// "readBoardNo"가 요청 받은 쿠키에 존재할 때
					// 새 쿠키 생성("readBoardNo", [20][30][400][2000]) 이런 식으로 읽은 게시물 번호를 점점 늘려나갈거다
					if(c.getValue().indexOf("["+boardNo+"]")==-1) {
						//다른 글 읽었는데 현재 글은 처음 읽은 경우
						
						//값 중에서 이거랑 똑같은 게 있는 지 찾아서 있으면 인덱스번호가 반환됨
						//값 중에서 이거랑 똑같은 게 없으면 -1이 반환됨
						
						//해당 글 번호를 쿠키에 누적해서 더함 여기서는 +=을 쓸 수 없어서
						c.setValue(c.getValue()+"["+boardNo+"]");
						result = service.updateReadCount(boardNo);
					}
				}
				//조회 수 증가 성공 시 
				if(result>0) {
					// 먼저 조회된 board의 readCount값을
					//result 값으로 변환하기
					board.setReadCount(result);//result 값으로 바꾼다
					
					//쿠키 파일에 대한 기본 설정
					// 적용 경로 설정
					c.setPath("/"); // "/" 이하 경로 요청 시(모든 요청 주소) 쿠키 서버로 전달
					//어떤 요청 주소에 대해서 쿠키를 담아서 서버로 제출을 시킬건지
					
					//쿠키에는 수명이 있는데 오늘 밤 23시 59분 59초까지만 쿠키가 유지되게 만들기
					//내일이 되면 쿠키가 사라져서 다시 조회수를 늘릴 수 있게 된다
					//하루에 한 번 씩만 특정 게시글의 조회수를 늘릴 수 있는 계산
					
					// 수명 지정
					Calendar cal = Calendar.getInstance(); // 싱글톤 패턴
					cal.add(cal.DATE, 1);

					// 날짜 표기법 변경 객체 (DB의 TO_CHAR()와 비슷)
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					// java.util.Date
					Date a = new Date(); // 현재 시간

					Date temp = new Date(cal.getTimeInMillis()); // 다음날 (24시간 후)
					// 2024-04-15 12:30:10

					Date b = sdf.parse(sdf.format(temp)); // 다음날 0시 0분 0초

					// 다음날 0시 0분 0초 - 현재 시간
					long diff = (b.getTime() - a.getTime()) / 1000; //소수점 버림을 이용해서 1초 날려버리기
					// -> 다음날 0시 0분 0초까지 남은 시간을 초단위로 반환

					c.setMaxAge((int) diff); // 수명 설정

					resp.addCookie(c); // 응답 객체를 이용해서 클라이언트에게 전달
					
					//쿠키를 이용해서 오늘 읽은 글은 또 못읽음
					//내가 작성한 글이 아닌 남이 작성한 글을 읽을 때에만 조회수 늘어남
					//한 브라우저로 올릴 수 있는 조회수는 게시글 당 하루 한번밖에 안된다
					//다른 브라우저로 들어오면 올릴 수 있다(크롬, 엣지,...)
					//그래서 같은 브라우저로 다른 사람 아이디 로그인해서 해도 조회수 안늘어난다
				}
			}
			/************* 쿠키를 이용한 조회수 증가(끝) **************/
			path="board/boardDetail"; //html로 forward
			model.addAttribute("board", board); //조회한 board를 넘긴다
			//board안에는 게시글 상세 조회 + imageList + commentList에 다 들어있다
			
			
			if(!board.getImageList().isEmpty()) {
				//조회된 이미지 목록(imageList)가 있을 경우
				//imageList는 board 안에 있다 (List 타입으로)
				//아니면 사이즈가 0이다로 해도 된다
				BoardImg thumbnail = null;
				/*우리가 imageList의 0번 인덱스를 썸네일로 하기로 우리가 임의로 정했다
				 * imageList의 0번 인덱스 == 가장 빠른 순서(imgOrder)*/
				if(board.getImageList().get(0).getImgOrder()==0) {
					//List[0] 얻어옴
					//이미지 목록의 첫 번째 행의 순서가 0이다 == 썸네일인 경우
					//썸네일이 없는 경우는, 0번 인덱스 없을 거다
					thumbnail = board.getImageList().get(0); //길게 말하기 힘드니까 thumbnail이라는 변수에 저장해둘게
					//썸네일이 
				}
				//if문 실행 안된 경우는 썸네일이 없는 경우인데, el에서는 빈칸으로 나와서 신경 안써도 된다
				model.addAttribute("thumbnail", thumbnail);
				
				//썸네일이 있을 때/없을 때
				//출력되는 이미지(썸네일 제외하고) 시작 인덱스를 계산,지정하는 코드
				model.addAttribute("start", thumbnail !=null ? 1 : 0);
				//thumbnail이 null이 아니면(존재하면) 1을 start에 넣고, null이면 0을 넣는다(삼항연산자)
				
				/*조회한 이미지 네 장의 순서가 1,2,3,4인 경우 0번이 없으면 썸네일이 없다 -> start에 0이 들어갈거다
				 * 썸네일 없을 때 화면에 앞에서부터 0,1,2,3번째에 넣어라*/
				
				//썸네일의 유무에 따라 아래의 사진 네 칸을 어떤 인덱스로 채울 지 로직을 만든거다
				
			}
		}
		return path;
	}
}