package br.bunny.service.person;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.domain.model.person.Role;
import br.bunny.domain.repository.person.PhysicalPersonRepository;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.filter.PhysicalPersonFilter;
import br.bunny.rest.dto.person.LoginPhysicalPersonDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class PhysicalPersonService {

    final private PhysicalPersonRepository physicalPersonRepository;
    final private br.bunny.service.role.RoleService roleService;
    final private ModelMapper mapper;

    public Page<PhysicalPerson> findAllPhysicalPerson(PhysicalPersonFilter filter, Pageable pageable) {
        return physicalPersonRepository.findAll(filter.toSpec(), pageable);
    }

    public PhysicalPerson findPhysicalPersonById(UUID id) {
        return physicalPersonRepository.findById(id).orElseThrow(() -> new BadRequestException("Person not found by id"));
    }

    public PhysicalPerson findPhysicalPersonByCpf(String cpf) {
        return physicalPersonRepository.findByCpf(cpf).orElseThrow(() -> new BadRequestException("Person not found by CPF"));
    }

    public PhysicalPerson findPhysicalPersonByEmail(String email) {
        return physicalPersonRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Person not found by email"));
    }

    public PhysicalPerson findByEmailAndCpf(String email, String cpf) {
        return physicalPersonRepository.findByEmailEqualsIgnoreCaseAndCpf(email, cpf).orElseThrow(() -> new BadRequestException("Person not found. Check if the email and CPF are correct"));
    }

    public boolean existsPhysicalPersonByEmail(String email) {
        return physicalPersonRepository.existsByEmail(email);
    }

    public boolean existsPhysicalPersonByCpf(String cpf) {
        return physicalPersonRepository.existsByCpf(cpf);
    }

    public PhysicalPerson savePhysicalPerson(PhysicalPerson person) {
        try {
            if (existsPhysicalPersonByCpf(person.getCpf()))
                throw new BadRequestException("There is already a person registered with the CPF provided");
            if (existsPhysicalPersonByEmail(person.getEmail()))
                throw new BadRequestException("There is already a registered user with the CPF provided");


            addRoleToPerson(person);

            return physicalPersonRepository.save(person);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to save person");
        }
    }

    private void addRoleToPerson(PhysicalPerson person) {
        if (person.getRoles() != null) {
            for (int i = 0; i < person.getRoles().size(); i++) {
                UUID roleId = person.getRoles().get(i).getId();
                Role role = roleService.findRoleById(roleId);
                if (role == null) throw new BadRequestException("Role not found by id");
                person.getRoles().set(i, role);
            }
        } else {
            Role defaultRole = roleService.findRoleByName("USER");
            person.setRoles(List.of(defaultRole));
        }
    }

    public PhysicalPerson updatePhysicalPerson(UUID id, PhysicalPerson person) {
        try {
            Optional<PhysicalPerson> personOptional = physicalPersonRepository.findById(id);
            personOptional.orElseThrow(() -> new BadRequestException("Person not found by id"));

            if (person.getEmail() != null && !person.getEmail().trim().equalsIgnoreCase(personOptional.get().getEmail().trim())) {
                if (existsPhysicalPersonByEmail(person.getEmail()))
                    throw new BadRequestException("There is already a registered user with the email provided");
            }

            addRoleToPerson(person);

            person.setId(id);
            mapper.map(person, personOptional.get());

            return physicalPersonRepository.save(personOptional.get());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to update person");
        }
    }

    public PhysicalPerson activateOrDesactivatePhysicalPerson(UUID id) {
        Optional<PhysicalPerson> physicalPerson = physicalPersonRepository.findById(id);
        physicalPerson.orElseThrow(() -> new BadRequestException("Person not found by id"));

        physicalPerson.get().setActive(!physicalPerson.get().isActive());
        return physicalPersonRepository.save(physicalPerson.get());
    }

    public void authenticatePerson(LoginPhysicalPersonDTO fields) {
        if (!physicalPersonRepository.existsByEmailIgnoreCase(fields.getEmail()))
            throw new BadRequestException("There is no person registered with the email provided");
        if (!physicalPersonRepository.existsByEmailIgnoreCaseAndPassword(fields.getEmail(), fields.getPassword()))
            throw new BadRequestException("Invalid email or password");
    }
}
