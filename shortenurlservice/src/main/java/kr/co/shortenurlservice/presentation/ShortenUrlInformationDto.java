package kr.co.shortenurlservice.presentation;

/**
 * 과제 테스트 - 5. DTO 추가
 * 단축 URL 정보 조회 API - 응답 정보
 */
public class ShortenUrlInformationDto {
    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenUrlKey() {
        return shortenUrlKey;
    }

    public Long getRedirectCount() {
        return redirectCount;
    }
}
