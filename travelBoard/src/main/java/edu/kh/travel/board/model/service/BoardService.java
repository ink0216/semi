package edu.kh.travel.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.Country;

public interface BoardService {

	/**인터셉터로 게시판 종류 얻어오기
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	//------------------------------------------------------------------------------
	//전체 게시글 조회+검색 게시글 조회 Controller메서드(afterLogin)에서 수행하는 두 서비스
	/**해당 게시판 목록 해당 페이지로 이동
	 * @param contiCode
	 * @param cp
	 * @return
	 */
	Map<String, Object> boardList(String contiCode, int cp,String countryCode);

	/**게시글 검색 서비스
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> searchList(Map<String, Object> paramMap, int cp);

	/**해당 게시판에 존재하는 게시글의 나라 이름 종류 조회
	 * @param contiCode
	 * @return
	 */
	List<Country> countryList(String contiCode);
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

	/**해당 게시글의 국가명 조회
	 * @param boardNo
	 * @return
	 */
	String countryName(int boardNo);

	/**게시글 좋아요 체크/ 해제
	 * @param map
	 * @return
	 */
	int boardLike(Map<String, Integer> map);

	/**헤더 검색창에서 게시글 제목으로 검색(대륙에 상관 없이)
	 * @param query1
	 * @return
	 */
	Map<String, Object> searchAll(String query1,int cp);

}
