package com.atm.services;

import com.atm.models.entities.Rate;
import com.atm.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RateService {

    @Autowired
    private RateRepository rateRepository;

    public BigDecimal getRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return BigDecimal.ONE;
        }

        return rateRepository.findByBaseCurrencyAndTargetCurrency(fromCurrency, toCurrency)
                .map(Rate::getRate)
                .orElseThrow(() -> new RuntimeException("Курс обмена " + fromCurrency + " в " + toCurrency + " не найден"));
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (amount == null) {
            return BigDecimal.ZERO;
        }

        if (fromCurrency.equals(toCurrency)) {

            return amount;
        }

        try {
            BigDecimal rate = getRate(fromCurrency, toCurrency);
            BigDecimal result = amount.multiply(rate);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка конвертирования " + amount + " " + fromCurrency + " в " + toCurrency + ": " + e.getMessage(), e);
        }
    }

}
