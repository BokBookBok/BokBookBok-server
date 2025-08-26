package bokbookbok.server.domain.user.dto.response;

import bokbookbok.server.domain.auth.domain.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long userId;
    private JwtToken jwtToken;
}
