package com.atm.util;

import com.atm.model.enums.TransactionStatus;
import com.atm.model.enums.TransactionType;
import com.atm.model.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionBuilder {
    private Long cardId;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private BigDecimal baseAmount;
    private String baseCurrency;
    private BigDecimal rate;
    private String description;
    private String targetCardNumber;
    private String paymentCode;
    private TransactionStatus status = TransactionStatus.COMPLETED;
    private LocalDateTime createdAt = LocalDateTime.now();

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public TransactionBuilder cardId(Long cardId) {
        this.cardId = cardId;
        return this;
    }

    public TransactionBuilder type(TransactionType type) {
        this.type = type;
        return this;
    }

    public TransactionBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder currency(String currency) {
        this.currency = currency;
        return this;
    }

    public TransactionBuilder baseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
        return this;
    }

    public TransactionBuilder baseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }

    public TransactionBuilder rate(BigDecimal rate) {
        this.rate = rate;
        return this;
    }

    public TransactionBuilder description(String description) {
        this.description = description;
        return this;
    }

    public TransactionBuilder targetCardNumber(String targetCardNumber) {
        this.targetCardNumber = targetCardNumber;
        return this;
    }

    public TransactionBuilder paymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
        return this;
    }

    public TransactionBuilder status(TransactionStatus status) {
        this.status = status;
        return this;
    }

    public Transaction build() {
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setBaseAmount(baseAmount);
        transaction.setBaseCurrency(baseCurrency);
        transaction.setRate(rate);
        transaction.setDescription(description);
        transaction.setTargetCardNumber(targetCardNumber);
        transaction.setPaymentCode(paymentCode);
        transaction.setStatus(status);
        transaction.setCreatedAt(createdAt);
        return transaction;
    }
}
