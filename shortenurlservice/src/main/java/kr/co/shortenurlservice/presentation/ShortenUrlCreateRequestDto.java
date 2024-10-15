package kr.co.shortenurlservice.presentation;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

/**
 * 과제 테스트 - 5. DTO 추가
 * 단축 URL 생성 API - 요청 정보
 */
public class ShortenUrlCreateRequestDto {
    /*
    @NotNull
    - 컨트롤러의 @Valid 애너테이션과 함께 널 체크와 같은 기본적인 유효성에 대한 검사를 진행

    URL 의 경우 형태가 바뀔 일이 없고, 딱히 도메인과 관련된 지식도 아니기 때문에 컨트롤러에서 유효성을 검사해 주는 편이 적절하다.
     */
    @NotNull
    @URL(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String originalUrl;

    public ShortenUrlCreateRequestDto() {
    }

    public ShortenUrlCreateRequestDto(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
