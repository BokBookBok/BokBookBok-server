package bokbookbok.server.global.config.exception;

import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessExceptionHandler.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(BusinessExceptionHandler customException) {
        ErrorCode errorCode = customException.getErrorCode();
        log.error("[CustomException] code: {}, message: {}", errorCode.getCode(), errorCode.getMsg());
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("유효성 검사에 실패했습니다.");

        log.warn("[ValidationException] message: {}", message);

        if (message.contains("이메일")) {
            return ResponseEntity
                    .status(ErrorCode.INVALID_EMAIL_TYPE.getStatus())
                    .body(ErrorResponse.of(ErrorCode.INVALID_EMAIL_TYPE));
        }

        return ResponseEntity
                .status(ErrorCode.INVALID_REQUEST.getStatus())
                .body(ErrorResponse.of(ErrorCode.INVALID_REQUEST, message));
    }

    /*@ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("[HttpMessageNotReadable] message: {}", ex.getMessage());

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException invalidEx) {
            // LocalDate 파싱 실패인지 확인
            if (invalidEx.getTargetType().equals(java.time.LocalDate.class)) {
                return ResponseEntity
                        .status(ErrorCode.INVALID_DATE_FORMAT.getStatus())
                        .body(ErrorResponse.of(ErrorCode.INVALID_DATE_FORMAT));
            }
        }

        return ResponseEntity
                .status(ErrorCode.INVALID_REQUEST.getStatus())
                .body(ErrorResponse.of(ErrorCode.INVALID_REQUEST, ex.getMessage()));
    }*/

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("[Exception] message: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
