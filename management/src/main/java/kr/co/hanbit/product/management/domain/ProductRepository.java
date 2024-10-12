package kr.co.hanbit.product.management.domain;

import java.util.List;

/**
 * 인터페이스에 의존하도록 코드 변경
 */
public interface ProductRepository {
    Product add(Product product);
    Product findById(Long id);
    List<Product> findAll();
    List<Product> findByNameContaining(String name);
    Product update(Product product);
    void delete(Long id);
}
