package kr.co.hanbit.product.management.domain;

/**
 * 예외를 새로 정의
 * Repository 에서 특정 id 에 해당하는 Product 를 찾지 못해 발생한 예외
 *
 * Product 말고 다른 도메인 객체들도 추가될 것을 감안하여 더 범용적인 이름으로 선택
 * Product 처럼 id(식별자)를 가지는 도메인 객체를 엔티티라고 부르므로 EntityNotFoundException 이라는 이름의 예외를 정의
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
