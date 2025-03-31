package com.example.apiDocsTICS.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.example.apiDocsTICS.Model.ENUM.Estado;


@Entity
@Table (name="Contrasena")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContrasenaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idContrasena;
    private String contrasena;
    @Column (name="estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private LocalDateTime fecha;
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioModel idUsuario;

}
