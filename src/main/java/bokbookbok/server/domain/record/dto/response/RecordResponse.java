package bokbookbok.server.domain.record.dto.response;

import bokbookbok.server.domain.record.domain.UserBookRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecordResponse {
    private int readingDays;
    private int averageDays;

    public static RecordResponse from(UserBookRecord record, int averageDays) {
        return RecordResponse.builder()
                .readingDays(record.getReadingDays())
                .averageDays(averageDays)
                .build();
    }
}
