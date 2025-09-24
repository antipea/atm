package com.atm.service;

import com.atm.dto.PaymentDTO;
import com.atm.dto.responce.TransactionResponseDTO;
import com.atm.model.entity.Transaction;

public interface PaymentService {
    public Transaction makePayment(Long cardId, PaymentDTO paymentDTO);
    public TransactionResponseDTO convertToDTO(Transaction transaction);
}
