package com.example.apiDocsTICS.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.apiDocsTICS.Model.DocumentosModel;

public interface IDocumentosRepository extends MongoRepository<DocumentosModel, ObjectId> {
    
    /**
     * Verifica si un usuario ha visto un documento específico
     * @param usuarioId ID del usuario que se verifica
     * @param documentoId ID del documento a verificar
     * @return true si el usuario ha visto el documento, false en caso contrario
     */
    @Query(value = "{ '_id': ?1, 'vistas': { $elemMatch: { 'usuarioId': ?0 } } }", exists = true)
    boolean existsByVistaUsuarioAndDocumentoId(ObjectId usuarioId, ObjectId documentoId);
}
