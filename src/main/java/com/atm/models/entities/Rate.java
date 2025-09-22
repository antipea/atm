package com.atm.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long rateId;

    @Column(name="r_base_currency", nullable = false)
    private String baseCurrency;

    @Column(name="r_target_currency", nullable = false)
    private String targetCurrency;

    @Column(name="r_rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
