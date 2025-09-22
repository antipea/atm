package com.atm.services;

import com.atm.dto.PaymentDTO;
import com.atm.exception.InsufficientFundsException;
import com.atm.models.entities.Card;
import com.atm.models.entities.Payment;
import com.atm.models.entities.Transaction;
import com.atm.models.enums.TransactionStatus;
import com.atm.models.enums.TransactionType;
import com.atm.repository.PaymentRepository;
import com.atm.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardService cardService;

    @Autowired
    private RateService rateService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction makePayment(Long cardId, PaymentDTO paymentDTO) {

        // Check payment for isset
        Payment payment = paymentRepository.findByPaymentCode(paymentDTO.getPaymentCode())
                .orElseThrow(() -> new RuntimeException("Данной услуги не существует"));

        if (!payment.getActive()) {
            throw new RuntimeException("Данная услуга больше не поддерживается");
        }

        Card card = cardService.getCardById(cardId);

        // Convert sum to rate
        BigDecimal convertedAmount = rateService.convertCurrency(
                paymentDTO.getAmount(),
                paymentDTO.getCurrency().toUpperCase(),
                card.getCardCurrency()
        );

        if (card.getCardBalance().compareTo(convertedAmount) < 0) {
            throw new InsufficientFundsException("Средств недостаточно для вполнения операции");
        }

        BigDecimal newBalance = card.getCardBalance().subtract(convertedAmount);
        cardService.updateBalance(cardId, newBalance);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setType(TransactionType.PAYMENT);
        transaction.setAmount(paymentDTO.getAmount());
        transaction.setCurrency(paymentDTO.getCurrency().toUpperCase());
        transaction.setBaseAmount(convertedAmount);
        transaction.setBaseCurrency(card.getCardCurrency());
        transaction.setRate(rateService.getRate(
                paymentDTO.getCurrency().toUpperCase(),
                card.getCardCurrency()
        ));
        transaction.setDescription("Платеж за " + payment.getPaymentName());
        transaction.setPaymentCode(String.valueOf(paymentDTO.getPaymentCode()));
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
