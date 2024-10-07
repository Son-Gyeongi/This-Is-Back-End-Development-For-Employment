package kr.co.hanbit.product.management.presentation;

import kr.co.hanbit.product.management.application.SimpleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
List를 이용한 상품 관리 애플리케이션
요구사항 : 상품을 관리할 수 있는 애플리케이션이다.
 */
@RestController
public class ProductController {

    private SimpleProductService simpleProductService;

    @Autowired
        // 생성자 주입, 의존성 주입 받음
    ProductController(SimpleProductService simpleProductService) {
        this.simpleProductService = simpleProductService;
    }

    /*
    상품 추가
    요구사항 : 단건으로 하나씩 상품 추가할 수 있어야 한다.
     */
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        // Product를 생성하고 리스트에 넣는 작업
        return simpleProductService.add(productDto);
    }

    /*
    상품 한 개 조회
    요구사항 : 상품 번호를 기준으로 하나의 상품을 조회할 수 있어야 한다.
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductDto findProductById(@PathVariable Long id) { // 특정 자원 조회하는 경우 @PathVariable 사용
        return simpleProductService.findById(id);
    }

    /*
    // 전체 상품 목록 조회
    // 요구사항 : 전체 상품 목록을 조회할 수 있어야 한다.
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findAllProduct() {
        return simpleProductService.findAll();
    }
     */

    /*
    전체 상품 목록 조회 - 상품 이름에 포함된 문자열로 검색
    요구사항 : 전체 상품 목록을 조회할 수 있어야 한다.
    요구사항 : 상품 이름에 포함된 특정 문자열을 기준으로 검색할 수 있어야 한다.
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findProducts(@RequestParam(required = false) String name) {
        // 상품 이름 중 특정 문자열을 포함하는 상품을 검색하는 것은 '필터 조건'에 해당하기 때문에 쿼리 파라미터 사용
        // (required = false) name 파라미터 필수로 받지 않아도 됨

        if (name == null) return simpleProductService.findAll();

        return simpleProductService.findByNameContaining(name);
    }

    /*
    상품 수정하기
    요구사항 : 상품 번호를 기준으로 상품 번호를 제외한 나머지 정보를 수정할 수 있어야 한다.
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ProductDto updateProduct(@PathVariable Long id,
                                    @RequestBody ProductDto productDto) {
        productDto.setId(id); // 클라이언트가 요청 바디로 id를 넣어 주지 않을 경우, id가 있어야 상품 수정이 정상적으로 이루어짐
        return simpleProductService.update(productDto);
    }
}
