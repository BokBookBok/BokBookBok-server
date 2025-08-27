package bokbookbok.server.domain.record.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecordHomeResponse {
    private String nickname;
    private int totalCount;
    private List<RecordSummaryResponse> records;
}
