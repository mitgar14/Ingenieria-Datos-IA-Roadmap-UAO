package com.example.apiDocsTICS.Service;

import java.util.List;

import com.example.apiDocsTICS.Model.DocumentoModel;

public interface IDocumentoService {
    String crearDocumento(DocumentoModel documento);
    String eliminarDocumentoPorId(int idDocumento);
    String modificarDocumentoPorId(int idDocumento, DocumentoModel documento);
    DocumentoModel obtenerDocumentoPorId(int idDocumento);
    List<DocumentoModel> obtenerDocumentos();
}
