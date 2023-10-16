package com.vault.velocity.limits.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deposit_history")
public class LoadFundsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rowId;

    private Long id;

    @Column(nullable = false, updatable = false)
    private Long customerId;

    @Column(nullable = false, updatable = false)
    private BigDecimal load_amount;

    private LocalDateTime time;

    private boolean isAccepted;

    private String currencyFormat;

}
