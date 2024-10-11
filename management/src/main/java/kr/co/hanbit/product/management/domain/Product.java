package kr.co.hanbit.product.management.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.util.Objects;

/*
요구사항 : 상품은 상품 번호, 상품 이름, 가격, 재고 수량이라는 네 가지 정보를 가진다.
 */
public class Product {
    private Long id;

    /*
    요구사항 : 상품 이름은 1글자 이상 ~ 100글자 이하의 문자열로, 동일한 상품 이름을 가지는 상품이 존재할 수 있다.
     */
    @Size(min = 1, max = 100) // 도메인 지식은 도메인 객체 내에 코드 작성
    private String name;

    /*
    요구사항 : 가격은 0원 이상 ~ 1,000,000원 이하의 값을 가질 수 있다.
     */
    @Max(1_000_000)
    @Min(0)
    private Integer price;

    /*
    요구사항 : 재고 수량은 0개 이상 ~ 9,999개 이하의 값을 가질 수 있다.
     */
    @Max(9_999)
    @Min(0)
    private Integer amount;

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    /*
    BeanPropertyRowMapper 가 데이터베이스에서 가져온 데이터를 Product 인스턴스로 변환하는 과정에서 setter 가 필요함
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
