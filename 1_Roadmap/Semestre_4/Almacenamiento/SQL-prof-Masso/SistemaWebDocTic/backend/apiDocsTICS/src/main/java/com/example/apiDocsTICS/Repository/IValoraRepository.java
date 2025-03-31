package com.example.apiDocsTICS.Repository;

import com.example.apiDocsTICS.Model.DocumentoModel;
import com.example.apiDocsTICS.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apiDocsTICS.Model.ValoraModel;

public interface IValoraRepository extends JpaRepository<ValoraModel, Integer> {
    Boolean existsByIdUsuarioAndIdDocumento(UsuarioModel idUsuario, DocumentoModel idDocumento);
}
