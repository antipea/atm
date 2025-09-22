package com.atm.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Информация о транзакции")
public class TransactionResponseDTO {
    @Schema(description = "ID транзакции", example = "1")
    private Long id;

    @Schema(description = "Тип транзакции", example = "DEPOSIT")
    private String type;

    @Schema(description = "Сумма", example = "100.00")
    private BigDecimal amount;

    @Schema(description = "Валюта", example = "BYN")
    private String currency;

    @Schema(description = "Базовая сумма", example = "100.00")
    private BigDecimal baseAmount;

    @Schema(description = "Базовая валюта", example = "BYN")
    private String baseCurrency;

    @Schema(description = "Описание", example = "Пополнение счета")
    private String description;

    @Schema(description = "Статус", example = "COMPLETED")
    private String status;

    @Schema(description = "Дата создания", example = "2023-12-01T10:30:00")
    private LocalDateTime createdAt;
}
