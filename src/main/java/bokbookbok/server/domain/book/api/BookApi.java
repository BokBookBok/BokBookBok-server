package bokbookbok.server.domain.book.api;

import bokbookbok.server.domain.book.application.BookService;
import bokbookbok.server.domain.book.dto.request.UpdateBookStatusRequest;
import bokbookbok.server.domain.book.dto.response.BookStatusResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.SuccessCode;
import bokbookbok.server.global.config.common.response.BaseResponse;
import bokbookbok.server.global.config.resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookApi {

    private final BookService bookService;

    @GetMapping("/{bookId}/status")
    @Operation(summary = "유저의 독서 상태 조회")
    public BaseResponse<BookStatusResponse> getBookStatus(
            @Parameter(hidden = true) @CurrentUser User user,
            @PathVariable Long bookId) {
        BookStatusResponse bookStatusResponse = bookService.getBookStatus(user, bookId);
        return BaseResponse.of(SuccessCode.CHECK_READING_STATUS_SUCCESS, bookStatusResponse);
    }

    @PatchMapping("/{bookId}/status")
    @Operation(summary = "유저의 독서 상태 변경")
    public BaseResponse<BookStatusResponse> updateBookStatus(
            @Parameter(hidden = true) @CurrentUser User user,
            @PathVariable Long bookId,
            UpdateBookStatusRequest updateBookStatusRequest) {

        BookStatusResponse updateBookStatusResponse = bookService.updateBookStatus
                (bookId, user, updateBookStatusRequest);
        return BaseResponse.of(SuccessCode.CHANGE_READING_STATUS_SUCCESS, updateBookStatusResponse);
    }
}
