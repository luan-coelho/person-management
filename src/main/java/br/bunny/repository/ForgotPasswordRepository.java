package br.bunny.repository;

import br.bunny.model.password.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, UUID> {

    boolean existsByCode(String code);

    @Query("SELECT f.active FROM ForgotPassword f WHERE f.code  LIKE :code")
    boolean codeAlreadyUsed(String code);
}
