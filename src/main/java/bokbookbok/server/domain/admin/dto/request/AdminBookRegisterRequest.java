package bokbookbok.server.domain.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class AdminBookRegisterRequest {
    private String title;
    private String description;
    private String author;
    private LocalDate startDate;
    private LocalDate endDate;
    private MultipartFile bookImageUrl;
    private Boolean isCurrent;

    @Schema(hidden = true)
    public boolean isCurrentOrDefault() {
        return this.isCurrent != null && this.isCurrent;
    }
}
