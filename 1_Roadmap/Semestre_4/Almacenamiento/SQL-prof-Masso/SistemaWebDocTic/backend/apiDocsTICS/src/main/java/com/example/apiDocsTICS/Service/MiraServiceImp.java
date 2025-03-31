package com.example.apiDocsTICS.Service;

import java.time.LocalDateTime;

import com.example.apiDocsTICS.Model.MiraModel;
import com.example.apiDocsTICS.Model.DocumentoModel;
import com.example.apiDocsTICS.Model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.apiDocsTICS.Repository.IMiraRepository;
import com.example.apiDocsTICS.Repository.IDocumentoRepository;
import com.example.apiDocsTICS.Repository.IUsuarioRepository;
import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Exception.RecursoExistente;

import java.util.List;
import java.util.Optional;

@Service
public class MiraServiceImp implements IMiraService {
    @Autowired
    IUsuarioRepository usuarioRepository;
    @Autowired
    IMiraRepository miraRepository;
    @Autowired
    IDocumentoRepository documentoRepository;

    @Override
    public List<MiraModel> obtenerMira() {
        return miraRepository.findAll();
    }

    @Override
    public List<MiraModel> obtenerMiraByUsuario(Integer idUsuario) {
        UsuarioModel usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el usuario " + idUsuario));
        return miraRepository.findByIdUsuario(usuario);
    }

    @Override
    public List<MiraModel> obtenerMiraByDocumento(Integer idDocumento) {
        DocumentoModel documento = documentoRepository.findById(idDocumento)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el documento " + idDocumento));
        return miraRepository.findByIdDocumento(documento);
    }

    @Override
    public String crearMira(MiraModel mira) {
        int idUsuario = mira.getIdUsuario().getIdUsuario();
        int idDocumento = mira.getIdDocumento().getIdDocumento();
        mira.setFechaMira(LocalDateTime.now());

        DocumentoModel documento = documentoRepository.findById(idDocumento)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el documento " + idDocumento));
        UsuarioModel usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encuentra el usuario " + idUsuario));

        Optional<MiraModel> miraOpt = miraRepository.findByIdDocumentoAndIdUsuario(documento, usuario);
        if (miraOpt.isPresent()) {
            throw new RecursoExistente("El Documento " + documento.getTituloDoc() + " ya ha sido observado por " + usuario.getNombreUsuario());
        }
        miraRepository.save(mira);
        return "Se ha creado exitosamente el registro de mira";
    }
}