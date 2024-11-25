package com.unab.banca.Validation.Exception;


import com.unab.banca.Validation.Entity.Error;;

public class NoAuthorizeException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private final transient Error error;

  public NoAuthorizeException(Error error) {
    super();
    this.error = error;
  }

  public NoAuthorizeException(String message, Error error) {
    super(message);
    this.error = error;
  }

  public Error getError() {
    return error;
  }
}


