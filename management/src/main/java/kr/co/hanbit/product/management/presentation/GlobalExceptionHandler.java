package kr.co.hanbit.product.management.presentation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;

/**
 * @RestControllerAdvice 로 유효성 검사 예외 처리
 * 컨트롤러까지 넘어온 예외를 처리하려는 것 그래서 에외에 대한 처리를 담당하는 전역 예외 핸들러는 표현 계층에 있어야 적절
 * 그리고 전역 예외 핸들러는 HTTP 상태 코드를 다뤄야 하기 때문에 표현 계층에 있는 편이 적절
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ConstraintViolationException 예외 처리 코드 추가하기 - 도메인 객체에 대한 유효성 검사 실패
    /*
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolatedException(
            ConstraintViolationException ex
    ) {
        // 예외에 대한 처리
        String errorMessage = "에러 메시지";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
     */

    // ConstraintViolationException 예외 처리 코드 추가하기 - 도메인 객체에 대한 유효성 검사 실패한 경우
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolatedException(
            ConstraintViolationException ex
    ) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        System.out.println("constraintViolations = " + constraintViolations);
        System.out.println();
        /*
        List<String> errors = constraintViolations.stream()
                .map(
                        constraintViolation ->
                                constraintViolation.getPropertyPath() + ", " +
                                        constraintViolation.getMessage()
                )
                .toList(); // "checkValid.validationTarget.price, 1000000 이하여야 합니다"
         */

        // ValidationService 에서 사용하는 메서드와 매개 변수 이름이 함께 노춛된다.
        // MethodArgumentNotValidException 예외 처리 메시지 처럼 바꾸기 위해서 코드를 수정하면 일관성 있게 맞출 수 있다.
        List<String> errors = constraintViolations.stream()
                .map(
                        constraintViolation ->
                                extractField(constraintViolation.getPropertyPath()) + ", " +
                                        constraintViolation.getMessage()
                )
                .toList();

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private String extractField(Path path) {
        System.out.println("path = " + path);
        System.out.println();
        String[] splittedArray = path.toString().split("[.]"); // .을 기준으로 나눈다. - [.] 는 정규 표현식
        int lastIndex = splittedArray.length - 1;
        return splittedArray[lastIndex];
    }

    // MethodArgumentNotValidException 예외 처리 코드 추가하기 - 컨트롤러에서 유효성 검사에 실패한 경우
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        System.out.println("fieldErrors = " + fieldErrors);
        System.out.println();
        List<String> errors = fieldErrors.stream()
                .map(
                        fieldError ->
                                fieldError.getField() + ", " + fieldError.getDefaultMessage()
                )
                .toList(); // "price, 널이어서는 안됩니다"

        ErrorMessage errorMessage = new ErrorMessage(errors);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
