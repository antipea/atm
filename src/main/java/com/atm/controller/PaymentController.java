package com.atm.controller;

import com.atm.dto.PaymentDTO;
import com.atm.dto.responce.TransactionResponseDTO;
import com.atm.service.PaymentService;
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
@RequestMapping("/api/payments")
@Tag(name = "Платежи", description = "Операции с платежами")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{cardId}/pay")
    @Operation(
            summary = "Оплата услуги",
            description = "Оплата услуги по указанному коду услуги и сумме в выбранной валюте"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Оплата успешно выполнена",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TransactionResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Недостаточно средств или ошибка в данных"),
            @ApiResponse(responseCode = "404", description = "Карта или услуга не найдена")
    })
    public ResponseEntity<TransactionResponseDTO> makePayment(@Parameter(description = "ID карты", example = "1")
                                                              @PathVariable Long cardId,
                                                              @Parameter(description = "Данные для оплаты")
                                                              @RequestBody PaymentDTO paymentDTO) {
        TransactionResponseDTO response = paymentService.convertToDTO(paymentService.makePayment(cardId, paymentDTO));
        return ResponseEntity.ok(response);
    }

}

