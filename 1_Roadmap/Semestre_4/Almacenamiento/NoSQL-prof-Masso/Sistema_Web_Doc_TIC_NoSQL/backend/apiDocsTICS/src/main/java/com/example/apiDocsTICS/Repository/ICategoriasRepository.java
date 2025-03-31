package com.example.apiDocsTICS.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.apiDocsTICS.Model.CategoriasModel;

public interface ICategoriasRepository extends MongoRepository<CategoriasModel, ObjectId> {
    
}
