package bokbookbok.server.domain.book.dto.response;

import bokbookbok.server.domain.book.domain.Book;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookInfoResponse {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String imageUrl;

    public static BookInfoResponse from(Book book, String imageUrl) {
        return BookInfoResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .author(book.getAuthor())
                .imageUrl(imageUrl)
                .build();
    }
}
