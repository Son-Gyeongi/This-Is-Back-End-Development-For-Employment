package kr.co.hanbit.product.management.domain;

import java.util.Objects;

/*
요구사항 : 상품은 상품 번호, 상품 이름, 가격, 재고 수량이라는 네 가지 정보를 가진다.
 */
public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean sameId(Long id) {
        return this.id.equals(id);
    }

    public Boolean containsName(String name) {
        return this.name.contains(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o; // 비교할 객체를 Product 로 변환
        return Objects.equals(id, product.id); // id 값만 같다면 같은 Product 로 인식한다고 가정
    }
}
