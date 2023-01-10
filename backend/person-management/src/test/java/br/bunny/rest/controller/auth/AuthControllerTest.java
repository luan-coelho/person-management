package br.bunny.rest.controller.auth;

import br.bunny.rest.dto.auth.AuthRequest;
import br.bunny.security.JwtTokenService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private JwtTokenService jwtTokenService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void login() {
    }

    @Test
    @DisplayName("Verifica se o subject do token é o mesmo passado na criação do token")
    void verificarTokenLogin() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("lumyth.br@gmail.com")
                .password("123")
                .build();
        String token = jwtTokenService.buildToken(authRequest);

        Claims claims = jwtTokenService.getClaims(token);

        Assertions.assertEquals(claims.getSubject(), "lumyth.br@gmail.com");
    }

    @Test
    @DisplayName("Verifica tempo de expiração do token")
    void verificarExpiracaoToken() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("lumyth.br@gmail.com")
                .password("123")
                .build();
        String token = jwtTokenService.buildToken(authRequest);

        Claims claims = jwtTokenService.getClaims(token);

        LocalDateTime dataExpiracaoToken = claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().truncatedTo(ChronoUnit.SECONDS);;
        LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(1).truncatedTo(ChronoUnit.SECONDS);;

        Assertions.assertEquals(dataExpiracaoToken, dataExpiracao);
    }
}