package com.example.apiDocsTICS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apiDocsTICS.Model.ComentarioModel;

public interface IComentarioRepository extends JpaRepository<ComentarioModel, Integer> {
    
}
