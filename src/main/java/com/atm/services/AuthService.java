package com.atm.services;

import com.atm.dto.AuthDTO;
import com.atm.exception.InvalidPinException;
import com.atm.models.entities.Card;
import com.atm.exception.CardNotFoundException;
import com.atm.repository.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private CardRepository cardRepository;

    public boolean authenticate(AuthDTO authDTO) {
        Card card = cardRepository.findByCardNumber(authDTO.getCardNumber())
                .orElseThrow(() -> new CardNotFoundException("Карта не существует"));

        if (!card.getActive()) {
            throw new CardNotFoundException("Карта заблокирована");
        }

        if (!card.getCardPinCode().equals(authDTO.getPinCode())) {
            throw new InvalidPinException("Некорректный пинкод");
        }

        return true;
    }

    public Long getCardIdByCardNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException("Карта не действует"));
        return card.getCardId();
    }
}
