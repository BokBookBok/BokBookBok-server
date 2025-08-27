package bokbookbok.server.domain.home.dto.response;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.book.dto.response.BookInfoResponse;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import bokbookbok.server.domain.record.dto.response.RecordResponse;
import bokbookbok.server.domain.review.domain.Review;
import bokbookbok.server.domain.review.dto.response.ReviewResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeResponse {

    private BookInfoResponse book;
    private RecordResponse record;
    private ReviewResponse myReview;
    private ReviewResponse bestReview;

    private Status status;

    public static HomeResponse forNotStarted(Book book, Review bestReview) {
        return HomeResponse.builder()
                .book(BookInfoResponse.from(book))
                .bestReview(ReviewResponse.from(bestReview))
                .status(Status.NOT_STARTED)
                .build();
    }

    public static HomeResponse forReading(Book book, UserBookRecord record, Review bestReview, int averageDays) {
        return HomeResponse.builder()
                .book(BookInfoResponse.from(book))
                .record(RecordResponse.from(record, averageDays))
                .bestReview(ReviewResponse.from(bestReview))
                .status(record.getStatus())
                .build();
    }

    public static HomeResponse forReadCompleted(Book book, UserBookRecord record, Review bestReview, int averageDays) {
        return HomeResponse.builder()
                .book(BookInfoResponse.from(book))
                .record(RecordResponse.from(record, averageDays))
                .status(record.getStatus())
                .bestReview(ReviewResponse.from(bestReview))
                .build();
    }

    public static HomeResponse forReviewed(Book book, UserBookRecord record, Review myReview, Review bestReview, int averageDays) {
        return HomeResponse.builder()
                .book(BookInfoResponse.from(book))
                .record(RecordResponse.from(record, averageDays))
                .myReview(ReviewResponse.from(myReview))
                .status(record.getStatus())
                .bestReview(ReviewResponse.from(bestReview))
                .build();
    }
}