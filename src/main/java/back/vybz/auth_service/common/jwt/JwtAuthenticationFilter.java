package back.vybz.auth_service.common.jwt;

import back.vybz.auth_service.common.util.RedisUtil;
import back.vybz.auth_service.busker.application.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final RedisUtil<String> redisUtil;

    private final AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // HTTP 요청 헤더에서 JWT 토큰을 꺼냄
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String uuid;

        // 요청 헤더에서 토큰 꺼내기.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwt = authHeader.substring(7);

            // 토큰 유효성 검증
            if (!jwtProvider.isInvalidToken(jwt)) {
                throw new RuntimeException("만료되었거나 위조된 토큰입니다.");
            }

            uuid = jwtProvider.extractClaim(jwt, claims -> claims.get("uuid", String.class));

            // accessToken -> Redis 유효성 검증
            if ("access".equals(jwtProvider.extractTokenType(jwt))) {
                String redisAccessToken = redisUtil.get("Access:" + uuid);

                if (redisAccessToken == null || !redisAccessToken.equals(jwt)) {
                    throw new RuntimeException("유효하지 않은 토큰입니다.");
                }
            }

            // 인증 안 되어 있으면 SecurityContext 인증 정보 수동으로 객체로 넣기
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = authService.loadUserByUuid(uuid);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"Unauthorized\"}");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().equals("/api/v1/auth/reissue");
    }
}