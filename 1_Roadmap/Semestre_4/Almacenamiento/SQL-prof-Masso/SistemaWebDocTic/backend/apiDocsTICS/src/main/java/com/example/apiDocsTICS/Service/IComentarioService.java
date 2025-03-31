package com.example.apiDocsTICS.Service;

import java.util.List;

import com.example.apiDocsTICS.Model.ComentarioModel;

public interface IComentarioService {
    String crearComentario(ComentarioModel comentario);
    String eliminarComentarioPorId(int idComentario);
    String modificarComentarioPorId(int idComentario, ComentarioModel comentario);
    ComentarioModel obtenerComentarioPorId(int idComentario);
    List<ComentarioModel> obtenerComentarios();
}
