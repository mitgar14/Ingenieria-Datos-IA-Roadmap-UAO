package com.example.apiDocsTICS.Exception;

public class ForeignKeyConstraintException extends RuntimeException {
    public ForeignKeyConstraintException(String mensaje) {
        super(mensaje);
    }
}