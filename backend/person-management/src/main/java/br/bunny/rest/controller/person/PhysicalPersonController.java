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
@RequestMapping("/api/physical-person")
public class PhysicalPersonController {

    private final PhysicalPersonService physicalPersonService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<ResponsePhysicalPersonDTO>> findAll(PhysicalPersonFilter filter, Pageable pageable) {
        Page<PhysicalPerson> persons = physicalPersonService.findAll(filter, pageable);
        Page<ResponsePhysicalPersonDTO> dtos = persons.map(person -> mapper.map(person, ResponsePhysicalPersonDTO.class));

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findById(@PathVariable Long id) {
        PhysicalPerson person = physicalPersonService.findById(id);
        ResponsePhysicalPersonDTO dto = mapper.map(person, ResponsePhysicalPersonDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/find-by-cpf/{cpf}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findByCpf(@PathVariable String cpf) {
        PhysicalPerson person = physicalPersonService.findByCpf(cpf);
        ResponsePhysicalPersonDTO dto = mapper.map(person, ResponsePhysicalPersonDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/find-by-email/{email}")
    public ResponseEntity<ResponsePhysicalPersonDTO> findByEmail(@PathVariable String email) {
        PhysicalPerson person = physicalPersonService.findByEmail(email);
        ResponsePhysicalPersonDTO dto = mapper.map(person, ResponsePhysicalPersonDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ResponsePhysicalPersonDTO> save(@RequestBody @Valid CreatePhysicalPersonDTO physicalPersonRequest) {
        if (physicalPersonService.existsByEmail(physicalPersonRequest.getEmail()))
            throw new BadRequestException("There is already a person registered with this email");
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(physicalPersonService.save(mapper.map(physicalPersonRequest, PhysicalPerson.class)), ResponsePhysicalPersonDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> update(@PathVariable("id") Long id, @RequestBody @Valid UpdatePhysicalPersonDTO physicalPersonRequest) {
        PhysicalPerson personRequest = mapper.map(physicalPersonRequest, PhysicalPerson.class);
        PhysicalPerson personUpdated = physicalPersonService.update(id, personRequest);
        ResponsePhysicalPersonDTO dto = mapper.map(personUpdated, ResponsePhysicalPersonDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/change-activity/{id}")
    public ResponseEntity<ResponsePhysicalPersonDTO> changeActivity(@PathVariable Long id) {
        PhysicalPerson person = physicalPersonService.changeActivity(id);
        ResponsePhysicalPersonDTO dto = mapper.map(person, ResponsePhysicalPersonDTO.class);

        return ResponseEntity.ok(dto);
    }
}
