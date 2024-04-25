package edu.kh.travel.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.travel.board.model.dto.Board;
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
	
	//해당 게시판 목록 해당 페이지로 이동+검색
	@Override
	public Map<String, Object> boardList(String selectContinent, int cp) {
		// 1. 지정된 게시판(boardCode)에서 삭제되지 않은 게시글 수를 조회 -> 그래야 총 몇 페이지 분량의 글이 있는 지 알 수 있어서!
				int listCount = mapper.getListCount(selectContinent);
				
				// 2. 1번의 결과 + cp를 이용해서
				//		Pagination 객체를 생성
				//	Pagination 객체: 게시글 목록 구성에 필요한 값을 저장한 객체(별의 별 거를 다 저장할거다)
				Pagination pagination = new Pagination(cp, listCount); 
				//Pagination 이게 만들어지면서 그 객체에 값이 대입되면서 calculate가 실행되면서 모든 필드값들이 초기화된다
				//그렇게 만들어진 객체가 pagination 변수에 저장될거다
				
				// 3. 특정 게시판의 지정된 페이지 목록 조회(공지 게시판의 2페이지 볼거다)
//				Mybatis RowBounds 객체 -> offset(지정한 값, 건너뛸 값) + Limit(몇 개 조회할 지)
//				offset=20을 이용해서 조회 -> 20개 행의 범위를 건너뜀 -> 1~20은 몇 칸 건너뛰고 몇 개를 조회할 지
		//
//				(offset, Limit)
//				1페이지 == 0,10
//				2페이지 == 10,10
//				3페이지 == 20,10
//				4페이지 == 30,10
				/*ROWBOUNDS 객체 (Mybatis(JDBC 프레임워크(긴 JDBC를 쉽게 쓰게 해주는 것)) 제공 객체)
				 * - 지정된 크기 (offset)만큼 건너뛰고
				 * 		제한된 크기(limit)만큼의 행을 조회하는 객체
				 * --->이걸 이용하면 페이징 처리가 굉장히 간단해짐!!!!
				 * */
				int limit = pagination.getLimit(); //limit 얻어오기
				int offset = (cp-1)*limit;
				//3페이지 보고싶으면 앞의 20개는 건너뛰고 열 개 조회하면 됨
				
				RowBounds rowBounds = new RowBounds(offset, limit); //몇 행 범위를 offset만큼 건너뛰고 몇 행만큼 볼 지
				//20, 10 -> 20개 건너뛰고 그 다음부터 10개 조회한다 ->이걸 가능하게 하는게 RowBounds이다!!!
				
				/*Mapper 메서드 호출 시
				 * - 첫 번째 매개변수 -> 무조건 SQL 에 전달할 파라미터가 됨
				 * - 두 번째 매개변수 -> RowBounds 객체 전달할 자리
				 * */
				List<Board> boardList = mapper.selectBoardList(selectContinent, rowBounds);
				
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
				//Pagination 이게 만들어지면서 그 객체에 값이 대입되면서 calculate가 실행되면서 모든 필드값들이 초기화된다
				//그렇게 만들어진 객체가 pagination 변수에 저장될거다
				
				// 3. 지정된 페이지의 검색 결과 목록을 조회하기
//						Mybatis RowBounds 객체 -> offset(지정한 값, 건너뛸 값) + Limit(몇 개 조회할 지)
//						offset=20을 이용해서 조회 -> 20개 행의 범위를 건너뜀 -> 1~20은 몇 칸 건너뛰고 몇 개를 조회할 지
				//
//						(offset, Limit)
//						1페이지 == 0,10
//						2페이지 == 10,10
//						3페이지 == 20,10
//						4페이지 == 30,10
						/*ROWBOUNDS 객체 (Mybatis(JDBC 프레임워크(긴 JDBC를 쉽게 쓰게 해주는 것)) 제공 객체)
						 * - 지정된 크기 (offset)만큼 건너뛰고
						 * 		제한된 크기(limit)만큼의 행을 조회하는 객체
						 * --->이걸 이용하면 페이징 처리가 굉장히 간단해짐!!!!
						 * */
						int limit = pagination.getLimit(); //limit 얻어오기
						int offset = (cp-1)*limit;
						//3페이지 보고싶으면 앞의 20개는 건너뛰고 열 개 조회하면 됨
						
						RowBounds rowBounds = new RowBounds(offset, limit); //몇 행 범위를 offset만큼 건너뛰고 몇 행만큼 볼 지
						//20, 10 -> 20개 건너뛰고 그 다음부터 10개 조회한다 ->이걸 가능하게 하는게 RowBounds이다!!!
						
						/*Mapper 메서드 호출 시
						 * - 첫 번째 매개변수 -> 무조건 SQL 에 전달할 파라미터가 됨
						 * - 두 번째 매개변수 -> RowBounds 객체 전달할 자리
						 * */
						List<Board> boardList = mapper.selectSearchList(paramMap, rowBounds);
						
						// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
						Map<String, Object> map = new HashMap<>();
						map.put("pagination", pagination);
						map.put("boardList", boardList);
						
						// 5. 결과 반환
						return map;
	}
	
}