package bokbookbok.server.domain.book.dto.response;

import bokbookbok.server.domain.book.domain.enums.VoteOption;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class VoteResultResponse {
    private String question;
    private List<VoteResultItem> voteResult;
    private VoteOption myVote;
}
