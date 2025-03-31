package com.example.apiDocsTICS.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table (name="Usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;
    private String nombreUsuario;
    private String deptoOrigen;
    private String ciudadOrigen;
    private String correoUsuario;
    private String preguntaSecreta;
    private String respPregunSecre;

    public UsuarioModel(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
