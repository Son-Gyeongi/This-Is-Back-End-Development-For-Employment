package kr.co.shortenurlservice_practice.domain;

import lombok.Getter;

import java.util.Random;

@Getter
public class ShortenUrl {
    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;

    public ShortenUrl(String originalUrl, String shortenUrlKey) {
        this.originalUrl = originalUrl;
        this.shortenUrlKey = shortenUrlKey;
        this.redirectCount = 0L;
    }
    
    // 8글자 단축 URL 생성 - Base56 인코딩 방식
    public static String generateShortenUrlKey() {
        String base56Characters = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
        Random random = new Random();
        StringBuilder shortenUrlKey = new StringBuilder();

        for (int count = 0; count < 8; count++) {
            int base56CharactersIndex = random.nextInt(0, base56Characters.length());
            char base56Character = base56Characters.charAt(base56CharactersIndex);
            shortenUrlKey.append(base56Character);
        }

        return shortenUrlKey.toString();
    }

    public void increaseRedirectCount() {
        // ShortenUrl 의 redirectCount 를 1 증가 시킨다.
        this.redirectCount = this.redirectCount + 1;
    }
}
