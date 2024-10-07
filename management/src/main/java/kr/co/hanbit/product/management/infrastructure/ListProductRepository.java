package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ListProductRepository {

    /*
    CopyOnWriteArrayList 를 사용한 이유는
    웹 애플리케이션이 여러 개의 스레드가 동시에 동작하는 멀티 스레드라는 특수한 환경 때문에
    '스레드 세이프한' 컬렉션을 사용해야 하기 때문이다.

    ArrayList 는 스레드 세이프하지 않은, 즉 스레드 안전성이 없는 컬렉션이다.
    - 지역 변수나 매개 변수로 전달되는 리스트의 경우 보통 하나의 스레드에서만 접근하기 떄문에 스레드 안전성 보장이 필요하지 않다.
    - 그 떄 많이 사용한다.
     */
    private List<Product> products = new CopyOnWriteArrayList<>();
    
    // CopyOnWriteArrayList 와 마찬가지로 스레드 안전성을 가지는 클래스, Long 타입의 값을 안전하게 다룰 수 있다.
    // 상품 번호를 1부터 1씩 증가한다는 요구사항에 따라 1로 초기화
    private AtomicLong sequence = new AtomicLong(1L);

    // 상품 추가
    public Product add(Product product) {
        product.setId(sequence.getAndAdd(1L)); // getAndAdd() : 값을 가져온 후 1씩 증가하는 연산
        
        products.add(product);
        return product;
    }

    // 상품 한 개 조회
    public Product findById(Long id) {
        return products.stream()
                .filter(product -> product.sameId(id))
                .findFirst()
                .orElseThrow();
    }

    // 전체 상품 목록 조회
    public List<Product> findAll() {
        return products;
    }

    // 상품 이름에 포함된 문자열로 검색
    public List<Product> findByNameContaining(String name) {
        return products.stream()
                .filter(product -> product.containsName(name))
                .toList();
    }
}
