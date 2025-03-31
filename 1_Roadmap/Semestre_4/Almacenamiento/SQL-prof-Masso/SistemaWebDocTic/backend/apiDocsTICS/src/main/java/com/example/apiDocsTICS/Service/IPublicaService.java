package com.example.apiDocsTICS.Service;

import java.util.List;
import com.example.apiDocsTICS.Model.PublicaModel;

public interface IPublicaService {
    String crearPublicacion(PublicaModel publicacion);
    String eliminarPublicacionPorId(int idPublica);
    String modificarPublicacionPorId(int idPublica, PublicaModel publicacion);
    PublicaModel obtenerPublicacionPorId(int idPublica);
    List<PublicaModel> obtenerPublicaciones();
}
