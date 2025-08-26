package bokbookbok.server.domain.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReviewItem {
    private Long reviewId;
    private Long userId;
    private String nickname;
    private String content;
    private int likeCount;
    private boolean liked;
}