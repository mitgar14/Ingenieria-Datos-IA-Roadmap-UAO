package com.example.apiDocsTICS.Service;


import java.util.List;

import org.bson.types.ObjectId;

import com.example.apiDocsTICS.Model.UsuariosModel;


public interface IUsuariosService {
    String crearUsuario(UsuariosModel usuario);
    UsuariosModel buscarUsuarioPorId(ObjectId usuarioId);
    List<UsuariosModel> listarUsuarios();
    String actualizarContrasena(ObjectId usuarioId, UsuariosModel usuario);
}
