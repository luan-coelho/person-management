package br.bunny.domain.repository.role;

import br.bunny.domain.model.person.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByNameEqualsIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
