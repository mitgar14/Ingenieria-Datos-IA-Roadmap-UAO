package com.example.apiDocsTICS.Service;

import java.util.List;

import com.example.apiDocsTICS.DTO.Documents.ValoracionDTO;
import org.bson.types.ObjectId;

import com.example.apiDocsTICS.Model.DocumentosModel;

public interface IDocumetosService {
    String CrearDocumento(DocumentosModel documentosModel);
    List<DocumentosModel> listarDocumentos();
    DocumentosModel buscarDocumento(ObjectId id);
    String valorarDocumento(ObjectId documentoId, ObjectId usuarioId, ValoracionDTO valoracionDTO);
    String registrarVista(ObjectId documentoId, ObjectId usuarioId);
    String registrarDescarga(ObjectId documentoId, ObjectId usuarioId);

}
