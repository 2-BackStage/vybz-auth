package back.vybz.auth_service.common.application;

import back.vybz.auth_service.common.entity.BaseResponseStatus;
import back.vybz.auth_service.common.exception.BaseException;
import back.vybz.auth_service.common.jwt.JwtProvider;
import back.vybz.auth_service.common.util.RedisUtil;
import back.vybz.auth_service.common.domain.mysql.User;
import back.vybz.auth_service.common.dto.ResponseSignInDto;
import back.vybz.auth_service.busker.infrastructure.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    private final AuthRepository authRepository;

    private final TokenService tokenService;

    public ResponseSignInDto reissue(String authorization) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        String refreshToken = authorization.replace("Bearer ", "");

        if (!jwtProvider.isInvalidToken(refreshToken)) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        String userUuid = jwtProvider.validateAndGetUserUuid(refreshToken);

        String redisToken = redisUtil.get("Refresh:" + userUuid);

        if (redisToken == null || !redisToken.equals(refreshToken)) {
            throw new BaseException(BaseResponseStatus.REFRESH_TOKEN_NOT_FOUND);
        }

        User user = authRepository.findByUserUuid(userUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OAUTH));

        return tokenService.issueToken(user);

    }
}
