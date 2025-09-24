package com.atm.service;

import com.atm.dto.TransferDTO;
import com.atm.dto.request.TransactionRequestDTO;
import com.atm.dto.responce.TransactionResponseDTO;
import com.atm.model.entity.Transaction;

public interface TransactionService {
    Transaction deposit(Long cardId, TransactionRequestDTO requestDto);
    Transaction withdraw(Long cardId, TransactionRequestDTO requestDto);
    Transaction transfer(Long cardId, TransferDTO transferDto);
    TransactionResponseDTO convertToDTO(Transaction transaction);
}
