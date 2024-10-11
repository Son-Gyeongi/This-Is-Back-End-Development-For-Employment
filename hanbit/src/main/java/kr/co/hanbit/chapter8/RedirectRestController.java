package kr.co.hanbit.chapter8;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

/*
    리다이렉트를 의미하는 301 상태 코드
    - 리다이렉트는 두 번의 HTTP 트랜잭션이 발생한다.
 */
@RestController
public class RedirectRestController {

    @RequestMapping("/redirectToTarget")
    public ResponseEntity redirectToTarget() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/targetOfRedirect"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping("/targetOfRedirect")
    public String targetOfRedirect() {
        return "This is Redirect";
    }
}
