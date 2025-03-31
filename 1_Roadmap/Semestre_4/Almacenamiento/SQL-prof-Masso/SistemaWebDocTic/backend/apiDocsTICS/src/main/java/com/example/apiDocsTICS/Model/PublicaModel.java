package com.example.apiDocsTICS.Model;

import java.time.LocalDateTime;

import com.example.apiDocsTICS.Model.ENUM.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="Publica")
@AllArgsConstructor
@NoArgsConstructor
public class PublicaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPublica;

    private LocalDateTime fechaPublicacion;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private UsuarioModel usuario;

    // Relación con Documento, sin cambiar DocumentoModel
    @ManyToOne
    @JoinColumn(name = "idDocumento", referencedColumnName = "idDocumento")
    private DocumentoModel documento;

    // Enum para el rol
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
