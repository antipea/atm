package com.atm.controllers;

import com.atm.dto.TransferDTO;
import com.atm.dto.request.TransactionRequestDTO;
import com.atm.dto.responce.TransactionResponseDTO;
import com.atm.models.entities.Transaction;
import com.atm.services.TransactionService;
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
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
@Tag(name = "Транзакции", description = "Операции с транзакциями")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{cardId}/deposit")
    @Operation(summary = "Пополнение счета", description = "Пополнение баланса карты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пополнение успешно выполнено",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка в данных запроса"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    public ResponseEntity<TransactionResponseDTO> deposit(@Parameter(description = "ID карты", example = "1")
                                                          @PathVariable Long cardId,
                                                          @Parameter(description = "Данные для пополнения денег")
                                                          @RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            Transaction transaction = transactionService.deposit(cardId, transactionRequestDTO);

            return ResponseEntity.ok(convertToDTO(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{cardId}/withdraw")
    @Operation(summary = "Снятие средств",
            description = "Снятие средств с карты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Снятие успешно выполнено",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Недостаточно средств или ошибка в данных"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    public ResponseEntity<TransactionResponseDTO> withdraw(@Parameter(description = "ID карты", example = "1")
                                                           @PathVariable Long cardId,
                                                           @Parameter(description = "Данные для снятия денег")
                                                           @RequestBody TransactionRequestDTO requestDTO) {
        try {
            Transaction transaction = transactionService.withdraw(cardId, requestDTO);
            return ResponseEntity.ok(convertToDTO(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{cardId}/transfer")
    @Operation(summary = "Перевод средств",
            description = "Перевод средств на другую карту")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Перевод успешно выполнен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Недостаточно средств или ошибка в данных"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    public ResponseEntity<TransactionResponseDTO> transfer(@Parameter(description = "ID карты", example = "1")
                                                           @PathVariable Long cardId,
                                                           @Parameter(description = "Данные для перевода")
                                                           @RequestBody TransferDTO transferDTO) {
        try {
            Transaction transaction = transactionService.transfer(cardId, transferDTO);
            return ResponseEntity.ok(convertToDTO(transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private TransactionResponseDTO convertToDTO(Transaction transaction) {
        TransactionResponseDTO transactionDTO = new TransactionResponseDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setType(transaction.getType().name());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setBaseAmount(transaction.getBaseAmount());
        transactionDTO.setBaseCurrency(transaction.getBaseCurrency());
        transactionDTO.setDescription(transaction.getDescription());
        transactionDTO.setStatus(transaction.getStatus().name());
        transactionDTO.setCreatedAt(transaction.getCreatedAt());

        return transactionDTO;
    }
}