package back.vybz.auth_service.busker.presentation;

import back.vybz.auth_service.busker.application.AuthService;
import back.vybz.auth_service.busker.dto.in.RequestAuthSignInDto;
import back.vybz.auth_service.busker.dto.in.RequestExistsEmailDto;
import back.vybz.auth_service.busker.dto.in.RequestSignUpDto;
import back.vybz.auth_service.busker.vo.in.RequestAuthSignInVo;
import back.vybz.auth_service.busker.vo.in.RequestExistsEmailVo;
import back.vybz.auth_service.busker.vo.in.RequestSignUpVo;
import back.vybz.auth_service.common.application.ReissueService;
import back.vybz.auth_service.common.dto.ResponseSignInDto;
import back.vybz.auth_service.common.entity.BaseResponseEntity;
import back.vybz.auth_service.common.entity.BaseResponseStatus;
import back.vybz.auth_service.common.vo.ResponseSignInVo;
import back.vybz.auth_service.user.application.OAuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/busker")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final OAuthService oAuthService;

    private final ReissueService reissueService;

    @Operation(summary = "Busker Sign-Up API", description = "버스커 회원가입", tags = {"Auth-service"})
    @PostMapping("/sign-up")
    public BaseResponseEntity<Void> signUp(
            @Valid @RequestBody RequestSignUpVo requestSignUpVo
    ) {
        authService.signUp(RequestSignUpDto.from(requestSignUpVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SIGN_UP_SUCCESS);
    }

    @Operation(summary = "Busker Check Email API", description = "이메일 중복 확인", tags = {"Auth-service"})
    @PostMapping("/check/email")
    public BaseResponseEntity<Boolean> emailExists(
            @Valid @RequestBody RequestExistsEmailVo requestExistsEmailVo
    ) {
        return new BaseResponseEntity<>(
                authService.existsEmail(RequestExistsEmailDto.from(requestExistsEmailVo).getEmail())
        );
    }

    @Operation(summary = "Busker SignIn API", description = "버스커 로그인", tags = {"Auth-service"})
    @PostMapping("/sign-in")
    public BaseResponseEntity<ResponseSignInVo> signIn(
            @Valid @RequestBody RequestAuthSignInVo requestAuthSignInVo
    ) {

        ResponseSignInDto responseSignInDto = authService.signIn(RequestAuthSignInDto.from(requestAuthSignInVo));

        return new BaseResponseEntity<>(
                BaseResponseStatus.SIGN_IN_SUCCESS, ResponseSignInVo.from(responseSignInDto)
        );
    }

    @Operation(
            summary = "Busker 액세스 토큰 재발급 API",
            description = "Busker 리프레시 토큰을 이용해 액세스 토큰을 재발급하는 API 입니다.",
            tags = {"Auth-SERVICE"}
    )
    @PostMapping("/reissue")
    public BaseResponseEntity<ResponseSignInVo> buskerReissue(
            @RequestHeader("Authorization") String authorization
    ) {

        ResponseSignInDto resultDto = reissueService.reissue(authorization);

        return new BaseResponseEntity<>(ResponseSignInVo.from(resultDto));
    }

    @Operation(
            summary = "Busker SignOut API",
            description = "로그아웃을 위한 API 입니다.",
            tags = {"Auth-SERVICE"}
    )
    @PostMapping("/sign-out")
    public BaseResponseEntity<Void> buskerLogout(
            @RequestHeader("Authorization") String authorization
    ) {
        oAuthService.signOut(authorization);

        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
