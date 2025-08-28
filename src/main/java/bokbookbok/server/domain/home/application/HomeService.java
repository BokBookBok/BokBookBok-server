package bokbookbok.server.domain.home.application;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.book.repository.BookRepository;
import bokbookbok.server.domain.home.dto.response.HomeResponse;
import bokbookbok.server.domain.record.application.UserBookRecordService;
import bokbookbok.server.domain.record.dao.UserBookRecordRepository;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import bokbookbok.server.domain.review.dao.ReviewRepository;
import bokbookbok.server.domain.review.domain.Review;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.S3.S3Service;
import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class HomeService {


    private final BookRepository bookRepository;
    private final UserBookRecordRepository recordRepository;
    private final ReviewRepository reviewRepository;
    private final UserBookRecordService userBookService;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public HomeResponse getHome(User user) {
        Book selectedBook = bookRepository.findCurrentBook()
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_EXISTS_BOOK));

        UserBookRecord record = recordRepository.findByUserAndBook(user, selectedBook).orElse(null);
        Review bestReview = reviewRepository.findTopByBookOrderByLikeCountDescCreatedAtAsc(selectedBook).orElse(null);
        int averageDays = userBookService.getAverageReadingDaysByBook(selectedBook);
        String imageUrl = s3Service.getPublicUrl(selectedBook.getImageUrl());

        if (record == null || record.getStatus() == Status.NOT_STARTED) {
            return HomeResponse.forNotStarted(selectedBook, bestReview, imageUrl);
        }

        switch (record.getStatus()) {
            case READING:
                return HomeResponse.forReading(selectedBook, record, bestReview, averageDays, imageUrl);
            case READ_COMPLETED:
                return HomeResponse.forReadCompleted(selectedBook, record, bestReview, averageDays, imageUrl);
            case REVIEWED:
                Review myReview = reviewRepository.findTopByUserAndBookOrderByCreatedAtDesc(user, selectedBook).orElse(null);
                return HomeResponse.forReviewed(selectedBook, record, myReview, bestReview, averageDays, imageUrl);
            default:
                throw new BusinessExceptionHandler(ErrorCode.INVALID_STATUS);
        }
    }
}