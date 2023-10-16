package com.vault.velocity.limits.repository;

import com.vault.velocity.limits.entity.LoadFundsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoadFundsRepository extends JpaRepository<LoadFundsEntity, Long> {
    //this is slow
    //@Query("SELECT history FROM LoadFundsEntity history WHERE history.customerId = :customerId AND history.time >= :startOfWeek AND history.time < :endOfWeek")
    Optional<List<LoadFundsEntity>> findByCustomerIdAndTimeBetweenOrderByTimeDesc(Long customerId, LocalDateTime startOfWeek, LocalDateTime endOfWeek);

    @Query("SELECT sum(history.load_amount) FROM LoadFundsEntity history WHERE history.customerId = :customerId AND history.time >= :startOfWeek AND history.time < :endOfWeek")
    BigDecimal sumDepositsWeekly(Long customerId, LocalDateTime startOfWeek, LocalDateTime endOfWeek);

    //This doesnt give any performance boost about similar like the below one
    //@Query("SELECT sum(history.load_amount) as load_amount, count (*) as count FROM LoadFundsEntity history WHERE history.customerId = :customerId AND history.time >= :startOfDay AND history.time < :endOfDay")
    @Query("SELECT history FROM LoadFundsEntity history WHERE history.customerId = :customerId AND history.time >= :startOfDay AND history.time < :endOfDay")
    List<LoadFundsEntity> findDepositsWithinDay(Long customerId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
