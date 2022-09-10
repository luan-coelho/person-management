package br.bunny.service.person;

import br.bunny.domain.model.person.PhysicalPerson;
import br.bunny.domain.model.person.Role;
import br.bunny.domain.repository.person.PhysicalPersonRepository;
import br.bunny.exception.validation.BadRequestException;
import br.bunny.filter.PhysicalPersonFilter;
import br.bunny.service.role.RoleService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PhysicalPersonService {

    final private PhysicalPersonRepository physicalPersonRepository;
    final private RoleService roleService;
    final private ModelMapper mapper;
    final private PasswordEncoder passwordEncoder;

    public Page<PhysicalPerson> findAllPhysicalPerson(PhysicalPersonFilter filter, Pageable pageable) {
        return physicalPersonRepository.findAll(filter.toSpec(), pageable);
    }

    public PhysicalPerson findPhysicalPersonById(Long id) {
        return physicalPersonRepository.findById(id).orElseThrow(() -> new BadRequestException("Person not found by id"));
    }

    public PhysicalPerson findPhysicalPersonByCpf(String cpf) {
        return physicalPersonRepository.findByCpf(cpf).orElseThrow(() -> new BadRequestException("Person not found by CPF"));
    }

    public PhysicalPerson findPhysicalPersonByEmail(String email) {
        return physicalPersonRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new BadRequestException("Person not found by email"));
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

            person.setPassword(passwordEncoder.encode(person.getPassword()));

            return physicalPersonRepository.save(person);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to save person");
        }
    }

    private void addRoleToPerson(PhysicalPerson person) {
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

    public PhysicalPerson updatePhysicalPerson(Long id, PhysicalPerson person) {
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

    public PhysicalPerson activateOrDesactivatePhysicalPerson(Long id) {
        Optional<PhysicalPerson> physicalPerson = physicalPersonRepository.findById(id);
        physicalPerson.orElseThrow(() -> new BadRequestException("Person not found by id"));

        physicalPerson.get().setActive(!physicalPerson.get().isActive());
        return physicalPersonRepository.save(physicalPerson.get());
    }

    public void generateRefleshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refleshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secrect".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refleshToken);
                String personEmail = decodedJWT.getSubject();
                PhysicalPerson user = findPhysicalPersonByEmail(personEmail);
                String acessToken = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles", user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", acessToken);
                tokens.put("reflesh_token", refleshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                //response.sendError(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Reflesh token is missing");
        }
    }
}
