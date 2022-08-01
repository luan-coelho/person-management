package br.bunny.service.person;

import br.bunny.exception.NotFoundException;
import br.bunny.model.person.PhysicalPerson;
import br.bunny.repository.PhysicalPersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PhysicalPersonService {

    final private PhysicalPersonRepository physicalPersonRepository;
    final private ModelMapper mapper;

    public List<PhysicalPerson> findAllPhysicalPerson() {
        return physicalPersonRepository.findAll();
    }

    public PhysicalPerson findPhysicalPersonById(UUID id) {
        return physicalPersonRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    public PhysicalPerson findPhysicalPersonByEmail(String email) {
        return physicalPersonRepository.findByEmail(email);
    }

    public boolean existsPhysicalPersonByEmail(String email) {
        return physicalPersonRepository.existsByEmail(email);
    }

    public boolean existsPhysicalPersonById(UUID id) {
        return physicalPersonRepository.existsById(id);
    }

    @Transactional
    public PhysicalPerson savePhysicalPerson(PhysicalPerson person) {
        try {
            return physicalPersonRepository.save(person);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar usuário.");
        }
    }

    @Transactional
    public PhysicalPerson updatePhysicalPerson(PhysicalPerson physicalPerson) {
        Optional<PhysicalPerson> physicalPersonOriginal = physicalPersonRepository.findById(physicalPerson.getId());
        physicalPersonOriginal.orElseThrow(() -> new NotFoundException("Usuário não encontrado."));

        mapper.map(physicalPersonOriginal, physicalPersonOriginal.get());

        try {
            return physicalPersonRepository.save(physicalPersonOriginal.get());
        } catch (Exception e) {
            throw new RuntimeException("Falha ao atualizar usuário.");
        }
    }

    @Transactional
    public PhysicalPerson activateOrDesactivatePhysicalPerson(UUID id, boolean ativo) {
        Optional<PhysicalPerson> physicalPerson = physicalPersonRepository.findById(id);

        if (physicalPerson.isEmpty()) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        physicalPerson.get().setActive(ativo);
        return physicalPersonRepository.save(physicalPerson.get());
    }
}
