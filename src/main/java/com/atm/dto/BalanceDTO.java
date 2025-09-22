package com.atm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Информация о балансе в трех валютах")
public class BalanceDTO {

    @Schema(description = "Баланс в BYN", example = "1000.50")
    private BigDecimal balanceByn;

    @Schema(description = "Баланс в USD", example = "310.00")
    private BigDecimal balanceUsd;

    @Schema(description = "Баланс в EUR", example = "270.00")
    private BigDecimal balanceEur;

    @Schema(description = "Валюта карты", example = "BYN")
    private String cardCurrency;
}
