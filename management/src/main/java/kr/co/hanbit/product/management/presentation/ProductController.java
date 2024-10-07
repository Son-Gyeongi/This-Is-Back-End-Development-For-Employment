package kr.co.hanbit.product.management.presentation;

import kr.co.hanbit.product.management.application.SimpleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private SimpleProductService simpleProductService;

    @Autowired
        // 생성자 주입, 의존성 주입 받음
    ProductController(SimpleProductService simpleProductService) {
        this.simpleProductService = simpleProductService;
    }

    // 상품 추가
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        // Product를 생성하고 리스트에 넣는 작업
        return simpleProductService.add(productDto);
    }

    // 상품 한 개 조회
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductDto findProductById(@PathVariable Long id) { // 특정 자원 조회하는 경우 @PathVariable 사용
        return simpleProductService.findById(id);
    }

    /*
    // 전체 상품 목록 조회
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findAllProduct() {
        return simpleProductService.findAll();
    }
     */

    // // 전체 상품 목록 조회 - 상품 이름에 포함된 문자열로 검색
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findProducts(@RequestParam(required = false) String name) {
        // 상품 이름 중 특정 문자열을 포함하는 상품을 검색하는 것은 '필터 조건'에 해당하기 때문에 쿼리 파라미터 사용
        // (required = false) name 파라미터 필수로 받지 않아도 됨

        if (name == null) return simpleProductService.findAll();

        return simpleProductService.findByNameContaining(name);
    }
}
