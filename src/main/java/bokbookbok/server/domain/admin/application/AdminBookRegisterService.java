package bokbookbok.server.domain.admin.application;

import bokbookbok.server.domain.admin.dto.request.AdminBookRegisterRequest;
import bokbookbok.server.domain.admin.dto.response.AdminBookRegisterResponse;
import bokbookbok.server.domain.book.domain.Book;
import bokbookbok.server.domain.book.repository.BookRepository;
import bokbookbok.server.global.config.S3.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AdminBookRegisterService {
    private final S3Service s3Service;
    private final BookRepository bookRepository;

    @Transactional
    public AdminBookRegisterResponse registerBook(AdminBookRegisterRequest adminBookRegisterRequest) {
        String imageUrl = s3Service.upload(adminBookRegisterRequest.getBookImageUrl(), "book");

        Book book = Book.builder()
                .title(adminBookRegisterRequest.getTitle())
                .description(adminBookRegisterRequest.getDescription())
                .startDate(adminBookRegisterRequest.getStartDate())
                .endDate(adminBookRegisterRequest.getEndDate())
                .imageUrl(imageUrl)
                .build();

        bookRepository.save(book);

        return AdminBookRegisterResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .startDate(book.getStartDate())
                .endDate(book.getEndDate())
                .build();
    }
}