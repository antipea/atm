package com.atm.service.impl;

import com.atm.dto.PaymentDTO;
import com.atm.dto.responce.TransactionResponseDTO;
import com.atm.exception.PaymentNotFoundException;
import com.atm.model.entity.Card;
import com.atm.model.entity.Payment;
import com.atm.model.entity.Transaction;
import com.atm.model.enums.TransactionType;
import com.atm.repository.PaymentRepository;
import com.atm.repository.TransactionRepository;
import com.atm.service.PaymentService;
import com.atm.service.RateService;
import com.atm.service.TransactionService;
import com.atm.util.TransactionBuilder;
import com.atm.util.TransactionConverter;
import com.atm.util.Validation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardServiceImpl cardService;

    @Autowired
    private RateService rateService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction makePayment(Long cardId, PaymentDTO paymentDTO) {

        // Check payment for isset
        Payment payment = paymentRepository.findByPaymentCode(paymentDTO.getPaymentCode())
                .orElseThrow(() -> new PaymentNotFoundException("Данной услуги не существует"));

        if (!payment.getActive()) {
            throw new PaymentNotFoundException("Данная услуга больше не поддерживается");
        }

        Card card = cardService.getCardById(cardId);

        // Convert sum to rate
        BigDecimal convertedAmount = rateService.convertCurrency(
                paymentDTO.getAmount(),
                paymentDTO.getCurrency().toUpperCase(),
                card.getCardCurrency()
        );

        Validation.validateAmount(BigDecimal.valueOf(card.getCardBalance().compareTo(convertedAmount)));

        BigDecimal newBalance = card.getCardBalance().subtract(convertedAmount);
        cardService.updateBalance(cardId, newBalance);

        // Create transaction
        Transaction transaction = TransactionBuilder.builder()
                .cardId(cardId)
                .type(TransactionType.PAYMENT)
                .amount(paymentDTO.getAmount())
                .currency(paymentDTO.getCurrency().toUpperCase())
                .baseAmount(convertedAmount)
                .baseCurrency(card.getCardCurrency())
                .rate(rateService.getRate(paymentDTO.getCurrency().toUpperCase(), card.getCardCurrency()))
                .description("Платеж за " +  payment.getPaymentName())
                .build();

        return transactionRepository.save(transaction);
    }

    public TransactionResponseDTO convertToDTO(Transaction transaction) {
        return TransactionConverter.convertToDTO(transaction);
    }
}
