package edu.kh.travel.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.travel.board.model.dto.Board;

public interface BoardService {

	/**인터셉터로 게시판 종류 얻어오기
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	//------------------------------------------------------------------------------
	//전체 게시글 조회+검색 게시글 조회 Controller메서드(afterLogin)에서 수행하는 두 서비스
	/**해당 게시판 목록 해당 페이지로 이동
	 * @param selectContinent
	 * @param cp
	 * @return
	 */
	Map<String, Object> boardList(String selectContinent, int cp);

	/**게시글 검색 서비스
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> searchList(Map<String, Object> paramMap, int cp);

	/**해당 게시판에 존재하는 게시글의 나라 이름 종류 조회
	 * @param selectContinent
	 * @return
	 */
	List<String> countryList(String selectContinent);
	//------------------------------------------------------------------------------

	/**해당 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Board selectOne(Map<String, Object> map);

	/**조회 수 증가시키기
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

}
