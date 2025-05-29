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

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String uuid;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwt = authHeader.substring(7);

            if (jwtProvider.isInvalidToken(jwt)) {
                throw new RuntimeException("만료되었거나 위조된 토큰입니다.");
            }

            uuid = jwtProvider.extractClaim(jwt, claims -> claims.get("uuid", String.class));

            if ("access".equals(jwtProvider.extractTokenType(jwt))) {
                String redisAccessToken = redisUtil.get("Access:" + uuid);

                if (redisAccessToken == null || !redisAccessToken.equals(jwt)) {
                    throw new RuntimeException("유효하지 않은 토큰입니다.");
                }
            }

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
        String uri = request.getRequestURI();
        return uri.equals("/api/v1/busker/reissue") ||
                uri.equals("/api/v1/busker/sign-out") ||
                uri.equals("/api/v1/oauth/reissue") ||
                uri.equals("/api/v1/oauth/sign-out");
    }

}