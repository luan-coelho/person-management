package br.bunny.rest.controller.auth;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.rest.dto.person.CreatePhysicalPersonDTO;
import br.bunny.rest.dto.person.LoginPhysicalPersonDTO;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
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
}
