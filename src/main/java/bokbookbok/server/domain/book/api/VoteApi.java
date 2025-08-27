package bokbookbok.server.domain.book.api;

import bokbookbok.server.domain.book.application.VoteService;
import bokbookbok.server.domain.book.dto.request.VoteRequest;
import bokbookbok.server.domain.book.dto.response.VoteResultResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.SuccessCode;
import bokbookbok.server.global.config.common.response.BaseResponse;
import bokbookbok.server.global.config.resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class VoteApi {
    private final VoteService voteService;

    @PostMapping("/{bookId}/vote")
    public BaseResponse<VoteResultResponse> vote(
            @PathVariable Long bookId,
            @RequestBody @Valid VoteRequest request,
            @Parameter(hidden = true) @CurrentUser User user
    ) {
        VoteResultResponse voteResultResponse = voteService.vote(bookId, user, request.getOption());
        return BaseResponse.of(SuccessCode.VOTE_SUCCESS, voteResultResponse);
    }

}
