package org.jala.university.domain.repository.repository_account;

import org.jala.university.domain.entity.entity_account.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface AuthenticationRepository<A, L extends Integer> extends JpaRepository<Authentication, Integer> {

    @Query("SELECT a FROM Authentication a JOIN a.customer c WHERE c.email = :email AND c.password = :password")
    Optional<Authentication> authenticate(@Param("email") String email, @Param("password") String password);

    @Query("SELECT a FROM Authentication a JOIN a.customer c WHERE c.email = :email")
    Optional<Authentication> findByEmail(@Param("email") String email);

    @Query("SELECT a FROM Authentication a JOIN a.customer c WHERE c.cpf = :cpf")
    Optional<Authentication> findByCpf(@Param("cpf") String cpf);


}
