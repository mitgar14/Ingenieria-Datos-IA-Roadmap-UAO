package com.example.apiDocsTICS.Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document ("Categorias")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoriasModel {
    @Id
    private ObjectId _id;
    private String nombre;
    private ObjectId subIdCategoria;
}
