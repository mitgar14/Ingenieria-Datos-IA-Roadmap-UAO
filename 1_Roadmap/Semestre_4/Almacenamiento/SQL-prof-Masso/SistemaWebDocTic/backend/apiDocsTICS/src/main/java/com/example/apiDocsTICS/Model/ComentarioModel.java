package com.example.apiDocsTICS.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@Table(name = "Comentario")
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComentario;
    private String comentario;
    private LocalDateTime fecha;
    @ManyToOne
    @JoinColumn(name="idUsuario")
    private UsuarioModel idUsuario;
    @ManyToOne
    @JoinColumn(name="idDocumento")
    private DocumentoModel idDocumento;
    @ManyToOne
    @JoinColumn(name="subidComentario")
    private ComentarioModel subidComentario;
}
