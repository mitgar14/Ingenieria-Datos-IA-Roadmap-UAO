package com.example.apiDocsTICS.Service;

import java.util.List;

import org.bson.types.ObjectId;

import com.example.apiDocsTICS.DTO.ComentarioResponseDTO;
import com.example.apiDocsTICS.Model.ComentariosModel;

public interface IComentariosService {
    String CrearComentario(ComentariosModel comentariosModel);
    List<ComentarioResponseDTO> listarComentarios();
    ComentarioResponseDTO buscarComentarioPorId(ObjectId id);
    String eliminarComentario(ObjectId id);
    String insertarReplica(ObjectId comentarioId, ComentariosModel replicaModel);
}
