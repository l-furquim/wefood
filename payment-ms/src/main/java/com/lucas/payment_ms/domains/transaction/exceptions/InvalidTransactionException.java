package com.lucas.payment_ms.domains.transaction.exceptions;

public class InvalidTransactionException extends RuntimeException {
  public InvalidTransactionException(String message) {
    super(message);
  }
}
