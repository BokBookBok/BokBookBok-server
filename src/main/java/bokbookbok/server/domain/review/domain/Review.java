package bokbookbok.server.domain.review.domain;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private String content;

    private int likeCount;

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

}
