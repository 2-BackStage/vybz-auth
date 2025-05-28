package back.vybz.auth_service.user.application;

import back.vybz.auth_service.common.entity.BaseResponseStatus;
import back.vybz.auth_service.common.exception.BaseException;
import back.vybz.auth_service.common.jwt.JwtProvider;
import back.vybz.auth_service.common.util.RedisUtil;
import back.vybz.auth_service.user.domain.mysql.User;
import back.vybz.auth_service.user.dto.out.ResponseOAuthSignInDto;
import back.vybz.auth_service.user.infrastructure.AuthRepository;
import back.vybz.auth_service.user.vo.in.RequestReissueVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    private final AuthRepository authRepository;

    private final TokenService tokenService;

    public ResponseOAuthSignInDto reissue(RequestReissueVo requestReissueVo) {

        String bearerToken = requestReissueVo.getRefreshToken();

        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        String refreshToken = bearerToken.replace("Bearer ", "");

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        // 2. 사용자 UUID 추출
        String userUuid = jwtProvider.validateAndGetUserUuid(refreshToken);

        // 3. Redis 저장된 Refresh Token 꺼내서 비교
        String redisToken = redisUtil.get("Refresh:" + userUuid);
        if (redisToken == null || !redisToken.equals(refreshToken)) {
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_NOT_FOUND);
        }

        // 4. 사용자 조회
        User user = authRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OAUTH));

        // 5. 새 토큰 발급
        return tokenService.issueToken(user);
    }
}
