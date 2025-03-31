package com.example.apiDocsTICS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apiDocsTICS.Model.DocumentoModel;

public interface IDocumentoRepository extends JpaRepository<DocumentoModel, Integer> {
    
}
