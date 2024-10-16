package kr.co.shortenurlservice.application;

import kr.co.shortenurlservice.domain.LackOfShortenUrlKeyException;
import kr.co.shortenurlservice.domain.ShortenUrl;
import kr.co.shortenurlservice.domain.ShortenUrlRepository;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 과제 테스트 - 9. 테스트 코드 추가
 *
 * 예외를 검증하고 모킹하는 테스트 클래스
 * - LackOfShortenUrlKeyException 발생 테스트 (단위 테스트)
 */
@ExtendWith(MockitoExtension.class)
class SimpleShortenUrlServiceUnitTest {

    @Mock
    private ShortenUrlRepository shortenUrlRepository;

    @InjectMocks
    private SimpleShortenUrlService simpleShortenUrlService;

    /*
    LackOfShortenUrlKeyException 예외를 단위 테스트 하는 이유
    - 레포지토리가 하는 행위를 테스트 코드 내에서 임의로 지정해 주는 편이 테스트 코드 작성에 유리하기 때문
        -> 모킹을 해주는 것이 적절하다는 의미
    */
    @Test
    @DisplayName("단축 URL 이 계속 중복되면 LackOfShortenUrlKeyException 예외가 발생해야한다.")
    void throwLackOfShortenUrlKeyExceptionTest() {
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto = new ShortenUrlCreateRequestDto(null);

        // findShortenUrlByShortenUrlKey() 에 아무 값이나 들어가면 ShortenUrl 인스턴스가 반환되도록 모킹해 줬다.
        // ShortenUrlKey 가 다섯 번 중복되는 상황을 만들기 어려운데 모킹을 활용해서 테스트를 쉽게 할 수 있었다.
        when(shortenUrlRepository.findShortenUrlByShortenUrlKey(any())).thenReturn(new ShortenUrl(null, null));

        // generateShortenUrl() 메서드를 호출 했을 때 LackOfShortenUrlKeyException 이 발생하는지를 검증
        assertThrows(LackOfShortenUrlKeyException.class, () -> {
            simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        });
    }
}