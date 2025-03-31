package com.example.apiDocsTICS.Exception;

public class ForeignKeyConstraintUsuarioException extends RuntimeException {
    public ForeignKeyConstraintUsuarioException(String mensaje) {
        super(mensaje);
    }
}
