package bokbookbok.server.global.config.common.codes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    OK(HttpStatus.OK, 2000, "OK"),
    USER_REGISTERED(HttpStatus.CREATED, 2001, "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK, 2002, "로그인에 성공하였습니다."),
    USER_EMAIL_FOUND(HttpStatus.OK, 2003, "아이디 조회에 성공하였습니다."),
    REGISTER_THIS_WEEK_BOOK(HttpStatus.OK, 2004, "금주 책 정보가 업로드 되었습니다."),
    UPDATE_USER_INFO_SUCCESS(HttpStatus.OK, 2006, "회원 정보를 수정하였습니다."),
    N_TOKEN_SUCCESS(HttpStatus.OK, 2025, "관리자 토큰이 발급되었습니다."),
    CHECK_EMAIL_DUPLICATED(HttpStatus.OK, 2014, "이메일 중복 확인이 완료되었습니다."),
    CHECK_NICKNAME_DUPLICATED(HttpStatus.OK, 2015, "닉네임 중복 확인이 완료되었습니다.");

    private final HttpStatus status;
    private final int code;
    private final String msg;
}
