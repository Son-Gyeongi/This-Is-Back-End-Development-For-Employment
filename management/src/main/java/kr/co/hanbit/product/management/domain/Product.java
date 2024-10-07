package kr.co.hanbit.product.management.domain;

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
}
