package com.lucas.profile_ms.repositories;

import com.lucas.profile_ms.domains.profile.Profile;
import com.lucas.profile_ms.domains.restaurant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    @Query("SELECT r FROM Restaurant r WHERE r.domainEmail = :email")
    Optional<List<Restaurant>> findByEmail(@Param("email") String email);

    @Query("SELECT r FROM Restaurant r WHERE r.domainEmail = :email")
    UserDetails findByEmailDetails(@Param("email") String email);

    @Query("SELECT r from Restaurant r WHERE r.cnpj = :cnpj")
    Optional<Restaurant> findByCnpj(@Param("cnpj") String cnpj);

}
