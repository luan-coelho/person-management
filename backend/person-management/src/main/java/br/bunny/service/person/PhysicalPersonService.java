package br.bunny.service.person;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.domain.model.person.Role;
import br.bunny.domain.repository.person.PhysicalPersonRepository;
import br.bunny.exception.auth.UsernameNotFoundException;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.filter.PhysicalPersonFilter;
import br.bunny.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhysicalPersonService {

    final private PhysicalPersonRepository physicalPersonRepository;
    final private RoleService roleService;
    final private ModelMapper mapper;
    final private PasswordEncoder passwordEncoder;

    public Page<PhysicalPerson> findAll(PhysicalPersonFilter filter, Pageable pageable) {
        return physicalPersonRepository.findAll(filter.toSpec(), pageable);
    }

    public void verifyCredentials(String email, String password) {
        PhysicalPerson person = physicalPersonRepository.findByEmailIgnoreCase(email).orElseThrow(UsernameNotFoundException::new);
        if (!passwordEncoder.matches(password, person.getPassword())) {
            throw new UsernameNotFoundException();
        }
    }

    public PhysicalPerson findById(Long id) {
        return physicalPersonRepository.findById(id).orElseThrow(() -> new BadRequestException("Person not found by id"));
    }

    public PhysicalPerson findByCpf(String cpf) {
        return physicalPersonRepository.findByCpf(cpf).orElseThrow(() -> new BadRequestException("Person not found by CPF"));
    }

    public PhysicalPerson findByEmail(String email) {
        return physicalPersonRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new BadRequestException("Person not found by email"));
    }

    public PhysicalPerson findByEmailAndCpf(String email, String cpf) {
        return physicalPersonRepository.findByEmailEqualsIgnoreCaseAndCpf(email, cpf).orElseThrow(() -> new BadRequestException("Person not found. Check if the email and CPF are correct"));
    }

    public boolean existsByEmail(String email) {
        return physicalPersonRepository.existsByEmail(email);
    }

    public boolean existsByCpf(String cpf) {
        return physicalPersonRepository.existsByCpf(cpf);
    }

    public PhysicalPerson save(PhysicalPerson person) {
        try {
            if (existsByCpf(person.getCpf()))
                throw new BadRequestException("There is already a person registered with the CPF provided");
            if (existsByEmail(person.getEmail()))
                throw new BadRequestException("There is already a registered user with the CPF provided");


            addRole(person);

            person.setPassword(passwordEncoder.encode(person.getPassword()));

            return physicalPersonRepository.save(person);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to save person");
        }
    }

    private void addRole(PhysicalPerson person) {
        if (person.getRoles() != null) {
            for (int i = 0; i < person.getRoles().size(); i++) {
                Long roleId = person.getRoles().get(i).getId();
                Role role = roleService.findRoleById(roleId);
                if (role == null) throw new BadRequestException("Role not found by id");
                person.getRoles().set(i, role);
            }
        } else {
//            Role defaultRole = roleService.findRoleByName("USER");
            Role defaultRole;
            defaultRole = new Role("USER");
            person.setRoles(List.of(defaultRole));
        }
    }

    public PhysicalPerson update(Long id, PhysicalPerson person) {
        try {
            Optional<PhysicalPerson> personOptional = physicalPersonRepository.findById(id);
            personOptional.orElseThrow(() -> new BadRequestException("Person not found by id"));

            if (person.getEmail() != null && !person.getEmail().trim().equalsIgnoreCase(personOptional.get().getEmail().trim())) {
                if (existsByEmail(person.getEmail()))
                    throw new BadRequestException("There is already a registered user with the email provided");
            }

            addRole(person);

            person.setId(id);
            mapper.map(person, personOptional.get());

            return physicalPersonRepository.save(personOptional.get());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to update person");
        }
    }

    public PhysicalPerson changeActivity(Long id) {
        Optional<PhysicalPerson> physicalPerson = physicalPersonRepository.findById(id);
        physicalPerson.orElseThrow(() -> new BadRequestException("Person not found by id"));

        physicalPerson.get().setActive(!physicalPerson.get().isActive());

        return physicalPersonRepository.save(physicalPerson.get());
    }

    public PhysicalPerson getAuthenticated() {
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        return physicalPersonRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new BadRequestException("Person not found by email"));
    }
}
