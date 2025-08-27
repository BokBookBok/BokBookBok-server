package bokbookbok.server.domain.book.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class VoteResultResponse {
    private List<VoteResultItem> voteResult;
}
