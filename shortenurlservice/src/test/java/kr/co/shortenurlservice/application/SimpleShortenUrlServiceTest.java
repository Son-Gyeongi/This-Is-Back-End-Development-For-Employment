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

    @Test
    @DisplayName("존재하지 않는 단축 URL 을 조회하는 경우")
    void findShortenUrlNotExistIdTest() {
        String noExistShortenUrl = "JWmSFaLV";

        // 예외 발생 테스트 assertThrows - 인자 2개 (기대하는 예외, 예외가 발생해야 할 코드를 넣을 수 있는 람다 표현식)
        assertThrows(NotFoundShortenUrlException.class, () -> {
            simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(noExistShortenUrl);
        });
    }
}