package com.atm.service.impl;

import com.atm.dto.AuthDTO;
import com.atm.dto.responce.AuthResponseDTO;
import com.atm.exception.CardNotFoundException;
import com.atm.exception.InvalidPinException;
import com.atm.model.entity.Card;
import com.atm.repository.CardRepository;
import com.atm.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private CardRepository cardRepository;

    public AuthResponseDTO authenticateAndGetResponse(AuthDTO authDTO) {
        boolean authenticated = authenticate(authDTO);
        if (authenticated) {
            Long cardId = getCardIdByCardNumber(authDTO.getCardNumber());
            return new AuthResponseDTO(cardId, "Аутентификация пользователя пройдена успешно");
        }
        return new AuthResponseDTO(null, "Аутентификация пользователя не прошла");
    }

    public boolean authenticate(AuthDTO authDTO) {
        Card card = cardRepository.findByCardNumber(authDTO.getCardNumber())
                .orElseThrow(() -> new CardNotFoundException("Карта не существует"));

        if (!card.getActive()) {
            throw new CardNotFoundException("Карта не существует");
        }

        if (card.getCardPinCode() != authDTO.getPinCode()) {
            throw new InvalidPinException("Некорректный пинкод");
        }

        return true;
    }

    public Long getCardIdByCardNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException("Карта не существует"));
        return card.getCardId();
    }

    public boolean isAuthenticationSuccessful(AuthResponseDTO response) {
        return response.getCardId() != null;
    }

    public ResponseEntity<AuthResponseDTO> getResponse(AuthResponseDTO response) {
        if (isAuthenticationSuccessful(response)) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }
}
