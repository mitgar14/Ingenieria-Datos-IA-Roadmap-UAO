package com.example.apiDocsTICS.Service;

import java.util.List;
import com.example.apiDocsTICS.Model.DescargaModel;

public interface IDescargaService {
    String crearDescarga(DescargaModel descarga);
    DescargaModel obtenerDescargaPorId(int idDescarga);
    List<DescargaModel> obtenerDescargas();
}
