package com.example.apiDocsTICS.Exception;

public class AccesoNoPermitidoException extends RuntimeException {
  public AccesoNoPermitidoException(String mensaje) {
    super(mensaje);
  }
}