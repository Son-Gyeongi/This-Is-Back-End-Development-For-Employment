package kr.co.shortenurlservice.domain;

import java.util.Random;

/**
 * 과제 테스트 - 3. 데이터 정의
 */
public class ShortenUrl {
    private String originalUrl; // 원래의 URL
    private String shortenUrlKey; // 단축된 URL - URL 이 아니라 '4h2Ddzo' 와 같은 키 값이 들어간다.
    private Long redirectCount; // 리다이렉트 카운트

    // 문자열 2개를 받는 생성자
    public ShortenUrl(String originalUrl, String shortenUrlKey) {
        this.originalUrl = originalUrl;
        this.shortenUrlKey = shortenUrlKey;
        this.redirectCount = 0L;
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

    /*
    키 생성 알고리즘 - 숫자와 알파벳으로 이루어진 8자리의 URL 단축 키를 무작위로 생성하여 반환

    Base56 인코딩 방식에 사용되는 문자열을 사용하는 방법

    - Base56에 사용되는 문자열은 다음과 같은 집합이다.
    - 56개의 문자로 구성된 문자열 - 23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz
    - 문자열 집합을 보면 숫자, 알파벳 대소문자에서 몇 가지 문자가 빠져 있다. 빠진 문자들은 서로 비슷하게 생겨 헷갈리기 때문이다.
        - 숫자 0, 대문자 오(O), 소문자 오(o)
        - 숫자 1, 대문자 아이(I), 소문자 엘(l)

    - Base56은 인코딩 방식 중 하나로, 직접 무언가를 인코딩할 필요는 없다.
        - 해당 문자열을 사용하여 랜덤하게 단축 URL 키를 생성하면 된다.
     */
    public static String generateShortenUrlKey() {
        String base56Characters = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";

        Random random = new Random(); // Java의 Random 객체를 생성하여 무작위 숫자를 생성
        StringBuilder shortenUrlKey = new StringBuilder();

        for (int count = 0; count < 8; count++) {
            // base56Characters 문자열의 길이(56) 내에서 무작위 인덱스를 생성
            int base56CharactersIndex = random.nextInt(0, base56Characters.length());
            // 무작위로 선택된 인덱스에 해당하는 문자를 base56Characters에서 가져와 base56Character 변수에 저장
            char base56Character = base56Characters.charAt(base56CharactersIndex);
            shortenUrlKey.append(base56Character);
        }

        return shortenUrlKey.toString();
    }
}
