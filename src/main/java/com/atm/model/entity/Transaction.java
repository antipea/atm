package com.atm.model.entity;

import com.atm.model.enums.TransactionStatus;
import com.atm.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "t_id")
    private Long id;

    @Column(name = "t_card_id")
    private Long cardId;

    @Column(name = "t_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "t_amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "t_currency", nullable = false)
    private String currency;

    @Column(name = "t_base_amount", nullable = false)
    private BigDecimal baseAmount;

    @Column(name = "t_base_currency", nullable = false)
    private String baseCurrency;

    @Column(name = "t_rate")
    private BigDecimal rate;

    @Column(name = "t_description")
    private String description;

    @Column(name = "t_target_card_number")
    private String targetCardNumber;

    @Column(name = "t_payment_code")
    private String paymentCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.COMPLETED;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
