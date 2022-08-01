package br.bunny.controller.person;

import br.bunny.dto.person.RequestPhysicalPersonDTO;
import br.bunny.dto.person.ResponsePhysicalPersonDTO;
import br.bunny.exception.NotFoundException;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.model.person.PhysicalPerson;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/physical-person")
public class PhysicalPersonController {

    private final PhysicalPersonService physicalPersonService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ResponsePhysicalPersonDTO>> findAllPhysicalPersons() {
        return ResponseEntity.ok(physicalPersonService.findAllPhysicalPerson()
                .stream()
                .map(person -> mapper.map(person, ResponsePhysicalPersonDTO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findPhysicalPersonById(@PathVariable UUID id) {
        if (!physicalPersonService.existsPhysicalPersonById(id))
            throw new NotFoundException("Usuário não encontrado.");
        return ResponseEntity.ok(mapper.map(physicalPersonService.findPhysicalPersonById(id), ResponsePhysicalPersonDTO.class));
    }

    @PostMapping
    public ResponseEntity<ResponsePhysicalPersonDTO> createPhysicalPerson(@RequestBody @Valid RequestPhysicalPersonDTO physicalPersonRequest) {
        if (physicalPersonService.existsPhysicalPersonByEmail(physicalPersonRequest.getEmail()))
            throw new BadRequestException("Já existe um usuário cadastrado com este email.");
        PhysicalPerson physicalPerson = new PhysicalPerson();
        mapper.map(physicalPersonRequest, physicalPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(physicalPersonService.savePhysicalPerson(physicalPerson), ResponsePhysicalPersonDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> updatePhysicalPerson(@PathVariable("id") UUID id, @RequestBody @Valid RequestPhysicalPersonDTO physicalPersonRequest) {
        if (!physicalPersonService.existsPhysicalPersonById(id))
            throw new NotFoundException("Usuário não encontrado.");
        PhysicalPerson physicalPerson = new PhysicalPerson();
        physicalPersonRequest.setId(id);
        mapper.map(physicalPersonRequest, physicalPerson);
        return ResponseEntity.ok(mapper.map(physicalPersonService.updatePhysicalPerson(physicalPerson), ResponsePhysicalPersonDTO.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> activateOrDesactivatePhysicalPerson(@PathVariable("id") UUID id, boolean ativo) {
        if (!physicalPersonService.existsPhysicalPersonById(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.map(physicalPersonService.activateOrDesactivatePhysicalPerson(id, ativo), ResponsePhysicalPersonDTO.class));
    }
}
