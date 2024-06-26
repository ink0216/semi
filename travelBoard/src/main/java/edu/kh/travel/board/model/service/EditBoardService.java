package edu.kh.travel.board.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.travel.board.model.dto.Board;
import edu.kh.travel.board.model.dto.Country;

public interface EditBoardService {

	/**해당 대륙에 있는 대륙 이름,코드 얻어오는 서비스
	 * @param contiCode
	 * @return countryList
	 */
	List<Country> countryList(String contiCode);

	/**게시글 작성 insert
	 * @param inputBoard
	 * @param images
	 * @return
	 */
	int boardInsert(Board inputBoard, List<MultipartFile> images)throws IllegalStateException, IOException;

	/**게시글 수정
	 * @param inputBoard
	 * @param images
	 * @param deleteOrder
	 * @return
	 */
	int boardUpdate(Board inputBoard, List<MultipartFile> images, String deleteOrder)throws IllegalStateException, IOException;

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
