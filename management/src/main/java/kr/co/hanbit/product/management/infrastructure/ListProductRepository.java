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

    /*
    CopyOnWriteArrayList 와 마찬가지로 스레드 안전성을 가지는 클래스, Long 타입의 값을 안전하게 다룰 수 있다.
    상품 번호를 1부터 1씩 증가한다는 요구사항에 따라 1로 초기화

    요구사항 : 상품 번호는 1부터 시작하여 상품이 추가될 때마다 1씩 증가한다.
    동일한 상품 번호를 가지는 상품은 존재할 수 없다.
     */
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

    // 상품 수정하기 - 레포지토리에서 Product를 통째로 바꿔 버리는 코드
    public Product update(Product product) {
        /*
        리스트의 indexOf 메서드는 리스트의 요소 중 매개변수로 받은 인스턴스와 동일한 인스턴스의 index 를 반환한다.
        여기서 '동일하다' 라고 판단하는 기준은 해당 요소의 equals 메서드이다.
        엄밀히 말하면 '동등한 인스턴스'를 찾게 되는 것이다. (동등성 - 객체의 내용이 같은지 비교, 동일성 - 객체의 메모리 위치가 같은지 비교)
        따라서 Product 의 equals 를 오버라이딩해 줘야 한다. - id 값만 같다면 같은 Product 로 인식한다고 가정
         */
        Integer indexToModify = products.indexOf(product);
        products.set(indexToModify, product);
        return product;
    }
}
