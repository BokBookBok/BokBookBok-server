package bokbookbok.server.domain.review.dto.response;

import bokbookbok.server.domain.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
    private Long id;
    private String content;
    private int likeCount;

    public static ReviewResponse from(Review review) {
        if (review == null) return null;

        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .likeCount(review.getLikeCount())
                .build();
    }
}
