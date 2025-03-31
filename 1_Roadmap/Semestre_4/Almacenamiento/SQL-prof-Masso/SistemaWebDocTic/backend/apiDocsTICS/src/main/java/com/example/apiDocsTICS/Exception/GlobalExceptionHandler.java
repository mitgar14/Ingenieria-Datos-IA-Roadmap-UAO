package com.example.apiDocsTICS.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<?> handleRecursoNoEncontradoException(RecursoNoEncontradoException ex, WebRequest request) {
        String mensaje = ex.getMessage();
        return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForeignKeyConstraintException.class)
    public ResponseEntity<?> handleForeignKeyConstraintException(ForeignKeyConstraintException ex, WebRequest request) {
        String mensaje = ex.getMessage();
        return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForeignKeyConstraintDocumentoException.class)
    public ResponseEntity<?> handleForeignKeyConstraintDocumentoException(ForeignKeyConstraintDocumentoException ex, WebRequest request) {
        String mensaje = ex.getMessage();
        return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForeignKeyConstraintUsuarioException.class)
    public ResponseEntity<?> handleForeignKeyConstraintUsuarioException(ForeignKeyConstraintUsuarioException ex, WebRequest request) {
        String mensaje = ex.getMessage();
        return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForeignKeyConstraintComentarioException.class)
    public ResponseEntity<?> handleForeignKeyConstraintComentarioException(ForeignKeyConstraintComentarioException ex, WebRequest request) {
        String mensaje = ex.getMessage();
        return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
    }
}