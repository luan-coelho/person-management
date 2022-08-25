package br.bunny.rest.controller.auth;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.domain.model.person.Role;
import br.bunny.rest.dto.person.CreatePhysicalPersonDTO;
import br.bunny.rest.dto.person.LoginPhysicalPersonDTO;
import br.bunny.service.person.PhysicalPersonService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PhysicalPersonService physicalPersonService;
    private final ModelMapper mapper;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginPhysicalPersonDTO loginPhysicalPersonDTO) {
        physicalPersonService.authenticatePerson(loginPhysicalPersonDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CreatePhysicalPersonDTO createPhysicalPersonDTO) {
        physicalPersonService.savePhysicalPerson(mapper.map(createPhysicalPersonDTO, PhysicalPerson.class));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/reflesh")
    public void refleshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refleshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secrect".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refleshToken);
                String personEmail = decodedJWT.getSubject();
                PhysicalPerson user = physicalPersonService.findPhysicalPersonByEmail(personEmail);
                String acessToken = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles", user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", acessToken);
                tokens.put("reflesh_token", refleshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                //response.sendError(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Reflesh token is missing");
        }
    }
}
