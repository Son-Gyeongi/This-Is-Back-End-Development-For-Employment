package kr.co.shortenurlservice.domain;

/**
 * 과제 테스트 - 3. 데이터 정의
 */
public class ShortenUrl {
    private String originalUrl; // 원래의 URL
    private String shortenUrlKey; // 단축된 URL - URL 이 아니라 '4h2Ddzo' 와 같은 키 값이 들어간다.
    private Long redirectCount; // 리다이렉트 카운트
}
