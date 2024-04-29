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


}
