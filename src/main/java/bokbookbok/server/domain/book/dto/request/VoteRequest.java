package bokbookbok.server.domain.book.dto.request;

import bokbookbok.server.domain.book.domain.enums.VoteOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VoteRequest {
    private VoteOption option;
}
