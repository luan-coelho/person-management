package br.bunny.domain.repository.person;

import br.bunny.domain.model.person.PhysicalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPerson, Long>, JpaSpecificationExecutor<PhysicalPerson> {
    Optional<PhysicalPerson> findByEmailIgnoreCase(String email);

    Optional<PhysicalPerson> findByCpf(String cpf);
    Optional<PhysicalPerson> findByEmailEqualsIgnoreCaseAndCpf(String email, String cpf);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByEmailIgnoreCaseAndPassword(String email, String password);
    boolean existsByEmailIgnoreCase(String email);
}
