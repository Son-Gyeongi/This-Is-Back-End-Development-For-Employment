package kr.co.hanbit.product.management.application;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service // 스프링 프레임워크가 해당 클래스를 인스턴스화하여 빈으로 등록한다.
@Validated // 해당 클래스에 있는 메서드들 중 @Valid 가 붙은 메서드 매개변수를 유효성 검사하겠다는 의미이다.
public class ValidationService {
    public <T> void checkValid(@Valid T validationTarget) {
        // T 는 제네릭으로 어떤 타입이든 올 수 있다.

        // do nothing - checkValid 로 인자를 담아 호출하는 것만으로 유효성에 대한 검증이 이루어진다.
    }
}
