package br.bunny.repository;

import br.bunny.model.person.Gender;
import br.bunny.model.person.PhysicalPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPerson, UUID>, JpaSpecificationExecutor<PhysicalPerson> {
    PhysicalPerson findByEmail(String email);

    Optional<PhysicalPerson> findByCpf(String cpf);

    boolean existsByEmail(String email);

    Page<PhysicalPerson> findAllByGender(Gender gender, Pageable pageable);

}
