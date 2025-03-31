package com.example.apiDocsTICS.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;



@Data
@Entity
@Table (name = "Mira")
@NoArgsConstructor
@AllArgsConstructor
public class MiraModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMira;
    private LocalDateTime fechaMira;
    @ManyToOne
    @JoinColumn(name= "idUsuario", nullable=false)
    private UsuarioModel idUsuario;
    @ManyToOne
    @JoinColumn(name="idDocumento", nullable=false)
    private DocumentoModel idDocumento;
}
