package bokbookbok.server.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateReviewResponse {
    private Long reviewId;
    private Long userId;
    private Long bookId;
}
