package edu.kh.travel.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.travel.board.model.dto.Board;

public interface BoardService {

	/**인터셉터로 게시판 종류 얻어오기
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/**해당 게시판 목록 해당 페이지로 이동+검색
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

}
