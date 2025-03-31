package com.example.apiDocsTICS.Model.Documents;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ReplicasComentario {
    private String comentarioReplica;
    private ObjectId usuarioReplicaId;
    private LocalDateTime fechaReplica;
}
