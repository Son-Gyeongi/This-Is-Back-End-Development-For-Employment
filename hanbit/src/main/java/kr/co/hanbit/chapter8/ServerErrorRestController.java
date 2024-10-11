package kr.co.hanbit.chapter8;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    서버측 에러를 의미하는 500 상태 코드
    - 서버에서 요청을 처리하는 과정에서 문제가 발생했다는 의미
 */
@RestController
public class ServerErrorRestController {

    @RequestMapping("/throwServerError")
    public void throwServerError() {
        throw new RuntimeException();
    }
}
