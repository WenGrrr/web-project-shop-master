package com.underfit.trpo.dao;

import com.underfit.trpo.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    @Query("FROM Rate m JOIN FETCH m.buyeridfk JOIN FETCH m.ordersidfk")
    Optional<List<Rate>> findAllRates();
    @Query("FROM Rate m JOIN FETCH m.buyeridfk JOIN FETCH m.ordersidfk WHERE m.ordersidfk.id = :ordersid ORDER BY m.buyeridfk.fio asc, m.buydate desc")
    Optional<List<Rate>> findAllRatesByIdOrders(@Param("ordersid") Long ordersid);
}