package br.bunny.rest.controller.auth;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.rest.dto.auth.AuthRequest;
import br.bunny.rest.dto.auth.AuthResponse;
import br.bunny.rest.dto.person.CreatePhysicalPersonDTO;
import br.bunny.rest.dto.person.ResponsePhysicalPersonDTO;
import br.bunny.security.JwtTokenService;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        physicalPersonService.save(mapper.map(createPhysicalPersonDTO, PhysicalPerson.class));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-authenticated-user")
    public ResponseEntity<ResponsePhysicalPersonDTO> getAuthenticatedUser() {
        return ResponseEntity.ok(mapper.map(physicalPersonService.getAuthenticated(), ResponsePhysicalPersonDTO.class));
    }

    @GetMapping("/verify-token/{token}")
    public ResponseEntity<Void> verifyToken(@PathVariable String token) {
        if (!jwtUtil.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }
}
