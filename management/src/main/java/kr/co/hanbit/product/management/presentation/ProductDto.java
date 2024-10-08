package kr.co.hanbit.product.management.presentation;

import jakarta.validation.constraints.NotNull;

public class ProductDto {
    private Long id;

    /*
    @NotNull: 오직 null 만 허용하지 않는다. "" 처럼 빈 문자열이나 " " 처럼 띄어쓰기만 있는 문자열은 허용된다.
    @NotEmpty: null 과 "" 처럼 빈 문자열이 허용되지 않는다. " " 처럼 띄어쓰기가 있는 문자열은 허용된다.
    @NotBlank: null, "", " " 전부 허용되지 않는다.
     */
    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    private Integer amount;

    public void setId(Long id) {
        this.id = id;
    }

    // getter 는 HTTP 응답을 주기 위해 추가된 메서드
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }
}
