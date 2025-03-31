package com.example.apiDocsTICS.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.apiDocsTICS.DTO.Documents.ValoracionDTO;
import com.example.apiDocsTICS.Exception.AccesoNoPermitidoException;
import com.example.apiDocsTICS.Model.Documents.Descarga;
import com.example.apiDocsTICS.Model.Documents.Valoracion;
import com.example.apiDocsTICS.Model.Documents.Vista;
import com.example.apiDocsTICS.Model.ENUM.Visibilidad;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.DocumentosModel;
import com.example.apiDocsTICS.Repository.IDocumentosRepository;

@Service
public class DocumentosServiceImp implements IDocumetosService {

    @Autowired
    private IDocumentosRepository documentosRepository;

    @Override
    public String CrearDocumento(DocumentosModel documentosModel) {
        documentosRepository.save(documentosModel);
        return "Documento creado con éxito";
    }

    @Override
    public List<DocumentosModel> listarDocumentos() {
        return documentosRepository.findAll();
    }

    @Override
    public DocumentosModel buscarDocumento(ObjectId id) {
        return documentosRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Documento no encontrado"));
    }

    // Otros métodos...

    @Override
    public String valorarDocumento(ObjectId documentoId, ObjectId usuarioId, ValoracionDTO valoracionDTO) {
        // Obtener el documento
        DocumentosModel documento = documentosRepository.findById(documentoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Documento no encontrado"));

        // Verificar si el usuario ha visto o descargado el documento
        boolean haVisto = documento.getVistas() != null && documento.getVistas().stream()
                .anyMatch(vista -> vista.getUsuarioId().equals(usuarioId));

        boolean haDescargado = documento.getDescargas() != null && documento.getDescargas().stream()
                .anyMatch(descarga -> descarga.getUsuarioId().equals(usuarioId));

        if (!haVisto && !haDescargado) {
            throw new AccesoNoPermitidoException("El usuario debe haber visto o descargado el documento antes de valorarlo");
        }

        // Agregar la valoración
        Valoracion nuevaValoracion = new Valoracion(usuarioId, valoracionDTO.getValoracion(), new Date());
        if (documento.getValoraciones() == null) {
            documento.setValoraciones(new ArrayList<>());
        }
        documento.getValoraciones().add(nuevaValoracion);

        // Guardar el documento actualizado
        documentosRepository.save(documento);

        return "Valoración agregada con éxito";
    }

    @Override
    public String registrarVista(ObjectId documentoId, ObjectId usuarioId) {
        DocumentosModel documento = documentosRepository.findById(documentoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Documento no encontrado"));

        // Verificar si el documento es Privado y si el usuario está en infoAutores
        if (documento.getVisibilidad() == Visibilidad.Privado) {
            boolean esAutor = documento.getInfoAutores() != null && documento.getInfoAutores().stream()
                    .anyMatch(infoAutor -> infoAutor.getUsuarioId().equals(usuarioId));

            if (!esAutor) {
                throw new AccesoNoPermitidoException("No tienes permiso para ver este documento");
            }
        }

        // Registrar la vista
        Vista nuevaVista = new Vista(usuarioId, new Date());
        if (documento.getVistas() == null) {
            documento.setVistas(new ArrayList<>());
        }
        documento.getVistas().add(nuevaVista);

        // Guardar el documento actualizado
        documentosRepository.save(documento);

        return "Vista registrada con éxito";
    }

    @Override
    public String registrarDescarga(ObjectId documentoId, ObjectId usuarioId) {
        DocumentosModel documento = documentosRepository.findById(documentoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Documento no encontrado"));

        // Verificar si el documento es Privado y si el usuario está en infoAutores
        if (documento.getVisibilidad() == Visibilidad.Privado) {
            boolean esAutor = documento.getInfoAutores() != null && documento.getInfoAutores().stream()
                    .anyMatch(infoAutor -> infoAutor.getUsuarioId().equals(usuarioId));

            if (!esAutor) {
                throw new AccesoNoPermitidoException("No tienes permiso para descargar este documento");
            }
        }

        // Registrar la descarga
        Descarga nuevaDescarga = new Descarga(usuarioId, new Date());
        if (documento.getDescargas() == null) {
            documento.setDescargas(new ArrayList<>());
        }
        documento.getDescargas().add(nuevaDescarga);

        // Guardar el documento actualizado
        documentosRepository.save(documento);

        return "Descarga registrada con éxito";
    }

}
