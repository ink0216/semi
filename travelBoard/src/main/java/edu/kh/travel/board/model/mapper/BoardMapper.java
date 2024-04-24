package edu.kh.travel.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.travel.board.model.dto.Board;

@Mapper
public interface BoardMapper {

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
