package kr.co.shortenurlservice.presentation;

import kr.co.shortenurlservice.domain.ShortenUrl;

/**
 * 과제 테스트 - 5. DTO 추가
 * 단축 URL 생성 API - 응답 정보
 */
public class ShortenUrlCreateResponseDto {
    private String originalUrl;
    private String shortenUrlKey;

    public ShortenUrlCreateResponseDto(ShortenUrl shortenUrl) {
        this.originalUrl = shortenUrl.getOriginalUrl();
        this.shortenUrlKey = shortenUrl.getShortenUrlKey();
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortenUrlKey() {
        return shortenUrlKey;
    }
}
