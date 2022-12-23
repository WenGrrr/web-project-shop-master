package com.underfit.trpo.dao;

import com.underfit.trpo.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("FROM Orders e LEFT JOIN FETCH e.productidfk LEFT JOIN FETCH e.selleridfk")
    Optional<List<Orders>> findAllOrders();
}