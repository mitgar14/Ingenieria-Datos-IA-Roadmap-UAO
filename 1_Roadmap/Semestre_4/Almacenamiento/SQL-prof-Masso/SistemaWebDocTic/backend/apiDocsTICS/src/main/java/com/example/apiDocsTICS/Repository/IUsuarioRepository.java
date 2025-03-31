package com.example.apiDocsTICS.Repository;


import com.example.apiDocsTICS.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

}
