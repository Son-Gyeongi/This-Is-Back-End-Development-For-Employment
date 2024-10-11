package kr.co.hanbit.chapter8;

import kr.co.hanbit.Bookmark;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    HTTP 응답 바디 - 응답 헤더의 'Content-Type' 과 아주 밀접한 관련이 있다.
    반환 타입에 맞춰 'Content-Type' 이 다르다.
    'Content-Type' 에 따라 웹 브라우저가 다르게 해석할 수 있다.
 */
@RestController
public class ContentTypeRestController {

//    @RequestMapping("/returnString")
    @RequestMapping(value = "/returnString", produces = "text/plain") // Content-Type 강제 지정
    public String returnString() {
        return "<strong>문자열</strong>을 리턴";
    }

    @RequestMapping("/returnBookmark")
    public Bookmark returnBookmark() {
        return new Bookmark();
    }
}
