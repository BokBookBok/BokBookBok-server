package bokbookbok.server.domain.admin.dto.request;

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
    private LocalDate startDate;
    private LocalDate endDate;
    private MultipartFile bookImageUrl;
}
