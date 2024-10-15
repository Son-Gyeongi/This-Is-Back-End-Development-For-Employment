package kr.co.shortenurlservice.application;

import kr.co.shortenurlservice.domain.NotFoundShortenUrlException;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 과제 테스트 - 9. 테스트 코드 추가
 *
 * 단축 URL 을 생성한 후 조회되는지 테스트 (통합 테스트)
 */
@SpringBootTest
class SimpleShortenUrlServiceTest {

    @Autowired
    private SimpleShortenUrlService simpleShortenUrlService;

    @Test
    @DisplayName("URL 을 단축한 후 단축된 URL 키로 조회하면 원래 URL 이 조회되어야 한다.")
    void shortenUrlAddTest() {
        String expectedOriginalUrl = "https://www.hanbit.co.kr/";
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto =
                new ShortenUrlCreateRequestDto(expectedOriginalUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto =
                simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        String shortenUrlKey = shortenUrlCreateResponseDto.getShortenUrlKey();

        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        assertTrue(originalUrl.equals(expectedOriginalUrl));
    }
}