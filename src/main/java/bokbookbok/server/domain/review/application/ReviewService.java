package bokbookbok.server.domain.review.application;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.dto.response.BookReviewResponse;
import bokbookbok.server.domain.book.dto.response.ReviewItem;
import bokbookbok.server.domain.book.repository.BookRepository;
import bokbookbok.server.domain.review.dao.ReviewLikeRepository;
import bokbookbok.server.domain.review.dao.ReviewRepository;
import bokbookbok.server.domain.review.domain.Review;
import bokbookbok.server.domain.review.domain.ReviewLike;
import bokbookbok.server.domain.review.dto.request.CreateReviewRequest;
import bokbookbok.server.domain.review.dto.response.CreateReviewResponse;
import bokbookbok.server.domain.review.dto.response.ReviewLikeResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final BookRepository bookRepository;

    @Transactional
    public CreateReviewResponse createReview(User user, CreateReviewRequest createReviewRequest) {
        Book book = bookRepository.findById(createReviewRequest.getBookId())
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_EXISTS_BOOK));

        if (reviewRepository.existsByUserAndBook(user, book)) {
            throw new BusinessExceptionHandler(ErrorCode.ALREADY_WRITTEN_REVIEW);
        }

        Review review = Review.builder()
                .user(user)
                .book(book)
                .content(createReviewRequest.getContent())
                .build();

        reviewRepository.save(review);

        return CreateReviewResponse.builder()
                .reviewId(review.getId())
                .userId(user.getId())
                .bookId(book.getId())
                .build();
    }

    @Transactional
    public ReviewLikeResponse toggleReviewLikeResponse(User user, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_EXISTS_REVIEW));

        Optional<ReviewLike> existingLike = reviewLikeRepository.findByUserAndReview(user, review);

        boolean liked;

        if (existingLike.isPresent()) {
            reviewLikeRepository.delete(existingLike.get());
            liked = false;
        } else {
            ReviewLike reviewLike = new ReviewLike(user, review);
            reviewLikeRepository.save(reviewLike);
            liked = true;
        }

        int likeCount = reviewLikeRepository.countByReview(review);
        review.updateLikeCount(likeCount);
        reviewRepository.save(review);

        return ReviewLikeResponse.builder()
                .reviewId(reviewId)
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }

    public BookReviewResponse getBookReviews(User user, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_EXISTS_BOOK));
        List<Review> reviews = reviewRepository.findAllByBook(book);

        List<ReviewItem> reviewItems = reviews.stream()
                .map(review -> {
                    boolean liked = reviewLikeRepository.existsByUserAndReview(user, review);
                    int likeCount = reviewLikeRepository.countByReview(review);

                    return ReviewItem.builder()
                            .reviewId(review.getId())
                            .userId(review.getUser().getId())
                            .nickname(review.getUser().getNickname())
                            .content(review.getContent())
                            .liked(liked)
                            .likeCount(likeCount)
                            .build();
                })
                .toList();

        return BookReviewResponse.builder()
                .myReview(reviewItems.stream()
                        .filter(item -> item.getUserId().equals(user.getId()))
                        .findFirst()
                        .orElse(null))
                .otherReviews(reviewItems.stream()
                        .filter(item -> !item.getUserId().equals(user.getId()))
                        .toList())
                .build();
    }
}
