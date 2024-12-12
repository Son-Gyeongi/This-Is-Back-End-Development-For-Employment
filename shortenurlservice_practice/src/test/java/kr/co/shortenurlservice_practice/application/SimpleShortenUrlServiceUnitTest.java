package kr.co.shortenurlservice_practice.application;

import kr.co.shortenurlservice_practice.domain.ShortenUrl;
import kr.co.shortenurlservice_practice.domain.ShortenUrlRepository;
import kr.co.shortenurlservice_practice.domain.exception.LackOfShortenUrlKeyException;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlCreateRequestDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * LackOfShortenUrlKeyException 예외 발생에 대한 단위 테스트 추가
 * - 단위 테스트 하는 이유 : 레포지토리가 하는 행위를 테스트 코드 내에서 임의로 지정해주는 편이 테스트 코드 작성에 유리하기 때문
 * 즉, 모킹을 해주는 것이 적절하다는 의미
 */
@ExtendWith(MockitoExtension.class)
public class SimpleShortenUrlServiceUnitTest {

    @Mock
    private ShortenUrlRepository shortenUrlRepository;

    @InjectMocks
    private SimpleShortenUrlService simpleShortenUrlService;

    @Test
    @DisplayName("단축 URL 이 계속 중복되면 LackOfShortenUrlKeyException 예외가 발생해야한다.")
    void throwLackOfShortenUrlKeyExceptionTest() {
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(null);

        // ShortenUrl 인스터스가 조회된다.
        when(shortenUrlRepository.findShortenUrlByShortenUrlKey(any())).thenReturn(new ShortenUrl(null, null));

        Assertions.assertThrows(LackOfShortenUrlKeyException.class, () -> {
            simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        });
    }
}
