package kr.co.hanbit.product.management;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

    /**
     * ModelMapper
     *     - "자바에서 제공하는 '리플렉션(Reflection) API'" 를 사용하여 두 클래스 사이의 변환 기능을 제공하는 라이브러리
     *     - ModelMapper 는 자바에서 기본으로 제공하는 라이브러리가 아니기 때문에 메이븐 의존성을 추가해줘야 함
     *     - new 키워드로 ModelMapper 를 생성하는 방법도 있지만 미리 빈으로 등록한 후 의존성을 주입받아서 사용하는 것이 성능상 유리하다.
     *
     *     - main 함수가 있는 Application 클래스에 다음과 같은 코드를 추가한다.
     *       - ModelMapper 클래스의 인스턴스를 생성한 후 빈으로 등록하는 코드이다.
     */
    @Bean
    public ModelMapper modelMapper() {
        /*
        // return new ModelMapper();
        ModelMapper 를 생성할 때는 바꿔 줘야 할 것이 하나 있다.
        ModelMapper 의 기본 설정은 '매개 변수가 없는 생성자로 인스턴스를 생성한 후 setter 로 값을 초기화하여 변환'하는 것이다.
        setter 없이도 Product 와 ProductDto 를 변환 가능하도록 하려면 다음과 같은 설정으로 ModelMapper 빈을 생성해야 한다.
        이 설정은 ModelMapper 가 private 인 필드에 리플렉션 API 로 접근하여 변환할 수 있도록 만들어 준다.

        ** 리플렉션 API
        구체적인 클래스 타입을 알지 못해도 그 클래스의 정보(메서드, 타입, 변수 등등)에 접근할 수 있게 해주는 자바 API
        참고 https://tecoble.techcourse.co.kr/post/2020-07-16-reflection-api/
         */

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }

}
