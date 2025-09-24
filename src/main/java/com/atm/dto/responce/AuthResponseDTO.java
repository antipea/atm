package com.atm.dto.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ на аутентификацию")
public class AuthResponseDTO {
    @Schema(description = "ID карты пользователя", example = "1")
    private Long cardId;

    @Schema(description = "Сообщение о результате аутентификации", example = "Authentication successful")
    private String message;
}
