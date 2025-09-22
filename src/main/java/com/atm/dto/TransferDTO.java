package com.atm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Осуществить перевод")
public class TransferDTO {

    @Schema(description = "Номер целевой карты", example = "9876543210987654")
    private String targetCardNumber;

    @Schema(description = "Сумма", example = "50.00")
    private BigDecimal amount;

    @Schema(description = "Валюта", example = "BYN")
    private String currency;

    @Schema(description = "Описание", example = "Перевод другу")
    private String description;

}