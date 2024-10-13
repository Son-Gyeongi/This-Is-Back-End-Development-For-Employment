package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.EntityNotFoundException;
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
@ActiveProfiles("prod")
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
        assertTrue(savedProductDto.getId().equals(foundProductDto.getId()));
        /*
        name 의 타입은 String 이다. 두 인스턴스가 동일한지를 비교하고 싶은 것이 아니고 두 값을 비교해야 한다.
        동일성 - 객체의 메모리 위치가 같은지 비교
        동등성 - 객체의 내용이 같은지 비교

        ** 동일성을 비교하는 코드에서 동등성을 비교하는 코드로 바꿔 주기 **
        -> 동등성 비교를 진행하려면 equals 메서드를 사용하면 된다.
         */
        assertTrue(savedProductDto.getName().equals(foundProductDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(foundProductDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(foundProductDto.getAmount()));
    }

    /*
    예외 발생에 대한 테스트 코드 추가하기
    테스트 코드에서 반드시 포함되어야 하는 코드 중 하나는 예외 발생에 관한 것이다.

    상품 관리 애플리케이션을 만들면서 Product 조회에 실패했을 때
    발생하는 EntityNotFoundException 예외 발생에 대한 테스트 코드를 추가해 보자.
     */
    @Test
    @DisplayName("존재하지 않는 상품 id로 조회하면 EntityNotFoundException이 발생해야한다.")
    void findProductNotExistIdTest() {
        Long notExistId = -1L;

        // 예외 발생 테스트 assertThrows - 인자 2개 (기대하는 예외, 예외가 발생해야 할 코드를 넣을 수 있는 람다 표현식)
        assertThrows(EntityNotFoundException.class, () -> {
            simpleProductService.findById(notExistId);
        });
    }
    /*
    EntityNotFoundException 예외가 던져지지 않았을 때 테스트에 실패하는 것까지 확인하기

    - test ListProductRepository 의 경우 findById() 에서 Product 를 찾지 못하면 EntityNotFoundException 예외가 던져지지만
    - prod DatabaseProductRepository 의 경우 findById() 에서 EntityNotFoundException 예외가 발생하지 않는다. (EmptyResultDataAccessException 예외 발생)

    해결 : EmptyResultDataAccessException 예외를 잡아서 EntityNotFoundException 으로 변경하는 방법을 알아보자.
    -> DatabaseProductRepository 클래스에서 findById() 에 try-catch 를 사용했다.
    -> try-catch 를 많이 사용하면 코드 읽기가 어려워지므로 try-catch 는 가급적 사용하지 않는 편이 좋다.
     */
}