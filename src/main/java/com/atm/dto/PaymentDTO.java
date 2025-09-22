package com.atm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Осуществить оплату")
public class PaymentDTO {

    @Schema(description = "Код услуги", example = "1001")
    private Integer paymentCode;

    @Schema(description = "Сумма", example = "75.50")
    private BigDecimal amount;

    @Schema(description = "Валюта", example = "BYN")
    private String currency;

}
