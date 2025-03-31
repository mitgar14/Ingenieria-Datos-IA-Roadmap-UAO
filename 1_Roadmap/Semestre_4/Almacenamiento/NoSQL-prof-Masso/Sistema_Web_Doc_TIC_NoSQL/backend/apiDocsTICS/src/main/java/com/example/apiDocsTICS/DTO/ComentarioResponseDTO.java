package com.example.apiDocsTICS.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class ComentarioResponseDTO {
    private String comentario;
    private String nombreUsuario;
    private LocalDateTime fecha;
    private final List<ReplicaResponseDTO> replicas;

    public ComentarioResponseDTO(String comentario, String nombreUsuario, LocalDateTime fecha, List<ReplicaResponseDTO> replicas) {
        this.comentario = comentario;
        this.nombreUsuario = nombreUsuario;
        this.fecha = fecha;
        this.replicas = replicas;
    }

    public List<ReplicaResponseDTO> getReplicas() {
        return replicas;
    }

    // Getters y setters
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public LocalDateTime getfecha() {
        return fecha;
    }

    public void setfecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}