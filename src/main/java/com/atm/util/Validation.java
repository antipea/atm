package com.atm.util;

import com.atm.exception.CardNotFoundException;
import com.atm.exception.InsufficientFundsException;

import java.math.BigDecimal;

public class Validation {

    public static void validateCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            throw new CardNotFoundException("Карты с номером " + cardNumber + " не существует ");
        }
    }

    public static void validateAmount(BigDecimal amount) {
        if (amount == null ||  amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }
    }

}
