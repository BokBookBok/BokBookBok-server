package bokbookbok.server.domain.review.application;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.repository.BookRepository;
import bokbookbok.server.domain.review.dao.ReviewRepository;
import bokbookbok.server.domain.review.domain.Review;
import bokbookbok.server.domain.review.dto.request.CreateReviewRequest;
import bokbookbok.server.domain.review.dto.response.CreateReviewResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Transactional
    public CreateReviewResponse createReview(User user, CreateReviewRequest createReviewRequest) {
        Book book = bookRepository.findById(createReviewRequest.getBookId())
                .orElseThrow(()-> new BusinessExceptionHandler(ErrorCode.NOT_EXISTS_BOOK));

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
}
