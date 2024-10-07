package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.infrastructure.ListProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service // 빈으로 등록
public class SimpleProductService {

    private ListProductRepository listProductRepository;
    private ModelMapper modelMapper; // 의존성 주입 받아서 ModelMapper 사용

    SimpleProductService(ListProductRepository listProductRepository, ModelMapper modelMapper) {
        this.listProductRepository = listProductRepository;
        this.modelMapper = modelMapper;
    }

    // 상품 추가
    public ProductDto add(ProductDto productDto) { // DTO는 표현 계층부터 응용 계층까지 역할
        // 1. ProductDto 를 Product로 변환하는 코드
        /*
        ModelMapper 의 map 메서드를 사용할 때는 첫 번째 인자로 변환시킬 대상을 넣고,
        두 번째 인자로 어떤 타입으로 변환할지는 [클래스 이름.class]의 형태로 넣어 주면 된다.
        - 그러면 필드 이름을 기준으로 동일한 필드 이름에 해당하는 값을 자동으로 복사하여 변환해 준다.
         */
        Product product = modelMapper.map(productDto, Product.class);

        // 2. 레포지토리를 호출하는 코드
        Product savedProduct = listProductRepository.add(product);

        // 3. Product 를 ProductDto 로 변환하는 코드
        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);

        // 4. DTO 를 반환하는 코드
        return savedProductDto;
    }

    // 상품 한 개 조회
    public ProductDto findById(Long id) {
        Product product = listProductRepository.findById(id);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }
}
