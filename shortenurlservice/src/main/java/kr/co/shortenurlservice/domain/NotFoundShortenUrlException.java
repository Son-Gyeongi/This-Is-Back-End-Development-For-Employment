package kr.co.shortenurlservice.domain;

/**
 * 예외 정의하고 처리하기
 * 단축 URL 을 찾지 못하는 상황에 대한 예외 추가
 * - 클라이언트가 요청한 shortenUrlKey 를 레포지토리에서 찾을 수 없는 경우
 */
public class NotFoundShortenUrlException extends RuntimeException {

}
