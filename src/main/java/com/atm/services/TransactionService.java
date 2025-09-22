package com.atm.services;

import com.atm.dto.TransferDTO;
import com.atm.dto.request.TransactionRequestDTO;
import com.atm.exception.InsufficientFundsException;
import com.atm.models.entities.Card;
import com.atm.models.entities.Transaction;
import com.atm.models.enums.TransactionStatus;
import com.atm.models.enums.TransactionType;
import com.atm.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CardService cardService;

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
            Transaction transaction = new Transaction();
            transaction.setCardId(cardId);
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setAmount(transactionRequestDTO.getAmount());
            transaction.setCurrency(transactionRequestDTO.getCurrency().toUpperCase());
            transaction.setBaseAmount(convertedAmount);
            transaction.setBaseCurrency(card.getCardCurrency());
            transaction.setRate(rateService.getRate(
                    transactionRequestDTO.getCurrency().toUpperCase(),
                    card.getCardCurrency()
            ));
            transaction.setDescription(transactionRequestDTO.getDescription());
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setCreatedAt(LocalDateTime.now());

            return transactionRepository.save(transaction);

        } catch (Exception e) {

            throw new RuntimeException("Ошибка перевода: " + e.getMessage(), e);
        }
    }

    public Transaction withdraw(Long cardId, TransactionRequestDTO transactionRequestDTO) {
        Card card = cardService.getCardById(cardId);

        // Convert sum to rate
        BigDecimal convertedAmount = rateService.convertCurrency(
                transactionRequestDTO.getAmount(),
                transactionRequestDTO.getCurrency().toUpperCase(),
                card.getCardCurrency()
        );

        if (card.getCardBalance().compareTo(convertedAmount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }

        // Updated balanced
        BigDecimal newBalance = card.getCardBalance().subtract(convertedAmount);
        cardService.updateBalance(cardId, newBalance);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setCurrency(transactionRequestDTO.getCurrency().toUpperCase());
        transaction.setBaseAmount(convertedAmount);
        transaction.setBaseCurrency(card.getCardCurrency());
        transaction.setRate(rateService.getRate(
                transactionRequestDTO.getCurrency().toUpperCase(),
                card.getCardCurrency()
        ));
        transaction.setDescription(transactionRequestDTO.getDescription());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public Transaction transfer(Long cardId, TransferDTO transferDTO) {
        Card sourceCard = cardService.getCardById(cardId);
        Card targetCard = cardService.getCardByNumber(transferDTO.getTargetCardNumber());

        if (targetCard == null) {
            throw new RuntimeException("Некорректная карта для перевода");
        }

        // Convert sum to rate
        BigDecimal convertedAmount = rateService.convertCurrency(
                transferDTO.getAmount(),
                transferDTO.getCurrency().toUpperCase(),
                sourceCard.getCardCurrency()
        );

        if (sourceCard.getCardBalance().compareTo(convertedAmount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств на счете");
        }

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
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(transferDTO.getAmount());
        transaction.setCurrency(transferDTO.getCurrency().toUpperCase());
        transaction.setBaseAmount(convertedAmount);
        transaction.setBaseCurrency(sourceCard.getCardCurrency());
        transaction.setRate(rateService.getRate(
                transferDTO.getCurrency().toUpperCase(),
                sourceCard.getCardCurrency()
        ));
        transaction.setDescription(transferDTO.getDescription());
        transaction.setTargetCardNumber(transferDTO.getTargetCardNumber());
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCreatedAt(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

}


