package br.bunny.rest.controller.auth;

import br.bunny.domain.model.person.Person;
import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.rest.dto.auth.AuthRequest;
import br.bunny.rest.dto.auth.AuthResponse;
import br.bunny.rest.dto.person.CreatePhysicalPersonDTO;
import br.bunny.security.JwtTokenUtil;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            Person person = (Person) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(person);
            AuthResponse response = new AuthResponse(person.getEmail(), accessToken);

            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            throw new BadRequestException("Invalid email or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody CreatePhysicalPersonDTO createPhysicalPersonDTO) {
        physicalPersonService.savePhysicalPerson(mapper.map(createPhysicalPersonDTO, PhysicalPerson.class));
        return ResponseEntity.ok().build();
    }
}
