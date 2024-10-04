package kr.co.hanbit.product.management.presentation;

import kr.co.hanbit.product.management.domain.Product;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    // 상품 추가
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public Product createProduct(@RequestBody Product product) {
        // Product를 생성하고 리스트에 넣는 작업
        return product;
    }
}
