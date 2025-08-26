package bokbookbok.server.domain.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class AdminBookRegisterResponse {
    private Long bookId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
