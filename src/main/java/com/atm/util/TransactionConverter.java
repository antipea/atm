package com.atm.util;

import com.atm.dto.responce.TransactionResponseDTO;
import com.atm.model.entity.Transaction;

public class TransactionConverter {
    public static TransactionResponseDTO convertToDTO(Transaction transaction) {
        TransactionResponseDTO transactionDTO = new TransactionResponseDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setType(transaction.getType().name());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setBaseAmount(transaction.getBaseAmount());
        transactionDTO.setBaseCurrency(transaction.getBaseCurrency());
        transactionDTO.setDescription(transaction.getDescription());
        transactionDTO.setStatus(transaction.getStatus().name());
        transactionDTO.setCreatedAt(transaction.getCreatedAt());

        return transactionDTO;
    }
}
