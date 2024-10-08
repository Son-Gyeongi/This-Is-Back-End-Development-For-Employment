package kr.co.hanbit.product.management.presentation;

import java.util.List;

/**
 * 에러 메시지를 좀 더 명확하게 나타내기 위해 ErrorMessage 클래스를 정의
 */
public class ErrorMessage {

    private List<String> errors;

    public ErrorMessage(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
