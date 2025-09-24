package com.atm.controller;

import com.atm.dto.AuthDTO;
import com.atm.dto.responce.AuthResponseDTO;
import com.atm.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Аутентификация", description = "Операции аутентификации пользователей")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Аутентификация пользователя",
            description = "Аутентификация пользователя по номеру карты и PIN-коду")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Аутентификация успешна",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Неверный номер карты или PIN-код"),
            @ApiResponse(responseCode = "400", description = "Ошибка в формате данных")
    })
    public ResponseEntity<AuthResponseDTO> login(
            @Parameter(
                    description = "Данные для аутентификации",
                    required = true
            )
            @RequestBody AuthDTO authDTO) {
        AuthResponseDTO response = authService.authenticateAndGetResponse(authDTO);

        return authService.getResponse(response);
    }
}
