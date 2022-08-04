package br.bunny.controller.person;

import br.bunny.dto.person.CreatePhysicalPersonDTO;
import br.bunny.dto.person.ResponsePhysicalPersonDTO;
import br.bunny.dto.person.UpdatePhysicalPersonDTO;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.filter.PhysicalPersonFilter;
import br.bunny.model.person.PhysicalPerson;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/physical-person")
public class PhysicalPersonController {

    private final PhysicalPersonService physicalPersonService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<ResponsePhysicalPersonDTO>> findAllPhysicalPersons(PhysicalPersonFilter filter, Pageable pageable) {
        return ResponseEntity.ok(physicalPersonService.findAllPhysicalPerson(filter, pageable)
                .map(person -> mapper.map(person, ResponsePhysicalPersonDTO.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findPhysicalPersonById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.map(physicalPersonService.findPhysicalPersonById(id), ResponsePhysicalPersonDTO.class));
    }

    @GetMapping("/findbycpf/{cpf}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findPhysicalPersonByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(mapper.map(physicalPersonService.findPhysicalPersonByCpf(cpf), ResponsePhysicalPersonDTO.class));
    }

    @GetMapping("/findbyemail/{email}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findPhysicalPersonByEmail(@PathVariable String email) {
        return ResponseEntity.ok(mapper.map(physicalPersonService.findPhysicalPersonByEmail(email), ResponsePhysicalPersonDTO.class));
    }

    @PostMapping
    public ResponseEntity<ResponsePhysicalPersonDTO> createPhysicalPerson(@RequestBody @Valid CreatePhysicalPersonDTO physicalPersonRequest) {
        if (physicalPersonService.existsPhysicalPersonByEmail(physicalPersonRequest.getEmail()))
            throw new BadRequestException("Já existe um usuário cadastrado com este email.");
        PhysicalPerson physicalPerson = new PhysicalPerson();
        mapper.map(physicalPersonRequest, physicalPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(physicalPersonService.savePhysicalPerson(physicalPerson), ResponsePhysicalPersonDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> updatePhysicalPerson(@PathVariable("id") UUID id, @RequestBody @Valid UpdatePhysicalPersonDTO physicalPersonRequest) {
        PhysicalPerson physicalPerson = new PhysicalPerson();
        physicalPersonRequest.setId(id);
        mapper.map(physicalPersonRequest, physicalPerson);
        return ResponseEntity.ok(mapper.map(physicalPersonService.updatePhysicalPerson(physicalPerson), ResponsePhysicalPersonDTO.class));
    }

    @GetMapping("/activateOrDesactivate/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> activateOrDesactivatePhysicalPerson(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(mapper.map(physicalPersonService.activateOrDesactivatePhysicalPerson(id), ResponsePhysicalPersonDTO.class));
    }
}
