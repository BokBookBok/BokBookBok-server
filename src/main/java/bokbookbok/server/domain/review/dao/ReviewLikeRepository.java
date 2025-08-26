package bokbookbok.server.domain.review.dao;

import bokbookbok.server.domain.review.domain.Review;
import bokbookbok.server.domain.review.domain.ReviewLike;
import bokbookbok.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByUserAndReview(User user, Review review);
    int countByReview(Review review);
}
