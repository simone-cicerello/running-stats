package com.thealmighty.runningstats.exception;

public class UtilException extends RuntimeException {

  public UtilException() {}

  public UtilException(String message) {
    super(message);
  }

  public UtilException(String message, Throwable cause) {
    super(message, cause);
  }

  public UtilException(Throwable cause) {
    super(cause);
  }

  public UtilException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
