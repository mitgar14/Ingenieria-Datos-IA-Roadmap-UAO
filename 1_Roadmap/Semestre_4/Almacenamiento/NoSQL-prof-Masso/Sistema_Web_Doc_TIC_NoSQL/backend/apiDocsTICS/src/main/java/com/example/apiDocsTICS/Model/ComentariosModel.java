package com.example.apiDocsTICS.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.apiDocsTICS.Model.Documents.ReplicasComentario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document("Comentarios")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ComentariosModel {
    private ObjectId _id;
    private String comentario;
    private LocalDateTime fecha;
    private List<ReplicasComentario> replicasComentario = new ArrayList<>();
    private ObjectId usuarioId;
    private ObjectId documentoId;
}
