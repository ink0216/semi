package edu.kh.travel.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.Country;

@Mapper
public interface BoardMapper {

	/**인터셉터로 게시판 종류 얻어오기
	 * @return boardTypeList
	 */
	List<Map<String, Object>> selectBoardTypeList();

	
	//------------------------------------------------------------------
	//게시글 목록 조회 서비스(boardList)에서 사용되는 여러 매퍼들 
	/**삭제되지 않은 게시글 수 조회
	 * @param contiCode
	 * @return
	 */
	int getListCount(Map<String, String> map1);

	/**특정 게시판의 지정된 페이지 게시글 목록 조회
	 * @param contiCode
	 * @param rowBounds
	 * @return count
	 */
	List<Board> selectBoardList(Map<String, String> map1, RowBounds rowBounds);


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
	
	/**해당 게시판에 존재하는 게시글의 나라 이름 종류 조회
	 * @param contiCode
	 * @return
	 */
	List<Country> countryList(String contiCode);
	//------------------------------------------------------------------


	/**해당 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectOne(Map<String, Object> map);


	/**조회 수 1 증가시키기
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);


	/**조회수를 조회
	 * @param boardNo
	 * @return
	 */
	int selectReadCount(int boardNo);


	/**해당 게시글의 국가명 조회
	 * @param boardNo
	 * @return
	 */
	String countryName(int boardNo);


	/**좋아요 삭제
	 * @param map
	 * @return
	 */
	int deleteBoardLike(Map<String, Integer> map);


	/**좋아요 삽입
	 * @param map
	 * @return
	 */
	int insertBoardLike(Map<String, Integer> map);


	/**해당 게시글의 좋아요 수 조회
	 * @param integer
	 * @return
	 */
	int selectLikeCount(Integer integer);

	// ---------------------------------------------------------------
	/**헤더 검색창에서 게시글 제목으로 검색(대륙에 상관 없이)한 게시글 수 얻어오기
	 * @param query1
	 * @return
	 */
	int searchAllCount(String query1);


	/**헤더 검색창에서 게시글 제목으로 검색(대륙에 상관 없이)한 게시글 리스트 조회
	 * @param query1
	 * @param rowBounds
	 * @return
	 */
	List<Board> searchAllList(String query1, RowBounds rowBounds);


	

}