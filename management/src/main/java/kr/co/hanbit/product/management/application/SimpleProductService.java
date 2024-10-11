package kr.co.hanbit.product.management.application;

import kr.co.hanbit.product.management.domain.Product;
import kr.co.hanbit.product.management.infrastructure.DatabaseProductRepository;
import kr.co.hanbit.product.management.presentation.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 빈으로 등록
public class SimpleProductService {

    // ListProductRepository 대신 DatabaseProductRepository 사용하기
    private DatabaseProductRepository databaseProductRepository;
    private ModelMapper modelMapper; // 의존성 주입 받아서 ModelMapper 사용
    private ValidationService validationService; // 유효성 검사하는 코드 추가

    @Autowired
    SimpleProductService(DatabaseProductRepository databaseProductRepository, ModelMapper modelMapper,
                         ValidationService validationService) {
        this.databaseProductRepository = databaseProductRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;
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
        validationService.checkValid(product); // Product 에 붙인 Bean Validation 애너테이션 기준으로 유효성 검사

        // 2. 레포지토리를 호출하는 코드
        Product savedProduct = databaseProductRepository.add(product);

        // 3. Product 를 ProductDto 로 변환하는 코드
        ProductDto savedProductDto = modelMapper.map(savedProduct, ProductDto.class);

        // 4. DTO 를 반환하는 코드
        return savedProductDto;
    }

    // 상품 한 개 조회
    public ProductDto findById(Long id) {
        Product product = databaseProductRepository.findById(id);
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    // 전체 상품 목록 조회
    public List<ProductDto> findAll() {
        List<Product> products = databaseProductRepository.findAll();
        // Product 리스트를 ProductDto 리스트로 변환
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        return productDtos;
    }

    // 상품 이름에 포함된 문자열로 검색
    public List<ProductDto> findByNameContaining(String name) {
        List<Product> products = databaseProductRepository.findByNameContaining(name);
        // Product 리스트를 ProductDto 리스트로 변환
        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        return productDtos;
    }

    // 상품 수정하기
    public ProductDto update(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product updatedProduct = databaseProductRepository.update(product);
        ProductDto updatedProductDto = modelMapper.map(updatedProduct, ProductDto.class);
        return updatedProductDto;
    }

    // 상품 삭제하기
    public void delete(Long id) {
        databaseProductRepository.delete(id);
    }
}
