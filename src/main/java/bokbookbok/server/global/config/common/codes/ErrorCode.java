package bokbookbok.server.global.config.common.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /// 4000 ~ : client error
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, 4000, "잘못된 요청 형식입니다. 입력값을 확인해주세요."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, 4001, "이미 가입된 이메일입니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, 4002, "존재하지 않는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, 4003, "비밀번호가 일치하지 않습니다."),
    USER_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, 4004, "일치하는 회원 정보가 없습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, 4005, "일치하는 이메일 정보가 없습니다."),
    INVALID_USER(HttpStatus.NOT_FOUND, 4006, "존재하지 않는 회원입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, 4007, "이미 사용중인 닉네임입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, 4009, "현재 비밀번호 값이 일치하지 않습니다."),
    INVALID_PROFILE_IMAGE(HttpStatus.BAD_REQUEST, 4010,"이미지 파일만 업로드 가능합니다."),
    INVALID_EMAIL_TYPE(HttpStatus.BAD_REQUEST, 4025, "이메일 형식이 올바르지 않습니다."),

    // 5000~ : server error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final int code;
    private final String msg;

}
