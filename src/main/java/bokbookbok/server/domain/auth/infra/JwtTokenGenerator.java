package bokbookbok.server.domain.auth.infra;

import bokbookbok.server.domain.auth.domain.JwtToken;
import bokbookbok.server.domain.auth.enums.JwtTokenExpireTime;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenGenerator {
    private final Key key;

    public JwtTokenGenerator(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtToken generateToken(String userId, String grantType) {
        // 권한 가져오기
        long now = (new Date()).getTime();

        // Jwt token time
        long accessTokenExpireTime = JwtTokenExpireTime.ACCESS_TOKEN_EXPIRE_TIME.getExpireTime();
        long refreshTokenExpireTime = JwtTokenExpireTime.REFRESH_TOKEN_EXPIRE_TIME.getExpireTime();


        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + accessTokenExpireTime);
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                //.claim("auth", "ROLE_USER")
                .claim("auth", "ROLE_" + grantType.toUpperCase())
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenExpireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}