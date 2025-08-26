package bokbookbok.server.domain.book.dto.request;

import bokbookbok.server.domain.book.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateBookStatusRequest {
    private Status status;
}
