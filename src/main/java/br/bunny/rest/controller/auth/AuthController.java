package br.bunny.rest.controller.auth;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.rest.dto.auth.AuthRequest;
import br.bunny.rest.dto.auth.AuthResponse;
import br.bunny.rest.dto.person.CreatePhysicalPersonDTO;
import br.bunny.security.JwtTokenService;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PhysicalPersonService physicalPersonService;
    private final ModelMapper mapper;
    private final JwtTokenService jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        physicalPersonService.verifyCredentials(request.getEmail(), request.getPassword());
        String accessToken = jwtUtil.buildToken(request);
        AuthResponse response = new AuthResponse(request.getEmail(), accessToken);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CreatePhysicalPersonDTO createPhysicalPersonDTO) {
        physicalPersonService.savePhysicalPerson(mapper.map(createPhysicalPersonDTO, PhysicalPerson.class));
        return ResponseEntity.ok().build();
    }
}
