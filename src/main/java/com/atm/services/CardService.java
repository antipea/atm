package com.atm.services;

import com.atm.models.entities.Card;
import com.atm.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Карта не существует"));
    }

    public Card getCardByNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Карта " + cardNumber + " не существует"));
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
