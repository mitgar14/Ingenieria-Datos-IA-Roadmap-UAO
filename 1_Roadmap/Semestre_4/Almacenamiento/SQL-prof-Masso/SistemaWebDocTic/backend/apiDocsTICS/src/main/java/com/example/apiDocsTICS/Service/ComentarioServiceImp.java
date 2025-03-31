package com.example.apiDocsTICS.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.Exception.ForeignKeyConstraintComentarioException;
import com.example.apiDocsTICS.Exception.ForeignKeyConstraintDocumentoException;
import com.example.apiDocsTICS.Exception.ForeignKeyConstraintUsuarioException;
import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.ComentarioModel;
import com.example.apiDocsTICS.Repository.IComentarioRepository;




@Service
public class ComentarioServiceImp implements IComentarioService {
    @Autowired IComentarioRepository comentarioRepository;
    @Override
    public String crearComentario(ComentarioModel comentario) {
        try {
            comentario.setFecha(LocalDateTime.now());
            comentarioRepository.save(comentario);
            return "El comentario con id " + comentario.getIdComentario() + " ha sido creado";
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("fk_Comentario_Documento1")) {
                throw new ForeignKeyConstraintDocumentoException("Error. El documento con ID: " + comentario.getIdDocumento().getIdDocumento() + " no fue encontrado");
            } else if (e.getMessage().contains("fk_Comentario_Usuario1")) {
                throw new ForeignKeyConstraintUsuarioException("Error. El usuario con ID: " + comentario.getIdUsuario().getIdUsuario() + " no fue encontrado");
            } else if (e.getMessage().contains("fk_Comentario_Comentario1")) {
                throw new ForeignKeyConstraintComentarioException("Error. El Idcomentario al que le apunta el subIdComentario con ID: " + comentario.getSubidComentario().getIdComentario() + " no fue encontrado");
            } else {
                throw e;
            }
        }
    }

    @Override
    public String eliminarComentarioPorId(int idComentario) {
        Optional<ComentarioModel> comentarioEncontrado = comentarioRepository.findById(idComentario);
        if (comentarioEncontrado.isPresent()) {
            comentarioRepository.deleteById(idComentario);
            return "El comentario con id " + idComentario + " ha sido eliminado";
        } else {
            throw new RecursoNoEncontradoException("Comentario no encontrado con el Id " + idComentario + " no existe");
        }
    }

    @Override
    public String modificarComentarioPorId(int idComentario, ComentarioModel comentario) {
        try {
            Optional<ComentarioModel> comentarioEncontrado = comentarioRepository.findById(idComentario);
            if (comentarioEncontrado.isPresent()) {
                ComentarioModel comentarioModificado = comentarioEncontrado.get();
                comentarioModificado.setComentario(comentario.getComentario());
                comentarioModificado.setFecha(LocalDateTime.now());
                comentarioModificado.setIdUsuario(comentario.getIdUsuario());
                comentarioModificado.setIdDocumento(comentario.getIdDocumento());
                comentarioModificado.setSubidComentario(comentario.getSubidComentario());
                comentarioRepository.save(comentarioModificado);  // Guardar los cambios
                return "El comentario con id " + idComentario + " ha sido modificado";
            } else {
                throw new RecursoNoEncontradoException("Comentario no encontrado con el Id " + idComentario);
            }
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("fk_Comentario_Documento1")) {
                throw new ForeignKeyConstraintDocumentoException("Error. El documento con ID: " + comentario.getIdDocumento().getIdDocumento() + " no fue encontrado");
            } else if (e.getMessage().contains("fk_Comentario_Usuario1")) {
                throw new ForeignKeyConstraintUsuarioException("Error. El usuario con ID: " + comentario.getIdUsuario().getIdUsuario() + " no fue encontrado");
            } else if (e.getMessage().contains("fk_Comentario_Comentario1")) {
                throw new ForeignKeyConstraintComentarioException("Error. El Idcomentario al que le apunta el subIdComentario con ID: " + comentario.getSubidComentario().getIdComentario() + " no fue encontrado");
            } else {
                throw e;
            }
        }
    }

    @Override
    public ComentarioModel obtenerComentarioPorId(int idComentario) {
        Optional<ComentarioModel> comentarioEncontrado = comentarioRepository.findById(idComentario);
        return comentarioEncontrado.orElseThrow(()->new RecursoNoEncontradoException("El comentario con el id " + idComentario + " no existe"));
    }

    @Override
    public List<ComentarioModel> obtenerComentarios() {
        return comentarioRepository.findAll();
    }
    
}
