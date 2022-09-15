package br.bunny.rest.controller.person;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.filter.PhysicalPersonFilter;
import br.bunny.rest.dto.person.CreatePhysicalPersonDTO;
import br.bunny.rest.dto.person.ResponsePhysicalPersonDTO;
import br.bunny.rest.dto.person.UpdatePhysicalPersonDTO;
import br.bunny.service.person.PhysicalPersonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/physical-persons")
public class PhysicalPersonController {

    private final PhysicalPersonService physicalPersonService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<ResponsePhysicalPersonDTO>> findAllPhysicalPersons(PhysicalPersonFilter filter, Pageable pageable) {
        return ResponseEntity.ok(physicalPersonService.findAllPhysicalPerson(filter, pageable).map(person -> mapper.map(person, ResponsePhysicalPersonDTO.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findPhysicalPersonById(@PathVariable Long id) {
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

    @PostMapping("/save")
    public ResponseEntity<ResponsePhysicalPersonDTO> savePhysicalPerson(@RequestBody @Valid CreatePhysicalPersonDTO physicalPersonRequest) {
        if (physicalPersonService.existsPhysicalPersonByEmail(physicalPersonRequest.getEmail()))
            throw new BadRequestException("There is already a person registered with this email");
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(physicalPersonService.savePhysicalPerson(mapper.map(physicalPersonRequest, PhysicalPerson.class)), ResponsePhysicalPersonDTO.class));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> updatePhysicalPerson(@PathVariable("id") Long id, @RequestBody @Valid UpdatePhysicalPersonDTO physicalPersonRequest) {
        return ResponseEntity.ok(mapper.map(physicalPersonService.updatePhysicalPerson(id, mapper.map(physicalPersonRequest, PhysicalPerson.class)), ResponsePhysicalPersonDTO.class));
    }

    @GetMapping("/activateOrDesactivateById/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> activateOrDesactivatePhysicalPerson(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(physicalPersonService.activateOrDesactivatePhysicalPerson(id), ResponsePhysicalPersonDTO.class));
    }
}
