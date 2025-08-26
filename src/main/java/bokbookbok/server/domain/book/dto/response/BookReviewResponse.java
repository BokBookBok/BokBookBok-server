package bokbookbok.server.domain.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BookReviewResponse {
    private ReviewItem myReview;
    private List<ReviewItem> otherReviews;
}