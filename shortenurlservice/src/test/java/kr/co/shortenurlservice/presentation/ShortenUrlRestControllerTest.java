package kr.co.shortenurlservice.presentation;

import kr.co.shortenurlservice.application.SimpleShortenUrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 과제 테스트 - 9. 테스트 코드 추가
 *
 * 컨트롤러 MOVED_PERMANENTLY, Location 헤더 테스트 (컨트롤러 단위 테스트)
 */
/*
@WebMvcTest
- 컨트롤러를 단위 테스트하기 위해 필요한 애너테이션
- 매개변수로 지정해 준 controllers 라는 값에 따라 테스트할 컨트롤러를 지정
- 컨트롤러에 대한 단위 테스트에서는 서비스에 대한 단위 테스트와는 달리
    컨트롤러에 대한 빈을 실제로 생성한다.
- 해당 코드와 같이 지정해 주면 ShortenUrlRestController 에 대한 빈을 생성하여 테스트 코드가 실행된다.
    -> 빈이 생성된다는 의미는 스프링 애플리케이션 컨테이너가 생성되고, 그 안에 컨트롤러에 대한 빈도 생성된다는 의미
    -> 즉, 컨트롤러에 대한 단위 테스트지만, 통합 테스트처럼 애플리케이션이 실제로 실행되는 것과 동일한 효과를 낼 수 있다.
 */
@WebMvcTest(controllers = ShortenUrlRestController.class)
class ShortenUrlRestControllerTest {

    /*
    @MockBean
    - @Mock 애너테이션과 비슷한 역할을 하지만 조금 다르다.
    - 컨트롤러에 대한 단위 테스트는 스프링 애플리케이션 컨테이너가 실행되기 때문에 컨테이너에 빈을 생성해 줘야만
        컨트롤러에서 해당 빈을 주입받아서 코드를 실행시킬 수 있다.
    - @Mock 의 경우 스프링 애플리케이션 컨테이너와는 무관하게,
        @InjectMocks 애너테이션이 붙은 곳으로 의존성을 집어 넣을 수 있었다는 차이가 있다.
     */
    @MockBean
    private SimpleShortenUrlService simpleShortenUrlService;

    /*
    MockMvc 클래스
    - 컨트롤러를 테스트하기 위한 기능을 가지고 있는 클래스
    - 컨트롤러를 테스트할 때는 컨트롤러의 메서드를 직접 호출하는 것이 아니라
        MockMvc 를 통해 테스트해야 한다.
        그래야 실제로 웹 애플리케이션 서버와 통신하는 것처럼 테스트할 수 있기 때문이다.
    - 우리가 컨트롤러를 테스트하는 목적은 HTTP 로 클라이언트의 요청에 적절한 응답을 주는지 테스트하는 것이다.
        이러한 기능을 수행하기 위해서는 MockMvc 를 주입받아서 테스트해야 하며,
        MockMvc 에 대한 빈은 @WebMvcTest 애너테이션으로 테스트 코드를 실행하는 경우에 생성된다.
    - @Autowired 를 통해 MockMvc 빈을 주입받을 수 있다.
     */
    @Autowired
    private MockMvc mockMvc;

    /*
    단축 URL 로 조회하여 상태 코드로 MOVED_PERMANENTLY 가 반환되고,
    헤더에 Location 으로 우리가 지정해 준 originalUrl 이 넘어오는지를 테스트
     */
    @Test
    @DisplayName("원래의 URL 로 리다이렉트 되어야한다.")
    void redirect() throws Exception {
        String expectedOriginalUrl = "https://www.hanbit.co.kr/";

        // SimpleShortenUrlService 는 빈이 생성되지만, 정체는 목 객체이므로 모킹을 해줘야 한다.
        // 테스트 대상이 아니기 때문에 목 객체를 넣어 줬고, 그에 따라 모킹해 줬다.
        when(simpleShortenUrlService.getOriginalUrlByShortenUrlKey(any())).thenReturn(expectedOriginalUrl);

        // MockMvc 를 통해 클라이언트가 서버로 요청을 보낸 것 처럼 호출해 준다.
        mockMvc.perform(get("/any-key"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", expectedOriginalUrl));
    }
}