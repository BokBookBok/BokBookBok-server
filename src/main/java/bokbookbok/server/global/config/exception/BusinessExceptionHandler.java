package bokbookbok.server.global.config.exception;

import bokbookbok.server.global.config.common.codes.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessExceptionHandler extends RuntimeException{
    private final ErrorCode errorCode;

}
