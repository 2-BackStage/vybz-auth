package back.vybz.auth_service.user.presentation;

import back.vybz.auth_service.common.entity.BaseResponseEntity;
import back.vybz.auth_service.common.entity.BaseResponseStatus;
import back.vybz.auth_service.common.exception.BaseException;
import back.vybz.auth_service.user.application.OAuthService;
import back.vybz.auth_service.user.application.ReissueService;
import back.vybz.auth_service.user.dto.in.RequestOAuthSignInDto;
import back.vybz.auth_service.user.dto.in.RequestOAuthSignOutDto;
import back.vybz.auth_service.user.dto.out.ResponseOAuthSignInDto;
import back.vybz.auth_service.user.vo.in.RequestOAuthSignInVo;
import back.vybz.auth_service.user.vo.in.RequestReissueVo;
import back.vybz.auth_service.user.vo.out.ResponseOAuthSignInVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    private final ReissueService reissueService;

    @Operation(
            summary = "카카오 로그인 API",
            description = "카카오 로그인 API 입니다.",
            tags = {"AUTH-SERVICE"}
    )
    @PostMapping("/sign-in")
    public BaseResponseEntity<ResponseOAuthSignInVo> signIn(
            @RequestBody RequestOAuthSignInVo requestOAuthSignInVo
            ) {

        RequestOAuthSignInDto dto = RequestOAuthSignInDto.from(requestOAuthSignInVo);

        ResponseOAuthSignInDto resultDto = oAuthService.signIn(dto);

        return new BaseResponseEntity<>(ResponseOAuthSignInVo.from(resultDto));
    }

    @Operation(
            summary = "액세스 토큰 재발급 API",
            description = "리프레시 토큰을 이용해 액세스 토큰을 재발급하는 API 입니다.",
            tags = {"AUTH-SERVICE"}
    )
    @PostMapping("/reissue")
    public BaseResponseEntity<ResponseOAuthSignInVo> reissue(
            @RequestBody RequestReissueVo requestReissueVo) {

        ResponseOAuthSignInDto result = reissueService.reissue(requestReissueVo);

        return new BaseResponseEntity<>( ResponseOAuthSignInVo.from(result));
    }

    @Operation(
            summary = "로그아웃 API",
            description = "로그아웃을 위한 API 입니다.",
            tags = {"AUTH-SERVICE"}
    )
    @DeleteMapping("/sign-out")
    public BaseResponseEntity<Void> logout(
            @RequestBody RequestOAuthSignOutDto requestOAuthSignOutDto
    ) {
        oAuthService.signOut(requestOAuthSignOutDto);

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
