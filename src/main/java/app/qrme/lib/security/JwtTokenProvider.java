package app.qrme.lib.security;

import app.qrme.lib.utils.BaseConstants;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class JwtTokenProvider {

    private final String AUDIENCE_UNKNOWN = "unknown";
    private final String AUDIENCE_WEB = "web";
    private final String AUDIENCE_MOBILE = "mobile";
    private final String AUDIENCE_TABLET = "tablet";

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public BaseConstants.JwtTokenResult validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody();
            return BaseConstants.JwtTokenResult.VALID_TOKEN;
        } catch (SignatureException ex) {
            return BaseConstants.JwtTokenResult.EXC_SIGNATURE;
        } catch (MalformedJwtException ex) {
            return BaseConstants.JwtTokenResult.EXC_MAILFORMED;
        } catch (ExpiredJwtException ex) {
            return BaseConstants.JwtTokenResult.EXC_EXPIRED;
        } catch (UnsupportedJwtException ex) {
            return BaseConstants.JwtTokenResult.EXC_UNSUPPORTED;
        } catch (IllegalArgumentException ex) {
            return BaseConstants.JwtTokenResult.EXC_ILLEGAL_ARGUMENT;
        }
    }

    public String getAuthType(String token) {
        String audience;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            audience = (String) claims.get("type");
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public Long getIdFromToken(String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            return Long.valueOf(claims.getId());
        } catch (Exception e) {
        }
        return null;
    }

    public Long parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        if (claims.getSubject().contains(BaseConstants.Common.score)) {
            String id = claims.getSubject().split(BaseConstants.Common.score)[0];
            return Long.parseLong(id);
        }
        return null;
    }


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String generateExpirableToken(Authentication authentication) {
        LocalDateTime ldt = LocalDateTime.now().plusDays(1);
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        SpringSecurityUser userPrincipal = (SpringSecurityUser) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("jti", userPrincipal.getId());
        claims.put("type", "USER");
        return Jwts.builder()
                .setSubject(AUDIENCE_WEB)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(out)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

}