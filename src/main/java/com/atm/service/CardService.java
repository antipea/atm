package com.atm.service;

import com.atm.model.entity.Card;

import java.math.BigDecimal;

public interface CardService {
    Card getCardById(Long cardId);
    Card getCardByNumber(String cardNumber);
    void updateBalance(Long cardId, BigDecimal newBalance);
    BigDecimal getBalance(Long cardId);
    String getCardCurrency(Long cardId);
}
