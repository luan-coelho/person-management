package br.bunny.domain.repository.person;

import br.bunny.domain.model.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByEmailEqualsIgnoreCaseAndPassword(String email, String password);
}
