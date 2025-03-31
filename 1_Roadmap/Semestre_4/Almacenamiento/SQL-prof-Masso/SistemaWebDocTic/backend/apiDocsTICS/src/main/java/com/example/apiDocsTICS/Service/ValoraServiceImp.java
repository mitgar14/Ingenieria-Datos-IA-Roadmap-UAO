package com.example.apiDocsTICS.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.apiDocsTICS.Exception.RecursoExistente;
import com.example.apiDocsTICS.Exception.ValoracionIncorrecta;
import com.example.apiDocsTICS.Model.DocumentoModel;
import com.example.apiDocsTICS.Model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.ValoraModel;
import com.example.apiDocsTICS.Repository.IValoraRepository;
import com.example.apiDocsTICS.Repository.IDocumentoRepository;
import com.example.apiDocsTICS.Repository.IUsuarioRepository;

@Service
public class ValoraServiceImp implements IValoraService{
    @Autowired IValoraRepository valoraRepository;
    @Autowired IUsuarioRepository usuarioRepository;
    @Autowired IDocumentoRepository documentoRepository;

    @Override
    public String crearValoracion(ValoraModel valoracion) {
        if(!(valoracion.getValoracion() <= 5 && valoracion.getValoracion() >= 1)){
            throw new ValoracionIncorrecta("La valoracion debe estar entre 1 y 5");
        }
        if (!documentoRepository.existsById(valoracion.getIdDocumento().getIdDocumento()))
            throw new RecursoNoEncontradoException("El Documento "+valoracion.getIdDocumento().getIdDocumento()+" no existe");
        if (!usuarioRepository.existsById(valoracion.getIdUsuario().getIdUsuario()))
            throw new RecursoNoEncontradoException("El Usuario "+valoracion.getIdUsuario().getIdUsuario()+" no existe");
        if (valoraRepository.existsByIdUsuarioAndIdDocumento(
                new UsuarioModel(valoracion.getIdUsuario().getIdUsuario()),
                new DocumentoModel(valoracion.getIdDocumento().getIdDocumento())))
            throw new RecursoExistente(
                    "La valoracion del usuario "+valoracion.getIdUsuario().getIdUsuario()+" al documento "+valoracion.getIdDocumento().getIdDocumento()+"Ya existe");
        valoracion.setFechaValoracion(LocalDateTime.now());
        valoraRepository.save(valoracion);
        return "La valoracion con Id "+ valoracion.getIdValora() + " fue creada con exito";
    }

    @Override
    public String eliminarValoracionPorId(int idValora) {
        Optional<ValoraModel> valoracionEncontrada = valoraRepository.findById(idValora);
        if (valoracionEncontrada.isPresent()) {
            valoraRepository.delete(valoracionEncontrada.get());
            return "La valoracion con Id "+ idValora + " fue eliminada con exito";
        } else {
            throw new RecursoNoEncontradoException("Valoracion no encontrada con el Id " + idValora);
        }
    }

    @Override
    public String modificarValoracionPorId(int idValora, ValoraModel valoracion) {
        // Validar que la valoración esté entre 1 y 5
        if (!(valoracion.getValoracion() <= 5 && valoracion.getValoracion() >= 1)) {
            throw new ValoracionIncorrecta("La valoración debe estar entre 1 y 5");
        }
    
        // Buscar la valoración existente por su ID
        Optional<ValoraModel> valoracionEncontrada = valoraRepository.findById(idValora);
        if (valoracionEncontrada.isPresent()) {
            ValoraModel valoracionModificada = valoracionEncontrada.get();
            valoracionModificada.setValoracion(valoracion.getValoracion());
            valoracionModificada.setIdDocumento(valoracion.getIdDocumento());
            valoracionModificada.setIdUsuario(valoracion.getIdUsuario());
            valoracionModificada.setFechaValoracion(LocalDateTime.now()); // Establecer la fecha de valoración a ahora
            valoraRepository.save(valoracionModificada);
            return "La valoración con Id " + idValora + " fue modificada con éxito";
        } else {
            throw new RecursoNoEncontradoException("Valoración no encontrada con el Id " + idValora);
        }
    }

    @Override
    public ValoraModel obtenerValoracionPorId(int idValora) {
        Optional<ValoraModel> valoracionEncontrada = valoraRepository.findById(idValora);
        if (valoracionEncontrada.isPresent()) {
            return valoracionEncontrada.get();
        } else {
            throw new RecursoNoEncontradoException("Valoracion no encontrada con el Id " + idValora);
        }
    }

    @Override
    public List<ValoraModel> obtenerValoraciones() {
        return valoraRepository.findAll();
    }
    
}
