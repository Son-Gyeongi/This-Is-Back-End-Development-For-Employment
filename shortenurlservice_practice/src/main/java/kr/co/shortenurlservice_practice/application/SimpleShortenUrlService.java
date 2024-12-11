package kr.co.shortenurlservice_practice.application;

import kr.co.shortenurlservice_practice.domain.ShortenUrl;
import kr.co.shortenurlservice_practice.domain.ShortenUrlRepository;
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
        String shortenUrlKey = ShortenUrl.generateShortenUrlKey();

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(shortenUrl);
        return shortenUrlCreateResponseDto;
    }

    // 단축 URL 정보 조회
    public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);

        return shortenUrlInformationDto;
    }

    // 단축 URL 리다이렉트
    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        String originalUrl = shortenUrl.getOriginalUrl();

        return originalUrl;
    }
}
