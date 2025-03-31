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
@Table(name="Descarga")
@AllArgsConstructor
@NoArgsConstructor
public class DescargaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDescarga;

    private LocalDateTime fechaDescarga;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "idDocumento")
    private DocumentoModel documento;
}
