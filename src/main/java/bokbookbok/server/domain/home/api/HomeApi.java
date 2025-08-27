package bokbookbok.server.domain.home.api;

import bokbookbok.server.domain.home.application.HomeService;
import bokbookbok.server.domain.home.dto.response.HomeResponse;
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
@RequestMapping("/api/home")
public class HomeApi {

    private final HomeService homeService;
    @GetMapping
    @Operation(summary = "메인 화면", description = "유저 독서 상태별로 다름")
    public BaseResponse<HomeResponse> getHome(@Parameter(hidden = true) @CurrentUser User user) {
        HomeResponse homeResponse = homeService.getHome(user);
        return BaseResponse.of(SuccessCode.GET_HOME_INFO, homeResponse);
    }
}
