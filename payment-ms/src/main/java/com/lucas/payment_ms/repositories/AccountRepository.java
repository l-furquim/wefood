package com.lucas.payment_ms.repositories;

import com.lucas.payment_ms.domains.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.key = :key OR a.user_id = :userId")
    Optional<Account> findByKeyOrUserId(@Param("key") String key, @Param("userId") String userId);

}
