package com.example.apiDocsTICS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apiDocsTICS.Model.CategoriaModel;

public interface ICategoriaRepository extends JpaRepository<CategoriaModel, Integer>{
    
}
