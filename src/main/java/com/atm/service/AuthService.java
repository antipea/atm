package com.atm.service;

import com.atm.dto.AuthDTO;
import com.atm.dto.responce.AuthResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    boolean authenticate(AuthDTO authDTO);
    Long getCardIdByCardNumber(String cardNumber);
    AuthResponseDTO authenticateAndGetResponse(AuthDTO authDTO);
    boolean isAuthenticationSuccessful(AuthResponseDTO response);
    ResponseEntity<AuthResponseDTO> getResponse(AuthResponseDTO response);
}
