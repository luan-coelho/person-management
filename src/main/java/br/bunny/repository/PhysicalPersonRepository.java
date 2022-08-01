package br.bunny.repository;

import br.bunny.model.person.PhysicalPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhysicalPersonRepository extends JpaRepository<PhysicalPerson, UUID> {

    PhysicalPerson findByEmail(String email);

    boolean existsByEmail(String email);
}
