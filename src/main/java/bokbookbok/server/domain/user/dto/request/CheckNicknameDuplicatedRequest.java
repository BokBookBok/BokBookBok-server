package bokbookbok.server.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckNicknameDuplicatedRequest {
    @NotBlank
    private String nickname;
}
