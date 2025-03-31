package com.example.apiDocsTICS.Service;


import java.util.List;
import com.example.apiDocsTICS.Model.UsuarioModel;


public interface IUsuarioService{
    String crearUsuario(UsuarioModel usuario);
    UsuarioModel buscarUsuarioPorId(int idUsuario);
    List<UsuarioModel> listarUsuarios();
    String eliminarUsuario(int idUsuario);
}
