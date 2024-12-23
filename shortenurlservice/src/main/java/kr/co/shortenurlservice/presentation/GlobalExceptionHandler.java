package kr.co.shortenurlservice.presentation;

import kr.co.shortenurlservice.domain.LackOfShortenUrlKeyException;
import kr.co.shortenurlservice.domain.NotFoundShortenUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 정의한 NotFoundShortenUrlException 을 보여주려면
 * 전역 예외 핸들러가 필요하다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    NotFoundShortenUrlException 에 대한 전역 예외 핸들러를 추가하고 상태 코드 404 를 응답한다.
     */
    @ExceptionHandler(NotFoundShortenUrlException.class)
    public ResponseEntity<String> handleNotFoundShortenUrlException(
            NotFoundShortenUrlException ex
    ) {
        return new ResponseEntity<>("단축 URL 을 찾지 못했습니다.", HttpStatus.NOT_FOUND);
    }

    /*
    LackOfShortenUrlKeyException 에 대한 전역 예외 핸들러를 추가
     */
    @ExceptionHandler(LackOfShortenUrlKeyException.class)
    public ResponseEntity<String> handleLackOfShortenUrlKeyException(
            LackOfShortenUrlKeyException ex
    ) {
        // 개발자에게 알려 줄 수 있는 수단 필요
        return new ResponseEntity<>("단축 URL 자원이 부족합니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
