package br.bunny.service.person;

import br.bunny.exception.validation.BadRequestException;
import br.bunny.filter.PhysicalPersonFilter;
import br.bunny.model.person.PhysicalPerson;
import br.bunny.repository.PhysicalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PhysicalPersonService {

    final private PhysicalPersonRepository physicalPersonRepository;
    final private ModelMapper mapper;

    public Page<PhysicalPerson> findAllPhysicalPerson(PhysicalPersonFilter filter, Pageable pageable) {
        return physicalPersonRepository.findAll(filter.toSpec(), pageable);
    }

    public PhysicalPerson findPhysicalPersonById(UUID id) {
        return physicalPersonRepository.findById(id).orElseThrow(() -> new BadRequestException("Usuário não encontrado pelo id."));
    }

    public PhysicalPerson findPhysicalPersonByCpf(String cpf) {
        return physicalPersonRepository.findByCpf(cpf).orElseThrow(() -> new BadRequestException("Usuário não encontrado pelo CPF."));
    }

    public PhysicalPerson findPhysicalPersonByEmail(String email) {
        return physicalPersonRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("Usuário não encontrado pelo email."));
    }

    public boolean existsPhysicalPersonByEmail(String email) {
        return physicalPersonRepository.existsByEmail(email);
    }

    public boolean existsPhysicalPersonByCpf(String cpf) {
        return physicalPersonRepository.existsByCpf(cpf);
    }

    @Transactional
    public PhysicalPerson savePhysicalPerson(PhysicalPerson person) {
        try {
            if (existsPhysicalPersonByCpf(person.getCpf()))
                throw new BadRequestException("Já existe um usuário cadastrado com o CPF informado.");
            if (existsPhysicalPersonByEmail(person.getEmail()))
                throw new BadRequestException("Já existe um usuário cadastrado com o email informado.");
            return physicalPersonRepository.save(person);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Falha ao salvar usuário.");
        }
    }

    @Transactional
    public PhysicalPerson updatePhysicalPerson(@NotNull PhysicalPerson person) {
        try {
            Optional<PhysicalPerson> personOptional = physicalPersonRepository.findById(person.getId());
            personOptional.orElseThrow(() -> new BadRequestException("Usuário não encontrado pelo id."));

            if (person.getEmail() != null && !person.getEmail().trim().equalsIgnoreCase(personOptional.get().getEmail().trim())) {
                if (existsPhysicalPersonByEmail(person.getEmail()))
                    throw new BadRequestException("Já existe um usuário cadastrado com o email informado.");
            }

            mapper.map(person, personOptional.get());

            return physicalPersonRepository.save(personOptional.get());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Falha ao atualizar usuário.");
        }
    }

    @Transactional
    public PhysicalPerson activateOrDesactivatePhysicalPerson(UUID id) {
        Optional<PhysicalPerson> physicalPerson = physicalPersonRepository.findById(id);
        physicalPerson.orElseThrow(() -> new BadRequestException("Usuário não encontrado pelo id."));

        physicalPerson.get().setActive(!physicalPerson.get().isActive());
        return physicalPersonRepository.save(physicalPerson.get());
    }
}
