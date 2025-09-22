package com.atm.controllers;

import com.atm.dto.BalanceDTO;
import com.atm.services.BalanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/balance")
@CrossOrigin(origins = "*")
@Tag(name = "Баланс", description = "Операции с балансом карт")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/{cardId}")
    @Operation(summary = "Получение баланса", description = "Получение текущего баланса карты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Баланс успешно получен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BalanceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    public ResponseEntity<BalanceDTO> getBalance(@Parameter(description = "ID карты", example = "1")
                                                 @PathVariable Long cardId) {
        try {
            BalanceDTO balanceDTO = balanceService.getBalanceInThreeCurrencies(cardId);
            return ResponseEntity.ok(balanceDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
