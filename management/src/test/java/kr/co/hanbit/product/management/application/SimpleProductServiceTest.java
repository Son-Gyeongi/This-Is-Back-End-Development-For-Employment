package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.presentation.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 우리는 코드가 의도하지 않은 방향으로 달라지는 것을 알아채기 위해 테스트 코드를 돌려 봐야 한다.
 *
 * 리팩토링 목표
 * - ModelMapper 를 사용하던 코드를 제거하고 우리가 만든 코드로 Product 와  ProductDto 간 변환을 수행하는 것
 *
 * ModelMapper 를 제거하는 리팩토링을 진행하기 위해서는 해당 코드가 사용되고 있는 곳인
 * SimpleProductService 에 대한 테스트 코드를 작성하는 것이 적절하다.
 */
/*
@SpringBootTest
- '스프링 컨테이너가 뜨는 통합 테스트'를 위해 사용하는 애너테이션
- 해당 애너테이션을 사용하면 통합 테스트가 애플리케이션이 실제 실행되는 것과 마찬가지로
    빈(Bean)을 스프링 프레임워크가 생성해 준 상태에서 진행한다.
- 애플리케이션이 실제 실행되는 것처럼 테스트하기 때문에 테스트 코드가 실행될 때까지 시간이 오래 걸린다는 단점이 있다.
- 테스트 코드의 실행 시간을 줄이려면 '스프링 컨테이너가 뜨는 통합 테스트'가 아니라
    '스프링 컨테이너가 뜨지 않는 통합 테스트'를 해야 한다.

@ActiveProfiles
- 테스트 코드에서 사용할 Profile 을 지정한다.
- 코드에서는 test Profile 로 실행되는 것이므로 @Profile("test")가 붙어 있는 클래스가 빈으로 등록된다.
 */
@SpringBootTest
@ActiveProfiles("test")
class SimpleProductServiceTest {

    /*
    @Autowired
    - SimpleProductService 의존성을 주입하는 코드이다.
    - 애플리케이션 코드에서는 생성자를 통한 주입을 사용했지만, 테스트 코드에서는 필드에 바로 주입해 줘도 무관하다.
     */
    @Autowired
    SimpleProductService simpleProductService;

    /*
    @Test
    - 해당 메서드가 테스트 코드라는 것을 의미한다.

    @DisplayName
    - 해당 테스트 코드의 이름을 지정할 수 있다.

    productAddAndFindByIdTest
    메서드 이름을 영어로 지었지만, 실무에서는 한글로 작성하는 경우도 많다.
     */
    @Test
    @DisplayName("상품을 추가한 후 id로 조회하면 해당 상품이 조회되어야 한다.")
    void productAddAndFindByIdTest() {
        ProductDto productDto = new ProductDto("연필", 300, 20);

        ProductDto savedProductDto = simpleProductService.add(productDto);
        Long savedProductId = savedProductDto.getId();

        ProductDto foundProductDto = simpleProductService.findById(savedProductId);

        // 테스트 성공/실패 자동 체크
        assertTrue(savedProductDto.getId() == foundProductDto.getId());
        assertTrue(savedProductDto.getName() == foundProductDto.getName());
        assertTrue(savedProductDto.getPrice() == foundProductDto.getPrice());
        assertTrue(savedProductDto.getAmount() == foundProductDto.getAmount());
    }
}