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

	
	

}