package kr.co.shortenurlservice.domain;

/**
 * 예외 정의하고 처리하기
 * 생성된 단축 URL 이 중복되는 상황에 대한 예외 추가
 * - shortenUrlKey 자원이 부족하여 일정 횟수 이상 중복됐을 때 발생하는 예외
 */
public class LackOfShortenUrlKeyException extends RuntimeException {

}
