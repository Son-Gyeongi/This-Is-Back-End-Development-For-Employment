package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.domain.ProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * 단위 테스트 - Mockito 로 단위 테스트 코드 작성하기
 *
 * ProductRepository 구현체 없이도 SimpleProductService 를 테스트할 수 있도록 코드 작성하기
 * Mockito 를 사용해 단위 테스트를 진행하려면 @SpringBootTest 가 아니라
 * @ExtendWith(MockitoExtension.class) 애너테이션을 붙여줘야 한다.
 *
 * @SpringBootTest 의 경우 스프링 부트 애플리케이션이 시작되면서 필요한 의존성들을 빈으로 등록하고 주입하는 과정을 거친다.
 * 반면 @ExtendWith(MockitoExtension.class) 는 스프링 부트 애플리케이션을 실행시키지 않고도 테스트 코드를 실행시킬 수 있다.
 *  통합 테스트보다 더 빠르게 실행된다.
 */
@ExtendWith(MockitoExtension.class)
public class SimpleProductServiceUnitTest {

    // SimpleProductService 의 add 메서드에 대한 단위 테스트를 하는 코드 작성
    /*
    @Mock
    해당 의존성에 '목 객체(Mock Object)'를 주입한다는 의미
    목 객체는 주입하는 것만으로는 아무 기능을 하지 않는다.
    테스트 코드에서 목 객체가 어떤 메서드에 대해 어떤 동작을 할지는 직접 정의해야 한다.

    단위 테스트를 통해 해당 인터페이스의 구현체가 없더라도 ProductRepository 역할을 하는 객체의 행동을 정의하여
    테스트할 수 있다.
    이처럼 단위 테스트는 테스트하려는 대상이 의존하고 있는 다른 요소가 존재하지 않더라도
    테스트하려는 대상을 자유롭게 테스트할 수 있다.
     */
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ValidationService validationService;

    /*
    @InjectMocks
    @Mock 으로 주입해 준 목 객체들을 SimpleProductService 내에 있는 의존성에 주입해 주는 역할을 한다.
    SimpleProductService 는 목 객체가 아니라 실제 인스턴스를 생성하여 로직으로 사용할 수 있다.
     */
    @InjectMocks
    private SimpleProductService simpleProductService;

    @Test
    @DisplayName("상품 추가 후에는 추가된 상품이 반환되어야한다.")
    void productAddTest() {
        ProductDto productDto = new ProductDto("연필", 300, 20);
        Long PRODUCT_ID = 1L;

        Product product = ProductDto.toEntity(productDto);
        product.setId(PRODUCT_ID);
        /*
        목 객체가 어떻게 행동해야 하는지를 정의한다.
        '목 객체가 when 에 해당하는 동작을 수행할 때 thenReturn 에 있는 값을 반환한다.'라는 의미이다.
        -> 'productRepository.add 메서드의 인자로 any() 가 들어가면, 반환값은 product 가 된다.' 라는 의미이다.
        -> 여기서 any() 는 아무 값이나 들어가는 것을 의미한다.
        -> productRepository.add 메서드는 어떤 파라미터를 받는지와 무관하게 product 를 반환하도록 정의
         */
        when(productRepository.add(any())).thenReturn(product);

        ProductDto savedProductDto = simpleProductService.add(productDto);

        assertTrue(savedProductDto.getId().equals(PRODUCT_ID));
        assertTrue(savedProductDto.getName().equals(productDto.getName()));
        assertTrue(savedProductDto.getPrice().equals(productDto.getPrice()));
        assertTrue(savedProductDto.getAmount().equals(productDto.getAmount()));
    }
}
