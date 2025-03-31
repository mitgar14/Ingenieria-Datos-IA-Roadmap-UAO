package com.example.apiDocsTICS.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apiDocsTICS.Model.MiraModel;
import com.example.apiDocsTICS.Model.UsuarioModel;
import com.example.apiDocsTICS.Model.DocumentoModel;

public interface IMiraRepository extends JpaRepository<MiraModel, Integer> {
    List<MiraModel> findByIdUsuario(UsuarioModel usuario);
    List<MiraModel> findByIdDocumento(DocumentoModel documento);
    Optional<MiraModel> findByIdDocumentoAndIdUsuario(DocumentoModel documento, UsuarioModel usuario);
}
