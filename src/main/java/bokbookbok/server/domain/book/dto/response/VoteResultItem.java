package bokbookbok.server.domain.book.dto.response;

import bokbookbok.server.domain.book.domain.enums.VoteOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VoteResultItem {
    private VoteOption option;
    private String text;
    private int count;
    private double percentage;
}
