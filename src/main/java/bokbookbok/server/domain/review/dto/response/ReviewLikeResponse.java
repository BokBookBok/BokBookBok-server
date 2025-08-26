package bokbookbok.server.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewLikeResponse {
    private Long reviewId;
    private boolean liked;
    private int likeCount;
}
