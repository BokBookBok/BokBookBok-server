package bokbookbok.server.domain.auth.application;

import bokbookbok.server.domain.auth.domain.JwtToken;
import bokbookbok.server.domain.auth.enums.JwtGrantType;
import bokbookbok.server.domain.auth.infra.JwtTokenGenerator;
import bokbookbok.server.domain.user.dao.UserRepository;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.domain.user.domain.enums.Role;
import bokbookbok.server.domain.user.dto.request.CheckEmailDuplicatedRequest;
import bokbookbok.server.domain.user.dto.request.CheckNicknameDuplicatedRequest;
import bokbookbok.server.domain.user.dto.request.LoginRequest;
import bokbookbok.server.domain.user.dto.request.RegisterRequest;
import bokbookbok.server.domain.user.dto.response.CheckDuplicatedResponse;
import bokbookbok.server.domain.user.dto.response.LoginResponse;
import bokbookbok.server.domain.user.dto.response.RegisterResponse;
import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPwd;

    public RegisterResponse register(RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BusinessExceptionHandler(ErrorCode.DUPLICATED_EMAIL);
        }

        if (userRepository.findByNickname(registerRequest.getNickname()).isPresent()) {
            throw new BusinessExceptionHandler(ErrorCode.DUPLICATED_NICKNAME);
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
                .userId(user.getId())
                .build();
    }

}
