package kr.co.shortenurlservice.domain;

/**
 * ShortenUrl 에 대한 레포지토리 인터페이스를 domain 계층에 추가
 */
public interface ShortenUrlRepository {
    void saveShortenUrl(ShortenUrl shortenUrl);
}
