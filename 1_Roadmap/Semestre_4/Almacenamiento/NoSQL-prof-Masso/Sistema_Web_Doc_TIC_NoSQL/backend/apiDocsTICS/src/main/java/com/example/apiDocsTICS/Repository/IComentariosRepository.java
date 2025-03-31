package com.example.apiDocsTICS.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.apiDocsTICS.Model.ComentariosModel;

@Repository
public interface IComentariosRepository extends MongoRepository<ComentariosModel, ObjectId> {
    
}
