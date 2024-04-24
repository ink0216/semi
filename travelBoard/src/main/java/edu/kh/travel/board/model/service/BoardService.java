package edu.kh.travel.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.travel.board.model.dto.Board;

public interface BoardService {

	/**인터셉터로 게시판 종류 얻어오기
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/**해당 대륙 게시판으로 이동
	 * @param selectContinent
	 * @return
	 */
	List<Board> boardList(String selectContinent);

}
