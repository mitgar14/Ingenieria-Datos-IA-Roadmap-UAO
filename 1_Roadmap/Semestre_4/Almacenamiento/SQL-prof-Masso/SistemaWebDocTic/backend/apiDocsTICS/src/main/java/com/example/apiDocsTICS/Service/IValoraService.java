package com.example.apiDocsTICS.Service;

import java.util.List;

import com.example.apiDocsTICS.Model.ValoraModel;

public interface IValoraService {
    String crearValoracion(ValoraModel valoracion);
    String eliminarValoracionPorId(int idValora);
    String modificarValoracionPorId(int idValora, ValoraModel valoracion);
    ValoraModel obtenerValoracionPorId(int idValora);
    List<ValoraModel> obtenerValoraciones();
}
