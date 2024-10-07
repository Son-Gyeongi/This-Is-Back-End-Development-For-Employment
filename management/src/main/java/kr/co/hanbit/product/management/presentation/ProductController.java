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
    public ProductDto findProductById(@PathVariable Long id) {
        return simpleProductService.findById(id);
    }

    // 전체 상품 목록 조회
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findAllProduct() {
        return simpleProductService.findAll();
    }
}
