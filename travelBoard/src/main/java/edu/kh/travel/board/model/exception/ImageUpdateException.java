package edu.kh.travel.board.model.exception;
//이미지 수정/삽입하다가 예외 발생했다
//사용자 정의 예외 만들거다
/*이미지 수정/삽입 중 문제 발생 시 사용할 사용자 정의 예외
 * 
 * */
public class ImageUpdateException extends RuntimeException{
	public ImageUpdateException() {
		super("이미지 수정/삽입 중 예외 발생");
	}
	public ImageUpdateException(String message) {
		super(message);
	}
}
