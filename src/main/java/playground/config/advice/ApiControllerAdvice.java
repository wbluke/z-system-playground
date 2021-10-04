package playground.config.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import playground.config.interceptor.InvalidLoginTokenException;
import playground.response.ApiExceptionResponse;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiExceptionResponse> handleBindException(BindException e) {
        String message = extractMessageFrom(e);

        return ResponseEntity.badRequest()
                .body(ApiExceptionResponse.badParameter(message));
    }

    @ExceptionHandler(InvalidLoginTokenException.class)
    public ResponseEntity<ApiExceptionResponse> handleLoginException(InvalidLoginTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiExceptionResponse.unauthorized(e.getMessage()));
    }

    private String extractMessageFrom(BindException e) {
        FieldError fieldError = e.getFieldError();
        return fieldError == null ? "" : fieldError.getDefaultMessage();
    }

}
