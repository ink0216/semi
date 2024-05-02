package edu.kh.travel.board.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.BoardImg;
import edu.kh.travel.board.model.dto.Country;

@Mapper
public interface EditBoardMapper {

	/**해당 대륙에 있는 대륙 이름,코드 얻어오는 서비스
	 * @param contiCode
	 * @return countryList
	 */
	List<Country> countryList(String contiCode);

	/**게시글 글(제목/내용) 부분만 작성(insert)
	 * @param inputBoard
	 * @return
	 */
	int boardInsert(Board inputBoard);

	/**게시글 이미지 여러 행 모두 삽입
	 * @param uploadList
	 * @return
	 */
	int insertUploadList(List<BoardImg> uploadList);
	//-------------------------------------------------------------------------------
	/**게시글 제목, 내용만 업데이트
	 * @param inputBoard
	 * @return
	 */
	int boardUpdate(Board inputBoard);

	/**게시글 이미지 수정 시 삭제된 이미지가 있는 경우 이미지 삭제
	 * @param map
	 * @return
	 */
	int deleteImage(Map<String, Object> map);

	/**게시글 이미지 새 이미지로 수정
	 * @param img
	 * @return
	 */
	int updateImage(BoardImg img);

	/**게시글에 새 이미지를 삽입 1행씩
	 * @param img
	 * @return
	 */
	int insertImage(BoardImg img);

	/**게시글 삭제
	 * @param map
	 * @return
	 */
	int boardDelete(Map<String, Object> map);
	
	/**해당 대륙코드에 맞는 대륙명 얻어오기
	 * @param contiCode
	 * @return
	 */
	String contiName(String contiCode);


	
	

}