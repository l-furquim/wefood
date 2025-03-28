package com.lucas.pedido_ms.repositories;

import com.lucas.pedido_ms.domains.orderitem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o FROM OrderItem o WHERE o.restaurantId = :id")
    List<OrderItem> findByRestaurantId(@Param("id") Long id);

}
