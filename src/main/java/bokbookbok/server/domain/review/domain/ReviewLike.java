package bokbookbok.server.domain.review.domain;

import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "reviews_likes")
public class ReviewLike extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review review;

    public ReviewLike(User user, Review review) {
        this.user = user;
        this.review = review;
    }
}
