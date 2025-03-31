package com.example.apiDocsTICS.Model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.apiDocsTICS.Model.Documents.Contrasenas;
import com.example.apiDocsTICS.Model.Documents.Localizacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document ("Usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuariosModel {
    @Id
    private ObjectId _id;
    private String nombreUsuario;
    private String correoUsuario;
    private List<Localizacion> localizacion = new ArrayList<>();
    private String preguntaSecreta;
    private String resPregunSecret;
    private List<Contrasenas> contrasenas = new ArrayList<>();
}
