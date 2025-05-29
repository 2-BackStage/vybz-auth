package back.vybz.auth_service.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final Environment env;

    public String validateAndGetUserUuid(String token) throws IllegalArgumentException {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("토큰에 담긴 유저 정보가 없습니다");
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 로그인 시 accessToken 발급 (uuid, role 담아서 JWT 토큰 생성)
    public String createAccessToken(Authentication authentication) {

        // 1. 사용자 정보 추출 - userUuid
        String userUuid = authentication.getName();

        // 1. 사용자 정보 추출 - role
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new IllegalArgumentException("권한이 없습니다."));

        Date now = new Date();
    Date expiration = new Date(now.getTime() + env.getProperty("JWT.token.access-expire-time", Long.class, 1800000L));

        return Jwts.builder()
                .claim("uuid", userUuid)
                .claim("role", role)
                .claim("token_type", "access")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    // refreshToken 발급
    public String createRefreshToken(Authentication authentication) {

        String userUuid = authentication.getName();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + env.getProperty("JWT.token.refresh-expire-time", Long.class, 1296000000L));

        return Jwts.builder()
                .subject(userUuid)
                .claim("token_type", "refresh")
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractTokenType(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("token_type", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    // JWT 서명에 사용할 비밀키(SecretKey) 반환
    public SecretKey getSignKey() {
        String secret = env.getProperty("JWT.secret-key");
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public boolean isInvalidToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("❌ 만료된 JWT 토큰입니다: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("❌ 지원하지 않는 JWT 토큰입니다: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("❌ 잘못된 형식의 JWT 토큰입니다: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("❌ JWT claims 비어있습니다: {}", e.getMessage());
        } catch (Exception e) {
            log.error("❌ JWT 토큰 검증 중 알 수 없는 오류 발생: {}", e.getMessage());
        }
        return false;
    }
}