package br.bunny.service.role;

import br.bunny.domain.model.person.Role;
import br.bunny.domain.repository.role.RoleRepository;
import br.bunny.exception.validation.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper mapper;

    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new BadRequestException("Role not found by id"));
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByNameEqualsIgnoreCase(name).orElseThrow(() -> new BadRequestException("Role not found by name"));
    }

    public Role saveRole(Role role) {
        if (roleRepository.existsByNameIgnoreCase(role.getName()))
            throw new BadRequestException("There is already a role registered with this name");
        return roleRepository.save(role);
    }

    public Role updateRole(UUID id, Role role) {
        Optional<Role> roleOriginal = roleRepository.findById(id);
        roleOriginal.orElseThrow(() -> new BadRequestException("Role not found by id"));

        if (role.getName() != null && !role.getName().trim().equalsIgnoreCase(roleOriginal.get().getName().trim())) {
            if (roleRepository.existsByNameIgnoreCase(role.getName()))
                throw new BadRequestException("There is already a registered role with the name provided");
        }

        role.setId(id);
        mapper.map(role, roleOriginal.get());

        return roleRepository.save(roleOriginal.get());
    }

    public Role activateOrDesactivateRole(UUID id) {
        Optional<Role> role = roleRepository.findById(id);
        role.orElseThrow(() -> new BadRequestException("Role not found by id"));

        role.get().setActive(!role.get().isActive());
        return roleRepository.save(role.get());
    }
}
