package bokbookbok.server.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.annotations.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class CreateReviewRequest {
    @NotNull
    private Long bookId;
    @NotBlank
    private String content;
}
