package com.atm.models.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long cardId;

    @Column(name="c_number", unique = true, nullable = false)
    private String cardNumber;

    @Column(name="c_pin_code", nullable = false)
    private Short cardPinCode;

    @Column(name = "c_currency", nullable = false)
    private String cardCurrency;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name="expires_at", nullable = false)
    private LocalDate cardExpiresAt;

    @Column(name="c_balance", nullable = false)
    private BigDecimal cardBalance;

    @JoinColumn(name = "c_user_id", nullable = false)
    private Long userId;

    public Card() {
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Short getCardPinCode() {
        return cardPinCode;
    }

    public void setCardPinCode(Short cardPinCode) {
        this.cardPinCode = cardPinCode;
    }

    public String getCardCurrency() {
        return cardCurrency;
    }

    public void setCardCurrency(String cardCurrency) {
        this.cardCurrency = cardCurrency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) { this.userId = userId; }

    public BigDecimal getCardBalance() { return cardBalance; }

    public void setCardBalance(BigDecimal cardPBalance) {
        this.cardBalance = cardPBalance;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCardExpiresAt() {
        return cardExpiresAt;
    }

    public void setCardExpiresAt(LocalDate cardExpiresAt) {
        this.cardExpiresAt = cardExpiresAt;
    }

}
