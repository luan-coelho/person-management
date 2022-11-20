package br.bunny.rest.controller.person;

import br.bunny.domain.model.person.Role;
import br.bunny.rest.dto.person.role.ResponseRoleDTO;
import br.bunny.rest.dto.person.role.RoleDTO;
import br.bunny.service.role.RoleService;
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
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<ResponseRoleDTO>> findAllRoles(Pageable pageable) {
        return ResponseEntity.ok(roleService.findAllRoles(pageable).map(person -> mapper.map(person, ResponseRoleDTO.class)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseRoleDTO> findRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.map(roleService.findRoleById(id), ResponseRoleDTO.class));
    }

    @PostMapping
    public ResponseEntity<ResponseRoleDTO> saveRole(@RequestBody @Valid RoleDTO roleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(roleService.saveRole(mapper.map(roleRequest, Role.class)), ResponseRoleDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRoleDTO> updateRole(@PathVariable("id") Long id, @RequestBody @Valid RoleDTO roleRequest) {
        return ResponseEntity.ok(mapper.map(roleService.updateRole(id, mapper.map(roleRequest, Role.class)), ResponseRoleDTO.class));
    }

    @GetMapping("/activateOrDesactivateById/{id}")
    public ResponseEntity<ResponseRoleDTO> activateOrDesactivateRole(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(roleService.activateOrDesactivateRole(id), ResponseRoleDTO.class));
    }
}
