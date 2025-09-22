package com.atm.repository;

import com.atm.models.entities.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByBaseCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);

}
