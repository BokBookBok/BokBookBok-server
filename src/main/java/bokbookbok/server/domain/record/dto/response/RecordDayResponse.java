package bokbookbok.server.domain.record.dto.response;

import bokbookbok.server.domain.record.domain.UserBookRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecordDayResponse {
    private int readingDays;
    private int averageDays;

    public static RecordDayResponse from(UserBookRecord record, int averageDays) {
        return RecordDayResponse.builder()
                .readingDays(record.getReadingDays())
                .averageDays(averageDays)
                .build();
    }
}
