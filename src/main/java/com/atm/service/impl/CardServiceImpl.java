package com.atm.service.impl;

import com.atm.exception.CardNotFoundException;
import com.atm.model.entity.Card;
import com.atm.repository.CardRepository;
import com.atm.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Карта не существует"));
    }

    public Card getCardByNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException("Карта " + cardNumber + " не существует"));
    }

    public void updateBalance(Long cardId, BigDecimal newBalance) {
        Card card = getCardById(cardId);
        card.setCardBalance(newBalance);
        cardRepository.save(card);
    }

    public BigDecimal getBalance(Long cardId) {
        Card card = getCardById(cardId);
        return card.getCardBalance();
    }

    public String getCardCurrency(Long cardId) {
        Card card = getCardById(cardId);
        return card.getCardCurrency();
    }

}
