package bokbookbok.server.domain.book.dto.response;

import bokbookbok.server.domain.book.domain.enums.Status;
import bokbookbok.server.domain.record.domain.UserBookRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class BookStatusResponse {
    private Long bookId;
    private Status status;

    public static BookStatusResponse from(UserBookRecord savedRecord) {
        return new BookStatusResponse(
                savedRecord.getBook().getId(), savedRecord.getStatus());
    }
}
