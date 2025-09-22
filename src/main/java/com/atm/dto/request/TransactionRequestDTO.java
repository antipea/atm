package com.atm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Осуществить транзакцию")
public class TransactionRequestDTO {

    @Schema(description = "Сумма", example = "100.00")
    private BigDecimal amount;

    @Schema(description = "Валюта", example = "BYN")
    private String currency;

    @Schema(description = "Описание", example = "Пополнение счета")
    private String description;

}