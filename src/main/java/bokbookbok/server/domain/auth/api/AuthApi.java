package bokbookbok.server.domain.auth.api;

import bokbookbok.server.domain.auth.application.AuthService;
import bokbookbok.server.domain.user.dto.request.CheckEmailDuplicatedRequest;
import bokbookbok.server.domain.user.dto.request.CheckNicknameDuplicatedRequest;
import bokbookbok.server.domain.user.dto.request.LoginRequest;
import bokbookbok.server.domain.user.dto.request.RegisterRequest;
import bokbookbok.server.domain.user.dto.response.CheckDuplicatedResponse;
import bokbookbok.server.domain.user.dto.response.LoginResponse;
import bokbookbok.server.domain.user.dto.response.RegisterResponse;
import bokbookbok.server.global.config.common.codes.SuccessCode;
import bokbookbok.server.global.config.common.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final AuthService authService;


    @Operation(summary = "회원가입")
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = authService.register(registerRequest);
        return ResponseEntity.ok(BaseResponse.of(SuccessCode.USER_REGISTERED, registerResponse));
    }

    @Operation(summary = "이메일 중복 확인", description = "true 반환 시, 이메일 중복")
    @PostMapping("/register/email")
    public ResponseEntity<BaseResponse<CheckDuplicatedResponse>> checkEmailDuplicated(@Valid @RequestBody CheckEmailDuplicatedRequest CheckEmailDuplicatedRequest) {
        CheckDuplicatedResponse checkEmailResponse = authService.checkEmailDuplicated(CheckEmailDuplicatedRequest);
        return ResponseEntity.ok(BaseResponse.of(SuccessCode.CHECK_EMAIL_DUPLICATED, checkEmailResponse));
    }

    @Operation(summary = "닉네임 중복 확인", description = "true 반환 시, 닉네임 중복")
    @PostMapping("/register/nickname")
    public ResponseEntity<BaseResponse<CheckDuplicatedResponse>> checkNicknameDuplicated(@Valid @RequestBody CheckNicknameDuplicatedRequest CheckNicknameDuplicatedRequest) {
        CheckDuplicatedResponse checkNicknameResponse = authService.checkNicknameDuplicated(CheckNicknameDuplicatedRequest);
        return ResponseEntity.ok(BaseResponse.of(SuccessCode.CHECK_NICKNAME_DUPLICATED, checkNicknameResponse));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(BaseResponse.of(SuccessCode.LOGIN_SUCCESS, loginResponse));
    }

}
