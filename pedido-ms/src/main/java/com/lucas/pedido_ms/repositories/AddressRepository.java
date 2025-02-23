package com.lucas.pedido_ms.repositories;

import com.lucas.pedido_ms.domains.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
