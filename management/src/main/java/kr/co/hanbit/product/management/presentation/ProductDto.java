package kr.co.hanbit.product.management.presentation;

import jakarta.validation.constraints.NotNull;
import kr.co.hanbit.product.management.domain.Product;

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

    /*
    ProductDto(String name, Integer price, Integer amount) 생성자 추가 후 생긴 에러
    
    에러
    Failed to instantiate instance of destination kr.co.hanbit.product.management.presentation.ProductDto. Ensure that kr.co.hanbit.product.management.presentation.ProductDto has a non-private no-argument constructor.
    Caused by: java.lang.NoSuchMethodException: kr.co.hanbit.product.management.presentation.ProductDto.<init>()
    
    해결 : 빈 생성자 추가 
     */
    public ProductDto() {
    }

    // productAddAndFindByIdTest 테스트를 위해서 생성자 추가
    public ProductDto(String name, Integer price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    /*
    ModelMapper 와 같은 동작을 하는 코드 추가하기

    toEntity 는 ProductDto 를 Product 로 변환하고,
    toDto 는 반대로 Product 를 ProductDto 로 변환한다.
     */
    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();

        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setAmount(productDto.getAmount());

        return product;
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto(
                product.getName(),
                product.getPrice(),
                product.getAmount()
        );

        productDto.setId(product.getId());

        return productDto;
    }
}
