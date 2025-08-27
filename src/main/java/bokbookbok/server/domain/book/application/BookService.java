package bokbookbok.server.domain.book.application;

import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.book.dto.request.UpdateBookStatusRequest;
import bokbookbok.server.domain.book.dto.response.BookInfoResponse;
import bokbookbok.server.domain.book.dto.response.BookStatusResponse;
import bokbookbok.server.domain.book.repository.BookRepository;
import bokbookbok.server.domain.record.dao.UserBookRecordRepository;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.ErrorCode;
import bokbookbok.server.global.config.exception.BusinessExceptionHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserBookRecordRepository recordRepository;

    @Transactional
    public BookStatusResponse updateBookStatus(Long bookId, User user, UpdateBookStatusRequest updateBookStatusRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_EXISTS_BOOK));

        Status newStatus = updateBookStatusRequest.getStatus();
        UserBookRecord record = recordRepository.findByUserAndBook(user, book)
                .orElseGet(() -> UserBookRecord.builder()
                        .user(user)
                        .book(book)
                        .status(newStatus)
                        .build());

        record.updateStatusWithDate(newStatus, LocalDate.now());
        UserBookRecord savedRecord = recordRepository.save(record);

        return BookStatusResponse.from(savedRecord);
    }

    public BookStatusResponse getBookStatus(User user, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.NOT_EXISTS_BOOK));

        UserBookRecord record = recordRepository.findByUserAndBook(user, book).orElse(null);

        Status status;
        if (record != null) {
            status = record.getStatus();
        } else {
            status = Status.NOT_STARTED;
        }

        return BookStatusResponse.builder()
                .bookId(book.getId())
                .status(status)
                .build();
    }

    public BookInfoResponse getCurrentBook(User user) {
        Book book = bookRepository.findCurrentBook()
                .orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.CURRENT_BOOK_NOT_FOUND));

        return BookInfoResponse.from(book);
    }
}
