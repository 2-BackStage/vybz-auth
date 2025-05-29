package back.vybz.auth_service.common.application;

import back.vybz.auth_service.common.jwt.JwtProvider;
import back.vybz.auth_service.common.util.RedisUtil;
import back.vybz.auth_service.common.domain.mysql.User;
import back.vybz.auth_service.common.dto.ResponseSignInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    public ResponseSignInDto issueToken(User user) {

        // Authentication 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUserUuid(), null, List.of(new SimpleGrantedAuthority(user.getRole().name())));

        // JWT 발급 (jwtProvider 사용)
        String accessToken = jwtProvider.createAccessToken(authentication);
        String refreshToken = jwtProvider.createRefreshToken(authentication);

        // redis 저장
        redisUtil.save("Access:" + user.getUserUuid(), accessToken, 30, TimeUnit.MINUTES);
        redisUtil.save("Refresh:" + user.getUserUuid(), refreshToken, 15, TimeUnit.DAYS);

        // 응답 dto 만들어 반환
        return ResponseSignInDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userUuid(user.getUserUuid())
                .build();
    }
}
