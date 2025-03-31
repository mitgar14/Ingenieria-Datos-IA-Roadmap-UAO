package com.example.apiDocsTICS.Service;

import com.example.apiDocsTICS.Model.MiraModel;
import java.util.List;

public interface IMiraService {
String crearMira(MiraModel mira);
List<MiraModel> obtenerMiraByUsuario(Integer idUsuario);
List<MiraModel> obtenerMiraByDocumento(Integer idDocumento);
List<MiraModel> obtenerMira();
}
