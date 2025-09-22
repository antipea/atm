package com.atm.controllers;

import com.atm.dto.AuthDTO;
import com.atm.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Аутентификация", description = "Операции аутентификации пользователей")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя",
            description = "Аутентификация пользователя по номеру карты и PIN-коду")
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) {
        try {
            boolean authenticated = authService.authenticate(authDTO);
            if (authenticated) {
                Long cardId = authService.getCardIdByCardNumber(authDTO.getCardNumber());
                return ResponseEntity.ok(new LoginResponse(cardId, "Аутентификация пользователя пройдена успешно"));
            }
            return ResponseEntity.status(401).body("Аутентификация пользователя не прошла");
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    private static class LoginResponse {
        private Long cardId;
        private String message;

        public LoginResponse(Long cardId, String message) {
            this.cardId = cardId;
            this.message = message;
        }

        public Long getCardId() { return cardId; }
        public void setCardId(Long cardId) { this.cardId = cardId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
