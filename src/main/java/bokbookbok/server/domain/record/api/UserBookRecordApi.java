package bokbookbok.server.domain.record.api;

import bokbookbok.server.domain.record.application.UserBookRecordService;
import bokbookbok.server.domain.record.dto.response.RecordHomeResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.SuccessCode;
import bokbookbok.server.global.config.common.response.BaseResponse;
import bokbookbok.server.global.config.resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/api/records")
public class UserBookRecordApi {
    private final UserBookRecordService userBookService;

    @GetMapping("")
    @Operation(summary = "기록 홈 조회")
    public BaseResponse<RecordHomeResponse> getRecords(@Parameter(hidden = true) @CurrentUser User user) {
        RecordHomeResponse recordHomeResponse = userBookService.getUserRecords(user);
        return BaseResponse.of(SuccessCode.GET_RECORDS_SUCCESS, recordHomeResponse);
    }
}
