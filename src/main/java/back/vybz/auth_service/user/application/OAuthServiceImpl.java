package back.vybz.auth_service.user.application;

import back.vybz.auth_service.common.application.TokenService;
import back.vybz.auth_service.common.entity.BaseResponseStatus;
import back.vybz.auth_service.common.exception.BaseException;
import back.vybz.auth_service.common.jwt.JwtProvider;
import back.vybz.auth_service.common.util.RedisUtil;
import back.vybz.auth_service.common.domain.mysql.Role;
import back.vybz.auth_service.common.domain.mysql.SocialType;
import back.vybz.auth_service.common.domain.mysql.Status;
import back.vybz.auth_service.common.domain.mysql.User;
import back.vybz.auth_service.user.dto.in.RequestOAuthSignInDto;
import back.vybz.auth_service.common.dto.ResponseSignInDto;
import back.vybz.auth_service.user.infrastructure.OAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

    private final OAuthRepository oAuthRepository;

    private final TokenService tokenService;

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    @Transactional
    @Override
    public ResponseSignInDto signIn(RequestOAuthSignInDto requestOAuthSignInDto) {

        String providerId = requestOAuthSignInDto.getProviderId();
        String email = requestOAuthSignInDto.getEmail();

        SocialType socialType = SocialType.valueOf(requestOAuthSignInDto.getProvider().toUpperCase());

        User user = oAuthRepository.findBySocialTypeAndProviderId(socialType, providerId)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .userUuid(UUID.randomUUID().toString())
                            .providerId(providerId)
                            .socialType(socialType)
                            .email(email)
                            .role(Role.USER)
                            .status(Status.ACTIVE)
                            .build();

                    return oAuthRepository.save(newUser);
                });

        return tokenService.issueToken(user);
    }

    @Transactional
    @Override
    public void signOut(String refreshToken) {

        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new BaseException(BaseResponseStatus.INVALID_REFRESH_TOKEN);
        }

        refreshToken = refreshToken.replace("Bearer ", "");

        String uuid = jwtProvider.validateAndGetUserUuid(refreshToken);

        redisUtil.delete("Access:" + uuid);
        redisUtil.delete("Refresh:" + uuid);
    }
}
