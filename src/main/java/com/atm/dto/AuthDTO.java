package com.atm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Данные для аутентификации")
public class AuthDTO {
    @Schema(description = "Номер карты", example = "1234567890123456")
    private String cardNumber;

    @Schema(description = "PIN-код", example = "1234")
    private Short pinCode;
}
