package edu.kh.travel.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.Country;
import edu.kh.travel.board.model.dto.Pagination;
import edu.kh.travel.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	private final BoardMapper mapper;
	//인터셉터로 게시판 종류 얻어오기
	@Override
		public List<Map<String, Object>> selectBoardTypeList() {
			return mapper.selectBoardTypeList();
		}
	//------------------------------------------------------------------------------
	//전체 게시글 조회+검색 게시글 조회 Controller메서드(afterLogin)에서 수행하는 두 서비스
	//해당 게시판 목록 해당 페이지로 이동+검색
	@Override
	public Map<String, Object> boardList(String contiCode, int cp,String countryCode) {
		// 1. 지정된 게시판(boardCode)에서 삭제되지 않은 게시글 수를 조회 -> 그래야 총 몇 페이지 분량의 글이 있는 지 알 수 있어서!
				Map<String, String> map1 = new HashMap<>();
				map1.put("countryCode", countryCode);
				map1.put("contiCode", contiCode);
				int listCount = mapper.getListCount(map1);
				
				// 2. 1번의 결과 + cp를 이용해서
				//		Pagination 객체를 생성
				//	Pagination 객체: 게시글 목록 구성에 필요한 값을 저장한 객체(별의 별 거를 다 저장할거다)
				Pagination pagination = new Pagination(cp, listCount); 
				
				int limit = pagination.getLimit(); //limit 얻어오기
				int offset = (cp-1)*limit;
				//3페이지 보고싶으면 앞의 20개는 건너뛰고 열 개 조회하면 됨
				
				RowBounds rowBounds = new RowBounds(offset, limit); //몇 행 범위를 offset만큼 건너뛰고 몇 행만큼 볼 지
				//20, 10 -> 20개 건너뛰고 그 다음부터 10개 조회한다 ->이걸 가능하게 하는게 RowBounds이다!!!
				
				/*Mapper 메서드 호출 시
				 * - 첫 번째 매개변수 -> 무조건 SQL 에 전달할 파라미터가 됨
				 * - 두 번째 매개변수 -> RowBounds 객체 전달할 자리
				 * */
				map1.put("contiCode", contiCode);
				List<Board> boardList = mapper.selectBoardList(map1, rowBounds);
				
				// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
				Map<String, Object> map = new HashMap<>();
				map.put("pagination", pagination);
				map.put("boardList", boardList);
				
				// 5. 결과 반환
				return map;
	}
	
	//게시글 검색 서비스
	@Override
	public Map<String, Object> searchList(Map<String, Object> paramMap, int cp) {
		// 1. 지정된 게시판(boardCode)에서 
				//		검색 조건에 맞으면서
				//		삭제되지 않은 게시글 수를 조회 -> 그래야 총 몇 페이지 분량의 글이 있는 지 알 수 있어서!
				
				// paramMap에 key, query, boardCode가 다 담겨있다
				int listCount = mapper.getSearchCount(paramMap);
				
				// 2. 1번의 결과 + cp를 이용해서
				//		Pagination 객체를 생성
				//	Pagination 객체: 게시글 목록 구성에 필요한 값을 저장한 객체(별의 별 거를 다 저장할거다)
				Pagination pagination = new Pagination(cp, listCount); 
				
						int limit = pagination.getLimit(); //limit 얻어오기
						int offset = (cp-1)*limit;
						//3페이지 보고싶으면 앞의 20개는 건너뛰고 열 개 조회하면 됨
						
						RowBounds rowBounds = new RowBounds(offset, limit); //몇 행 범위를 offset만큼 건너뛰고 몇 행만큼 볼 지
						//20, 10 -> 20개 건너뛰고 그 다음부터 10개 조회한다 ->이걸 가능하게 하는게 RowBounds이다!!!
						
						List<Board> boardList = mapper.selectSearchList(paramMap, rowBounds);
						// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
						Map<String, Object> map = new HashMap<>();
						map.put("pagination", pagination);
						map.put("boardList", boardList);
						
						// 5. 결과 반환
						return map;
	}
	
	//해당 게시판에 존재하는 게시글의 나라 이름 종류 조회
	@Override
	public List<Country> countryList(String contiCode) {
		return mapper.countryList(contiCode);
	}
	//------------------------------------------------------------------------------
	//해당 게시글 상세 조회
	public Board selectOne(Map<String, Object> map) {
		//select 세 번 할거다!
		//그러려면 서비스에서 매퍼를 세 번 호출하면 된다!
		//매퍼 메서드 하나 당 sql 하나만 수행하니까!
		// 근데 수행하려는 sql이 모두 select이면서 먼저 조회된 결과 일부를 이용해
		// 다음 sql의 조건으로 삼는 경우
		// myBatis의 동적 sql이용 가능
		return mapper.selectOne(map);
	}
	//조회 수 증가시키기
	@Override
	public int updateReadCount(int boardNo) {
		// 조회수 1 증가시키기
		int result = mapper.updateReadCount(boardNo);
		if(result>0) {
			return mapper.selectReadCount(boardNo);
		}
		return -1;
	}
	//해당 게시글의 국가명 조회
	@Override
	public String countryName(int boardNo) {
		return mapper.countryName(boardNo);
	}
	
	//게시글 좋아요 체크/해제
	@Override
	public int boardLike(Map<String, Integer> map) {
		int result = 0;
		if(map.get("likeCheck")==1) { //그 회원이 그 게시글에 좋아요 눌렀던 경우
			result = mapper.deleteBoardLike(map); //또 클릭했으니까 지우기
		}
		else { //그 회원이 그 게시글에 좋아요 안눌렀던 경우
			result = mapper.insertBoardLike(map); //또 클릭했으니까 삽입하기
		}
		if(result>0) {
			//좋아요 삭제나 삽입이 성공한 경우
			//해당 게시글의 좋아요 수 조회해서 반환해주기
			return mapper.selectLikeCount(map.get("boardNo"));
		}
		//좋아요 삭제나 삽입이 실패한 경우
		return -1;
	}
	//------------------------------------------------------------------------------
	// 헤더 검색창에서 게시글 제목으로 검색(대륙에 상관 없이)
	@Override
	public Map<String, Object> searchAll(String query1,int cp) {

		// paramMap에 key, query, boardCode가 다 담겨있다
		int listCount = mapper.searchAllCount(query1);
		
		// 2. 1번의 결과 + cp를 이용해서
		//		Pagination 객체를 생성
		//	Pagination 객체: 게시글 목록 구성에 필요한 값을 저장한 객체(별의 별 거를 다 저장할거다)
		Pagination pagination = new Pagination(cp, listCount); 
		
		int limit = pagination.getLimit(); //limit 얻어오기
		int offset = (cp-1)*limit;
		//3페이지 보고싶으면 앞의 20개는 건너뛰고 열 개 조회하면 됨
		
		RowBounds rowBounds = new RowBounds(offset, limit); //몇 행 범위를 offset만큼 건너뛰고 몇 행만큼 볼 지
		//20, 10 -> 20개 건너뛰고 그 다음부터 10개 조회한다 ->이걸 가능하게 하는게 RowBounds이다!!!
		
		List<Board> boardList = mapper.searchAllList(query1, rowBounds);
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("boardList", boardList);
		
		// 5. 결과 반환
		return map;
	}
	
}