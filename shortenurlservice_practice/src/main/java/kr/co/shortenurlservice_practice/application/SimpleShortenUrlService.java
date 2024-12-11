package kr.co.shortenurlservice_practice.application;

import kr.co.shortenurlservice_practice.domain.ShortenUrl;
import kr.co.shortenurlservice_practice.domain.ShortenUrlRepository;
import kr.co.shortenurlservice_practice.domain.exception.LackOfShortenUrlKeyException;
import kr.co.shortenurlservice_practice.domain.exception.NotFoundShortenUrlException;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlCreateResponseDto;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlInformationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleShortenUrlService {

    private final ShortenUrlRepository shortenUrlRepository;

    // 단축 URL 생성
    public ShortenUrlCreateResponseDto generateShortenUrl(
            ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        /*
        해야할 일
        1. 단축 URL 키(Key) 생성
        2. 원래의 URL 과 단축 URL 키를 통해 ShortenUrl 도메인 객체 생성
        3. 생성된 ShortenUrl 을 레포지토리를 통해 저장
        4. ShortenUrl 을 ShortenUrlCreateResponseDto 로 변환하여 반환
         */

        String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
        String shortenUrlKey = getUniqueShortenUrlKey();

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(shortenUrl);
        return shortenUrlCreateResponseDto;
    }

    private String getUniqueShortenUrlKey() {
        final int MAX_RETRY_COUNT = 5; // 일정 횟수 이상 중복됐을 때 예외 발생
        int count = 0;

        while (count++ < MAX_RETRY_COUNT) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
            ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

            if (shortenUrl == null)
                return shortenUrlKey; // shortenUrlKey 가 기존 shortenUrl 과 중복되지 않는다
        }

        throw new LackOfShortenUrlKeyException(); // 현재 중복이 5번 이상으로 shortenUrlKey 부족 현상이 시작되고 있다.
    }

    // 단축 URL 정보 조회
    public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        // 존재하지 않는 shortenUrlKey 요청했을 때
        if (shortenUrl == null)
            throw new NotFoundShortenUrlException();

        ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);

        return shortenUrlInformationDto;
    }

    // 단축 URL 리다이렉트
    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        // 존재하지 않는 shortenUrlKey 요청했을 때
        if (shortenUrl == null)
            throw new NotFoundShortenUrlException();

        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        String originalUrl = shortenUrl.getOriginalUrl();

        return originalUrl;
    }
}
