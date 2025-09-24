package com.atm.service.impl;

import com.atm.dto.TransferDTO;
import com.atm.dto.request.TransactionRequestDTO;
import com.atm.dto.responce.TransactionResponseDTO;
import com.atm.exception.TransactionErrorException;
import com.atm.model.entity.Card;
import com.atm.model.entity.Transaction;
import com.atm.model.enums.TransactionType;
import com.atm.repository.TransactionRepository;
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
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardServiceImpl cardService;

    @Autowired
    private RateService rateService;

    public Transaction deposit(Long cardId, TransactionRequestDTO transactionRequestDTO) {
        try {
            Card card = cardService.getCardById(cardId);

            // Convert sum to rate
            BigDecimal convertedAmount = rateService.convertCurrency(
                    transactionRequestDTO.getAmount(),
                    transactionRequestDTO.getCurrency().toUpperCase(),
                    card.getCardCurrency()
            );

            // Updated balanced
            BigDecimal newBalance = card.getCardBalance().add(convertedAmount);
            cardService.updateBalance(cardId, newBalance);

            // Create transaction
            Transaction transaction = TransactionBuilder.builder()
                    .cardId(cardId)
                    .type(TransactionType.DEPOSIT)
                    .amount(transactionRequestDTO.getAmount())
                    .currency(transactionRequestDTO.getCurrency().toUpperCase())
                    .baseAmount(convertedAmount)
                    .baseCurrency(card.getCardCurrency())
                    .rate(rateService.getRate(transactionRequestDTO.getCurrency().toUpperCase(), card.getCardCurrency()))
                    .description(transactionRequestDTO.getDescription())
                    .build();

            return transactionRepository.save(transaction);

        } catch (Exception e) {

            throw new TransactionErrorException("Ошибка перевода: " + e.getMessage());
        }
    }

    public Transaction withdraw(Long cardId, TransactionRequestDTO transactionRequestDTO) {
        try {
            Card card = cardService.getCardById(cardId);

            // Convert sum to rate
            BigDecimal convertedAmount = rateService.convertCurrency(
                    transactionRequestDTO.getAmount(),
                    transactionRequestDTO.getCurrency().toUpperCase(),
                    card.getCardCurrency()
            );

            Validation.validateAmount(BigDecimal.valueOf(card.getCardBalance().compareTo(convertedAmount)));

            // Updated balanced
            BigDecimal newBalance = card.getCardBalance().subtract(convertedAmount);
            cardService.updateBalance(cardId, newBalance);

            // Create transaction
            Transaction transaction = TransactionBuilder.builder()
                    .cardId(cardId)
                    .type(TransactionType.WITHDRAWAL)
                    .amount(transactionRequestDTO.getAmount())
                    .currency(transactionRequestDTO.getCurrency().toUpperCase())
                    .baseAmount(convertedAmount)
                    .baseCurrency(card.getCardCurrency())
                    .rate(rateService.getRate(transactionRequestDTO.getCurrency().toUpperCase(), card.getCardCurrency()))
                    .description(transactionRequestDTO.getDescription())
                    .build();

            return transactionRepository.save(transaction);

        } catch (Exception e) {

            throw new TransactionErrorException("Ошибка выдачи наличных: " + e.getMessage());
        }
    }

    public Transaction transfer(Long cardId, TransferDTO transferDTO) {
        try {
            Card sourceCard = cardService.getCardById(cardId);
            Card targetCard = cardService.getCardByNumber(transferDTO.getTargetCardNumber());

            Validation.validateCardNumber(targetCard.getCardNumber());

            // Convert sum to rate
            BigDecimal convertedAmount = rateService.convertCurrency(
                    transferDTO.getAmount(),
                    transferDTO.getCurrency().toUpperCase(),
                    sourceCard.getCardCurrency()
            );

            Validation.validateAmount(BigDecimal.valueOf(sourceCard.getCardBalance().compareTo(convertedAmount)));

            // Convert sum to rate
            BigDecimal targetConvertedAmount = rateService.convertCurrency(
                    transferDTO.getAmount(),
                    transferDTO.getCurrency().toUpperCase(),
                    targetCard.getCardCurrency()
            );

            // Updated balanced
            cardService.updateBalance(cardId, sourceCard.getCardBalance().subtract(convertedAmount));
            cardService.updateBalance(targetCard.getCardId(), targetCard.getCardBalance().add(targetConvertedAmount));

            // Create transaction
            Transaction transaction = TransactionBuilder.builder()
                    .cardId(cardId)
                    .type(TransactionType.TRANSFER)
                    .amount(transferDTO.getAmount())
                    .currency(transferDTO.getCurrency().toUpperCase())
                    .baseAmount(convertedAmount)
                    .baseCurrency(sourceCard.getCardCurrency())
                    .rate(rateService.getRate(transferDTO.getCurrency().toUpperCase(), sourceCard.getCardCurrency()))
                    .description(transferDTO.getDescription())
                    .targetCardNumber(transferDTO.getTargetCardNumber())
                    .build();

            return transactionRepository.save(transaction);

        } catch (Exception e) {

            throw new TransactionErrorException("Ошибка перевода денежных средств: " + e.getMessage());
        }
    }

    public TransactionResponseDTO convertToDTO(Transaction transaction) {
        return TransactionConverter.convertToDTO(transaction);
    }

}


