package com.unab.banca.Validation.Exception;


import com.unab.banca.Validation.Entity.Error;;

public class NoFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private final transient Error error;

  public NoFoundException(Error error) {
    super();
    this.error = error;
  }

  public NoFoundException(String message, Error error) {
    super(message);
    this.error = error;
  }

  public Error getError() {
    return error;
  }
}


