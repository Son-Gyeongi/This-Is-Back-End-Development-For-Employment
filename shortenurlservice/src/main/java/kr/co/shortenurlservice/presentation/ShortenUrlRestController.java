package kr.co.shortenurlservice.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 과제 테스트 - 4. 컨트롤러 추가
 * <p>
 * 요구사항에서 언급한 것처럼 단축 URL 에는 세 가지 API 가 필요할 것으로 보인다.
 * 가. 단축 URL 생성 API -> /shortenUrl, POST
 * 나. 단축 URL 리다이렉트 API -> /{shortenUrlKey}, GET (사용자가 직접 입력하는 API 로 자원의 주소인 shortenUrl 제거)
 * 다. 단축 URL 정보 조회 API -> /shortenUrl/{shortenUrlKey}, GET
 */
@RestController
public class ShortenUrlRestController {

    // 단축 URL 생성 API
    @RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
    public ResponseEntity<?> createShortenUrl() {
        return ResponseEntity.ok().body(null);
    }

    // 단축 URL 리다이렉트 API
    @RequestMapping(value = "/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectShortenUrl() {
        return ResponseEntity.ok().body(null);
    }

    // 단축 URL 정보 조회 API
    @RequestMapping(value = "/shortenUrl/{shortenUrlKey}", method = RequestMethod.GET)
    public ResponseEntity<?> getShortenUrlInformation() {
        return ResponseEntity.ok().body(null);
    }
}
