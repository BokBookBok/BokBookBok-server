package bokbookbok.server.domain.record.dto.response;

import bokbookbok.server.domain.book.dto.response.BookInfoResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class RecordSummaryResponse {
    private BookInfoResponse bookInfoResponse;
    private int readDays;
    private LocalDate startDate;
    private LocalDate endDate;
    private String weekLabel;
}
