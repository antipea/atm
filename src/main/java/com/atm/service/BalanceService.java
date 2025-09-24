package com.atm.service;

import com.atm.dto.BalanceDTO;

public interface BalanceService {
    BalanceDTO getBalanceInThreeCurrencies(Long cardId);
}
