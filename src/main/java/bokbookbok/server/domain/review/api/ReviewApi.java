package bokbookbok.server.domain.review.api;

import bokbookbok.server.domain.review.application.ReviewService;
import bokbookbok.server.domain.review.dto.request.CreateReviewRequest;
import bokbookbok.server.domain.review.dto.response.CreateReviewResponse;
import bokbookbok.server.domain.user.domain.User;
import bokbookbok.server.global.config.common.codes.SuccessCode;
import bokbookbok.server.global.config.common.response.BaseResponse;
import bokbookbok.server.global.config.resolver.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ReviewApi {

    private final ReviewService reviewService;

    @PostMapping("/write")
    @Operation(summary = "감상평 작성")
    public BaseResponse<CreateReviewResponse> writeReview(
            @Parameter(hidden = true) @CurrentUser User user,
            @RequestBody @Valid CreateReviewRequest createReviewRequest
    ) {
        CreateReviewResponse createReviewResponse = reviewService.createReview(user, createReviewRequest);
        return BaseResponse.of(SuccessCode.WRITE_REVIEW_SUCCESS, createReviewResponse);
    }
}
