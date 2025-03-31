package com.example.apiDocsTICS.Model;

import com.example.apiDocsTICS.Model.ENUM.Visibilidad;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="Documento")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDocumento;
    private String tituloDoc;
    @Column(name="visibilidad")
    @Enumerated(EnumType.STRING)
    private Visibilidad visibilidad;
    @JsonProperty("URL")
    private String URL;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name="idCategoria")
    private CategoriaModel idCategoria;
    
    public DocumentoModel(Integer idDocumento){
        this.idDocumento = idDocumento;
    }
}
