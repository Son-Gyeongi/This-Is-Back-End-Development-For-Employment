package kr.co.shortenurlservice_practice.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.shortenurlservice_practice.application.SimpleShortenUrlService;
import kr.co.shortenurlservice_practice.domain.ShortenUrl;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlCreateResponseDto;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlInformationDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 컨트롤러에 대한 단위 테스트 추가
 * @WebMvcTest - 컨트롤러를 단위 테스트 하기 위해 필요
 */
@WebMvcTest(controllers = ShortenUrlRestController.class) // 테스트할 컨트롤러 지정
class ShortenUrlRestControllerTest {

    @MockBean
    private SimpleShortenUrlService simpleShortenUrlService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("원래의 URL 로 리다이렉트 되어야 한다.")
    void redirectTest() throws Exception {
        String expectedOriginalUrl = "https://www.hanbit.co.kr/";

        when(simpleShortenUrlService.getOriginalUrlByShortenUrlKey(any())).thenReturn(expectedOriginalUrl);

        mockMvc.perform(get("/any-key"))
                .andExpect(status().isMovedPermanently())
                .andExpect(header().string("Location", expectedOriginalUrl));
    }

    @Test
    @DisplayName("originalUrl 을 shortenUrlKey 로 만들어준다.")
    void createShortenUrlTest() throws Exception {
        String expectedOriginalUrl = "https://www.hanbit.co.kr/";
        String expectedShortenUrlKey = "3dfKuPQc";
        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto
                = new ShortenUrlCreateResponseDto(new ShortenUrl(expectedOriginalUrl, expectedShortenUrlKey));

        when(simpleShortenUrlService.generateShortenUrl(any())).thenReturn(shortenUrlCreateResponseDto);

        Map<Object, Object> request = new HashMap<>();
        request.put("originalUrl", expectedOriginalUrl);

        ResultActions resultActions = mockMvc.perform(post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print());
        // Map 으로 만든 input 을 json 형식의 string 으로 만들기 위해 objectMapper 사용 - 참고 https://kimdozzi.tistory.com/252

        resultActions.andExpect(status().isOk())
                .andExpect(handler().methodName("createShortenUrl"))
                .andExpect(jsonPath("$.originalUrl").value(expectedOriginalUrl))
                .andExpect(jsonPath("$.shortenUrlKey").value(expectedShortenUrlKey));
    }

    @Test
    @DisplayName("단축한 URL 정보 조회")
    void getShortenUrlInformationTest() throws Exception {
        String expectedOriginalUrl = "https://www.hanbit.co.kr/";
        String expectedShortenUrlKey = "3dfKuPQc";
        Long redirectCount = 1L;
        ShortenUrlInformationDto shortenUrlInformationDto
                = new ShortenUrlInformationDto(expectedOriginalUrl, expectedShortenUrlKey, redirectCount);

        when(simpleShortenUrlService.getShortenUrlInformationByShortenUrlKey(any())).thenReturn(shortenUrlInformationDto);

        ResultActions resultActions = mockMvc.perform(get("/shortenUrl/any-key"))
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(handler().methodName("getShortenUrlInformation"))
                .andExpect(jsonPath("$.originalUrl").value(expectedOriginalUrl))
                .andExpect(jsonPath("$.shortenUrlKey").value(expectedShortenUrlKey))
                .andExpect(jsonPath("$.redirectCount", instanceOf(Number.class)));
    }
}