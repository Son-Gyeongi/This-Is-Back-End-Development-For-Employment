package kr.co.shortenurlservice.presentation;

import jakarta.validation.Valid;
import kr.co.shortenurlservice.application.SimpleShortenUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * 과제 테스트 - 4. 컨트롤러 추가
 *
 * 요구사항에서 언급한 것처럼 단축 URL 에는 세 가지 API 가 필요할 것으로 보인다.
 * 가. 단축 URL 생성 API -> /shortenUrl, POST
 * 나. 단축 URL 리다이렉트 API -> /{shortenUrlKey}, GET (사용자가 직접 입력하는 API 로 자원의 주소인 shortenUrl 제거)
 * 다. 단축 URL 정보 조회 API -> /shortenUrl/{shortenUrlKey}, GET
 */
@RestController
public class ShortenUrlRestController {

    private SimpleShortenUrlService simpleShortenUrlService;

    @Autowired
    ShortenUrlRestController(SimpleShortenUrlService simpleShortenUrlService) {
        this.simpleShortenUrlService = simpleShortenUrlService;
    }

    // 단축 URL 생성 API
    @RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(
            @Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto =
                simpleShortenUrlService.generateShortenUrl(shortenUrlCreateRequestDto);
        return ResponseEntity.ok().body(shortenUrlCreateResponseDto);
    }

    // 단축 URL 리다이렉트 API
    /*
    단축 URL 리다이렉트 기능 추가
    - 단축 URL 서비스의 가장 핵심 기능인 리다이렉트되는 API

    요구사항
    단축된 URL -> 원본 URL 로 '리다이렉트될 때마다 카운트가 증가'되어야 하고,
        해당 정보를 확인할 수 있는 API 가 있어야 한다.
    => 원본 URL 로 리다이렉트될 때마다 카운트가 증가되어야 한다.

    리다이렉트 기능의 핵심은 컨트롤러이다.
    a. 서비스에서 가져온 originalUrl 을 응답 헤더의 Location 으로 설정해주고,
    b. 상태 코드를 301 로 변경해주는
    두 가지 동작을 해야한다.
     */
    @RequestMapping(value = "/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortenUrl(@PathVariable String shortenUrlKey) throws URISyntaxException {
        String originalUrl = simpleShortenUrlService.getOriginalUrlByShortenUrlKey(shortenUrlKey);

        URI redirectUri = new URI(originalUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    // 단축 URL 정보 조회 API
    /*
    단축 URL 정보 조회 기능 추가
    과제 테스트 - 8. Postman 으로 테스트하면서 기능 개발
     */
    @RequestMapping(value = "/shortenUrl/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(@PathVariable String shortenUrlKey) {

        ShortenUrlInformationDto shortenUrlInformationDto =
                simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(shortenUrlKey);

        return ResponseEntity.ok().body(shortenUrlInformationDto);
    }
}
