package com.example.apiDocsTICS.DTO;

import java.time.LocalDateTime;

public class ReplicaResponseDTO {
    private final String comentarioReplica;
    private final String nombreUsuario;
    private final LocalDateTime fechaReplica;

    public ReplicaResponseDTO(String comentarioReplica, String nombreUsuario, LocalDateTime fechaReplica) {
        this.comentarioReplica = comentarioReplica;
        this.nombreUsuario = nombreUsuario;
        this.fechaReplica = fechaReplica;
    }

    public String getComentarioReplica() {
        return comentarioReplica;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public LocalDateTime getFechaReplica() {
        return fechaReplica;
    }
}
