package com.lucas.profile_ms.repositories;

import com.lucas.profile_ms.domains.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

    @Query("SELECT p FROM Profile p WHERE p.email = :email")
    Optional<Profile> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Profile p WHERE p.email = :email")
    UserDetails findByEmailDetails(@Param("email") String email);
}
