package br.bunny.domain.repository;

import br.bunny.domain.model.person.Gender;
import br.bunny.domain.model.person.PhysicalPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPerson, UUID>, JpaSpecificationExecutor<PhysicalPerson> {
    Optional<PhysicalPerson> findByEmail(String email);

    Optional<PhysicalPerson> findByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    Page<PhysicalPerson> findAllByGender(Gender gender, Pageable pageable);

}
