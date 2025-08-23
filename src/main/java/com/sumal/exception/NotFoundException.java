package com.sumal.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("Not found");
  }
}
