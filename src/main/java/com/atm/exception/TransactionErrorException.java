package com.atm.exception;

public class TransactionErrorException extends RuntimeException{
    public TransactionErrorException(String message) {
        super(message);
    }
}
