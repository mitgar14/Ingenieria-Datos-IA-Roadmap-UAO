package com.example.apiDocsTICS.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiDocsTICS.DTO.ComentarioResponseDTO;
import com.example.apiDocsTICS.DTO.ReplicaResponseDTO;
import com.example.apiDocsTICS.Exception.GlobalExceptionHandler;
import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.ComentariosModel;
import com.example.apiDocsTICS.Model.Documents.ReplicasComentario;
import com.example.apiDocsTICS.Model.UsuariosModel;
import com.example.apiDocsTICS.Repository.IComentariosRepository;
import com.example.apiDocsTICS.Repository.IDocumentosRepository;
import com.example.apiDocsTICS.Repository.IUsuariosRepository;

@Service
public class ComentariosServiceImp implements IComentariosService {
    @Autowired
    private IComentariosRepository comentariosRepository;
    @Autowired
    private IUsuariosRepository usuariosRepository;
    @Autowired
    private IDocumentosRepository documentosRepository;

    @Override
    public String CrearComentario(ComentariosModel comentariosModel) {
        // Verificar si el usuario ha visto el documento
        ObjectId usuarioId = comentariosModel.getUsuarioId();
        ObjectId documentoId = comentariosModel.getDocumentoId();
        boolean haVistoDocumento = documentosRepository.existsByVistaUsuarioAndDocumentoId(usuarioId, documentoId);

        if (!haVistoDocumento) {
            return "El usuario no ha visto el documento y no puede comentar.";
        }

        // Establecer la fecha actual en el comentario
        comentariosModel.setFecha(LocalDateTime.now());
        if (comentariosModel.getReplicasComentario() != null && !comentariosModel.getReplicasComentario().isEmpty()) {
            comentariosModel.getReplicasComentario().forEach(replica -> {
                replica.setFechaReplica(LocalDateTime.now());
            });
        }
        comentariosRepository.save(comentariosModel);
        return "El comentario con id: " + comentariosModel.get_id() + " ha sido creado";
    }

    @Override
    public List<ComentarioResponseDTO> listarComentarios() {
        List<ComentariosModel> comentarios = comentariosRepository.findAll();
        return comentarios.stream().map(comentario -> {
            ObjectId usuarioId = comentario.getUsuarioId();
            UsuariosModel usuario = usuariosRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
            List<ReplicaResponseDTO> replicas = new ArrayList<>();
            if (comentario.getReplicasComentario() != null) {
                replicas = comentario.getReplicasComentario().stream()
                    .map(replica -> {
                        UsuariosModel usuarioReplica = usuariosRepository.findById(replica.getUsuarioReplicaId())
                                .orElseThrow(() -> new RuntimeException("Usuario de réplica no encontrado"));
                        return new ReplicaResponseDTO(
                            replica.getComentarioReplica(),
                            usuarioReplica.getNombreUsuario(),
                            replica.getFechaReplica()
                        );
                    })
                    .collect(Collectors.toList());
            }

            return new ComentarioResponseDTO(
                comentario.getComentario(), 
                usuario.getNombreUsuario(), 
                comentario.getFecha(),
                replicas
            );
        }).collect(Collectors.toList());
    }

    @Override
    public ComentarioResponseDTO buscarComentarioPorId(ObjectId id) {
        ComentariosModel comentario = comentariosRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Comentario no encontrado"));
        
        ObjectId usuarioId = comentario.getUsuarioId();
        UsuariosModel usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        List<ReplicaResponseDTO> replicas = new ArrayList<>();
        if (comentario.getReplicasComentario() != null) {
            replicas = comentario.getReplicasComentario().stream()
                .map(replica -> {
                    UsuariosModel usuarioReplica = usuariosRepository.findById(replica.getUsuarioReplicaId())
                            .orElseThrow(() -> new RuntimeException("Usuario de réplica no encontrado"));
                    return new ReplicaResponseDTO(
                        replica.getComentarioReplica(),
                        usuarioReplica.getNombreUsuario(),
                        replica.getFechaReplica()
                    );
                })
                .collect(Collectors.toList());
        }
    
        return new ComentarioResponseDTO(
            comentario.getComentario(),
            usuario.getNombreUsuario(),
            comentario.getFecha(),
            replicas
        );
    }

    @Override
    public String eliminarComentario(ObjectId id) {
        comentariosRepository.deleteById(id);
        return "El comentario con id: " + id + " ha sido eliminado";
    }
    
    @Override
    public String insertarReplica(ObjectId comentarioId, ComentariosModel replicaModel) {
        ComentariosModel comentario = comentariosRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        
        replicaModel.setFecha(LocalDateTime.now());
        ReplicasComentario replica = new ReplicasComentario();
        replica.setComentarioReplica(replicaModel.getComentario());
        replica.setUsuarioReplicaId(replicaModel.getUsuarioId());
        replica.setFechaReplica(replicaModel.getFecha());
        comentario.getReplicasComentario().add(replica);
        comentariosRepository.save(comentario);
        return "Réplica insertada con éxito";
    }
}

