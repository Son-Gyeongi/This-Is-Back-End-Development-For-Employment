package kr.co.shortenurlservice.presentation;

import kr.co.shortenurlservice.domain.ShortenUrl;

/**
 * 과제 테스트 - 5. DTO 추가
 * 단축 URL 정보 조회 API - 응답 정보
 */
public class ShortenUrlInformationDto {
    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;

    public ShortenUrlInformationDto(ShortenUrl shortenUrl) {
        this.originalUrl = shortenUrl.getOriginalUrl();
        this.shortenUrlKey = shortenUrl.getShortenUrlKey();
        this.redirectCount = shortenUrl.getRedirectCount();
    }

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
