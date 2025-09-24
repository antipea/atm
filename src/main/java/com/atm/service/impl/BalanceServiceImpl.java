package com.atm.service.impl;

import com.atm.dto.BalanceDTO;
import com.atm.model.entity.Card;
import com.atm.service.BalanceService;
import com.atm.service.CardService;
import com.atm.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private CardService cardService;

    @Autowired
    private RateService rateService;

    public BalanceDTO getBalanceInThreeCurrencies(Long cardId) {
        Card card = cardService.getCardById(cardId);

        // Get balance in card's currency
        BigDecimal balanceInCardCurrency = card.getCardBalance();
        String cardCurrency = card.getCardCurrency();

        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setCardCurrency(cardCurrency);

        // Convert balance in  currencies
        if ("BYN".equals(cardCurrency)) {
            balanceDTO.setBalanceByn(balanceInCardCurrency);
            balanceDTO.setBalanceUsd(rateService.convertCurrency(balanceInCardCurrency, "BYN", "USD"));
            balanceDTO.setBalanceEur(rateService.convertCurrency(balanceInCardCurrency, "BYN", "EUR"));
        } else if ("USD".equals(cardCurrency)) {
            balanceDTO.setBalanceUsd(balanceInCardCurrency);
            balanceDTO.setBalanceByn(rateService.convertCurrency(balanceInCardCurrency, "USD", "BYN"));
            balanceDTO.setBalanceEur(rateService.convertCurrency(balanceInCardCurrency, "USD", "EUR"));
        } else if ("EUR".equals(cardCurrency)) {
            balanceDTO.setBalanceEur(balanceInCardCurrency);
            balanceDTO.setBalanceByn(rateService.convertCurrency(balanceInCardCurrency, "EUR", "BYN"));
            balanceDTO.setBalanceUsd(rateService.convertCurrency(balanceInCardCurrency, "EUR", "USD"));
        } else {
            BigDecimal balanceInByn = rateService.convertCurrency(balanceInCardCurrency, cardCurrency, "BYN");
            balanceDTO.setBalanceByn(balanceInByn);
            balanceDTO.setBalanceUsd(rateService.convertCurrency(balanceInByn, "BYN", "USD"));
            balanceDTO.setBalanceEur(rateService.convertCurrency(balanceInByn, "BYN", "EUR"));
        }

        return balanceDTO;
    }
}

