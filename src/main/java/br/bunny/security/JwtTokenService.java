package br.bunny.security;

import br.bunny.rest.dto.auth.AuthRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Service
public class JwtTokenService {

    @Value("${security.jwt.token.secret-key}")
    private String SECRET_KEY;
    @Value("${security.jwt.token.expiration}")
    private String EXPIRATION;

    public String buildToken(AuthRequest authRequest) {
        long expiration = Long.parseLong(EXPIRATION);
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(expiration);
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        String expirationTime = localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

        return Jwts
                .builder()
                .setExpiration(date)
                .setSubject(authRequest.getEmail())
                .claim("expirationTime", expirationTime)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaims(token);
            Date date = claims.getExpiration();
            LocalDateTime expiration = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            boolean dataHoraIsAfterExpiration = LocalDateTime.now().isAfter(expiration);
            return !dataHoraIsAfterExpiration;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public String getLogin(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
}
