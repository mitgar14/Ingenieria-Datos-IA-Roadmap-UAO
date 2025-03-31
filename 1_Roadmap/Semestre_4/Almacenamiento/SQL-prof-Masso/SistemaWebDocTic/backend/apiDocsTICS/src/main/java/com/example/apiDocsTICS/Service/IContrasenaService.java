package com.example.apiDocsTICS.Service;

import com.example.apiDocsTICS.Model.ContrasenaModel;

public interface IContrasenaService {
    String obtenerContrasenaActivaUsuario(Integer idUsuario);
    String crearContrasena(ContrasenaModel contrasena);
}
