package kr.co.shortenurlservice.application;

import kr.co.shortenurlservice.domain.LackOfShortenUrlKeyException;
import kr.co.shortenurlservice.domain.NotFoundShortenUrlException;
import kr.co.shortenurlservice.domain.ShortenUrl;
import kr.co.shortenurlservice.domain.ShortenUrlRepository;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice.presentation.ShortenUrlCreateResponseDto;
import kr.co.shortenurlservice.presentation.ShortenUrlInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 과제 테스트 - 6. 서비스 코드 추가
 */
@Service
public class SimpleShortenUrlService {

    private ShortenUrlRepository shortenUrlRepository;

    @Autowired
    SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    /*
    단축 URL 생성 기능 추가 - 단축 URL 생성 로직

    a. 먼저 단축 URL 키를 생성
    ** 요구사항 **
    - 단축된 URL 의 키는 8글자로 생성되어야 합니다. '단축된 URL 의 키'는 'https://bit.ly/3onGWgK' 에서 경로(Path)에
        해당하는 '3onGWgK'를 의미합니다. bitly 에서는 7글자의 키를 사용합니다.
    - 키 생성 알고리즘은 자유롭게 구현하시면 됩니다.
     */
    public ShortenUrlCreateResponseDto generateShortenUrl(
            ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        /*
        해야 할 일
        가. 단축 URL 키(Key) 생성
        나. 원래의 URL 과 단축 URL 키를 통해 ShortenUrl 도메인 객체 생성
        다. 생성된 ShortenUrl 을 레포지토리를 통해 저장
        라. ShortenUrl 을 ShortenUrlCreateResponseDto 로 변환하여 반환
         */

        String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
//        String shortenUrlKey = ShortenUrl.generateShortenUrlKey(); // 가
        /*
        가. shortenUrlKey 가 중복될 경우 일정 횟수까지 반복하여 shortenUrlKey 를 생성한 후 예외를 던지는 코드 추가
        - 반복이 많이 생긴다는 건 shortenUrlKey 자원이 굉장히 부족해진 상황이므로 계속 중복이 발생할 확률이 높다.
        - 따라서 몇 번 재시도한 후 해당 횟수를 넘어가면 그냥 예외를 던지고 개발자에게 자원이 부족하다고 알려 주는 편이 더 적절하다.
         */
        String shortenUrlKey = getUniqueShortenUrlKey();

        // 나
        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        // 다
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        // 라
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(shortenUrl);
        return shortenUrlCreateResponseDto;
    }

    /*
    단축 URL 정보 조회 기능 추가
     */
    public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        /*
        조회된 단축 URL 이 없는 경우, 즉 ShortenURL 의 값이 null 인 경우에 NotFoundShortenUrlException 예외가 던져진다.
         */
        if (shortenUrl == null)
            throw new NotFoundShortenUrlException();

        ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);

        return shortenUrlInformationDto;
    }

    /*
    단축 URL 리다이렉트 기능 추가
     */
    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        /*
        조회된 단축 URL 이 없는 경우, 즉 ShortenURL 의 값이 null 인 경우에 NotFoundShortenUrlException 예외가 던져진다.
         */
        if (shortenUrl == null)
            throw new NotFoundShortenUrlException();

        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        String originalUrl = shortenUrl.getOriginalUrl();

        return originalUrl;
    }

    private String getUniqueShortenUrlKey() {
        final int MAX_RETRY_COUNT = 5;
        int count = 0;

        while (count++ < MAX_RETRY_COUNT) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
            ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

            if (shortenUrl == null)
                return shortenUrlKey;
        }

        throw new LackOfShortenUrlKeyException();
    }
}
