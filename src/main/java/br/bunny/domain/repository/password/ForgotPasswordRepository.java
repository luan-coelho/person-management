package br.bunny.domain.repository.password;

import br.bunny.domain.model.password.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {

    boolean existsByCode(String code);

    @Query("SELECT f.active FROM ForgotPassword f WHERE f.code LIKE :code")
    boolean codeAlreadyUsed(String code);

    @Modifying
    @Query("UPDATE ForgotPassword fg SET fg.active = false WHERE fg.person.email LIKE :email")
    void disableAllRequests(String email);

    @Query(value = """
            SELECT count(forgot_password) > 0
            FROM forgot_password
                     JOIN person ON person.email ILIKE :email
            WHERE person.id = forgot_password.person_id
            """, nativeQuery = true)
    boolean codeExistsByPersonEmail(String email);

    Optional<ForgotPassword> findByCode(String code);
}
