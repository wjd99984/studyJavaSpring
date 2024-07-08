package com.jungspring.common;


import com.jungspring.common.error.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;




/*ResponseEntity: HTTP 응답을 표현하는 클래스입니다.
ExceptionHandler: 특정 예외를 처리하는 메서드임을 나타내는 어노테이션입니다.
RestControllerAdvice: 전역 예외 처리 기능을 제공하는 어노테이션입니다.*/

@Slf4j //Lombok에서 제공하는 어노테이션으로, 로깅을 위한 Logger 객체를 자동으로 생성해 줍니다.
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> IllegalArgumentException(IllegalArgumentException exception) {

        log.warn("error 에러발생!!!!", exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(ExceptionResponse.from(exception));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> noSuchElement(NoSuchElementException exception) {

        log.warn("NO SUCH ELEMENT!!!!", exception.getMessage(), exception);
        return ResponseEntity.badRequest().body(ExceptionResponse.from(exception));
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError)error;
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            stringBuilder.append(fieldName).append(" : ").append(message).append(", ");
            log.error("MethodArgumentNotValidException 발생: {} \n{}", fieldName + ": " + message, error);
        });
        return ResponseEntity.badRequest().body(new ExceptionResponse(stringBuilder.toString()));
    }
}

/*이 코드는 Spring 프레임워크에서 사용되는 예외 처리 핸들러입니다. @ExceptionHandler 어노테이션을 사용하여 특정 예외가 발생했을 때 실행되는 메서드를 정의합니다. 이 경우 MethodArgumentNotValidException 예외가 발생하면 methodArgumentNotValidException 메서드가 호출됩니다.

이 예외는 주로 Spring MVC에서 @Valid 또는 @Validated 어노테이션을 사용하여 요청 본문의 유효성 검사가 실패할 때 발생합니다.

코드를 자세히 살펴보면 다음과 같은 기능을 수행합니다:

@ExceptionHandler(value = MethodArgumentNotValidException.class): MethodArgumentNotValidException 예외가 발생했을 때 이 메서드가 호출됩니다.

methodArgumentNotValidException(MethodArgumentNotValidException e): 예외 객체 e를 파라미터로 받아 처리합니다.

BindingResult bindingResult = e.getBindingResult();: 예외 객체에서 BindingResult를 가져옵니다. BindingResult는 유효성 검사 오류 정보를 포함합니다.

StringBuilder stringBuilder = new StringBuilder();: 오류 메시지를 구성하기 위한 StringBuilder 객체를 생성합니다.

bindingResult.getAllErrors().forEach(error -> { ... });: 모든 오류를 순회하며 각각의 오류 정보를 처리합니다.

FieldError fieldError = (FieldError)error;: 각 오류를 FieldError로 캐스팅합니다.
String fieldName = fieldError.getField();: 오류가 발생한 필드 이름을 가져옵니다.
String message = fieldError.getDefaultMessage();: 기본 오류 메시지를 가져옵니다.
stringBuilder.append(fieldName).append(" : ").append(message).append(", ");: 필드 이름과 메시지를 StringBuilder에 추가합니다.
log.error("MethodArgumentNotValidException 발생: {} \n{}", fieldName + ": " + message, error);: 오류 정보를 로그에 기록합니다.
return ResponseEntity.badRequest().body(new ExceptionResponse(stringBuilder.toString()));: 400 Bad Request 응답을 생성하고, 오류 메시지를 포함하는 ExceptionResponse 객체를 응답 본문에 담아 반환합니다.

아래는 좀 더 상세한 설명을 위해 사용된 클래스와 메서드를 예시로 포함한 코드입니다:*/