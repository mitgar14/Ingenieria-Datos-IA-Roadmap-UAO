package com.example.apiDocsTICS.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiDocsTICS.DTO.ComentarioResponseDTO;
import com.example.apiDocsTICS.Model.ComentariosModel;
import com.example.apiDocsTICS.Service.IComentariosService;


@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    @Autowired
    private IComentariosService comentariosService;

    @GetMapping("/listar")
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentarios() {
        List<ComentarioResponseDTO> comentarios = comentariosService.listarComentarios();
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ComentarioResponseDTO> buscarComentarioPorId(@PathVariable ObjectId id) {
        ComentarioResponseDTO comentario = comentariosService.buscarComentarioPorId(id);
        return ResponseEntity.ok(comentario);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable ObjectId id) {
        String resultado = comentariosService.eliminarComentario(id);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/insertar")
    public ResponseEntity<String> crearComentario(@RequestBody ComentariosModel comentario) {
        String resultado = comentariosService.CrearComentario(comentario);
        if (resultado.equals("El usuario no ha visto el documento y no puede comentar.")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/insertarReplica/{comentarioId}")
    public ResponseEntity<String> insertarReplica(
            @PathVariable("comentarioId") String comentarioId, 
            @RequestBody ComentariosModel replica) {
        try {
            ObjectId id = new ObjectId(comentarioId);
            String resultado = comentariosService.insertarReplica(id, replica);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("ID de comentario inválido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al insertar réplica: " + e.getMessage());
        }
    }
    
}