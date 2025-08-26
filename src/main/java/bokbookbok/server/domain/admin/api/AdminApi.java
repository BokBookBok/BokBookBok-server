package bokbookbok.server.domain.admin.api;

import bokbookbok.server.domain.admin.application.AdminBookRegisterService;
import bokbookbok.server.domain.admin.dto.request.AdminBookRegisterRequest;
import bokbookbok.server.domain.admin.dto.response.AdminBookRegisterResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.SuccessCode;
import bokbookbok.server.global.config.common.response.BaseResponse;
import bokbookbok.server.global.config.resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminApi {

    private final AdminBookRegisterService adminBookRegisterService;

    @Operation(summary = "금주 도서 등록")
    @PostMapping(value = "/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<AdminBookRegisterResponse> AdminRegisterBook(
            @Parameter(hidden = true) @CurrentUser User admin,
            @Valid @ModelAttribute AdminBookRegisterRequest adminBookRegisterRequest
    ) {
        AdminBookRegisterResponse adminBookRegisterResponse = adminBookRegisterService
                .registerBook(adminBookRegisterRequest);

        return BaseResponse.of(SuccessCode.REGISTER_THIS_WEEK_BOOK, adminBookRegisterResponse);
    }
}
