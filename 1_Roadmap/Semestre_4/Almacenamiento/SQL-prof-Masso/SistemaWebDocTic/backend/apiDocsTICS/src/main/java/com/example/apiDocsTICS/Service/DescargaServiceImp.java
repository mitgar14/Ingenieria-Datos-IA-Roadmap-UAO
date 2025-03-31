package com.example.apiDocsTICS.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.apiDocsTICS.Exception.RecursoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.DescargaModel;
import com.example.apiDocsTICS.Repository.IDescargaRepository;
import com.example.apiDocsTICS.Repository.IDocumentoRepository;
import com.example.apiDocsTICS.Repository.IUsuarioRepository;

@Service
public class DescargaServiceImp implements IDescargaService {
    @Autowired
    IDescargaRepository descargaRepository;
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IDocumentoRepository documentoRepository;

    @Override
    public String crearDescarga(DescargaModel descarga) {
            descarga.setFechaDescarga(LocalDateTime.now());
            if(!documentoRepository.existsById(descarga.getDocumento().getIdDocumento())){
                throw new RecursoNoEncontradoException("Documento con id "+descarga.getDocumento().getIdDocumento()+" no existe");
            }
            if(!usuarioRepository.existsById(descarga.getUsuario().getIdUsuario())){
                throw new RecursoExistente("Usuario con id "+descarga.getUsuario().getIdUsuario()+" no existe");
            }
            descargaRepository.save(descarga);
            return "Descarga creada exitosamente";
    }

    @Override
    public DescargaModel obtenerDescargaPorId(int idDescarga) {
        return descargaRepository.findById(idDescarga)
                .orElseThrow(() -> new RecursoNoEncontradoException("Descarga no encontrada"));
    }

    @Override
    public List<DescargaModel> obtenerDescargas() {
        return descargaRepository.findAll();
    }
}
