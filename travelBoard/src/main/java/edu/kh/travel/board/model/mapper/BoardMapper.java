package edu.kh.travel.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.travel.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	/**인터셉터로 게시판 종류 얻어오기
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	
	//------------------------------------------------------------------
	//게시글 목록 조회 서비스(boardList)에서 사용되는 여러 매퍼들 
	/**삭제되지 않은 게시글 수 조회
	 * @param selectContinent
	 * @return
	 */
	int getListCount(String selectContinent);

	/**특정 게시판의 지정된 페이지 게시글 목록 조회
	 * @param selectContinent
	 * @param rowBounds
	 * @return count
	 */
	List<Board> selectBoardList(String selectContinent, RowBounds rowBounds);


	/**검색 조건에 맞는 게시글 수 조회하기(카운트한 결과값이 반환된다)
	 * @param paramMap
	 * @return
	 */
	int getSearchCount(Map<String, Object> paramMap);
	


	/**지정된 페이지의 검색 결과 게시글 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return boardList
	 */
	List<Board> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);
	//------------------------------------------------------------------

}