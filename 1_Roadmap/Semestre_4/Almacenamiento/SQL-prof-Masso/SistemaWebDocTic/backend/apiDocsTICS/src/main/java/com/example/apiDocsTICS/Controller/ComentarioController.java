package com.example.apiDocsTICS.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.ComentarioModel;
import com.example.apiDocsTICS.Service.IComentarioService;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
    @Autowired
    IComentarioService comentarioService;

    @PostMapping("/post")
    public ResponseEntity<String> crearDocumento(@RequestBody ComentarioModel Comentario) {
        comentarioService.crearComentario(Comentario);
        return new ResponseEntity<>(comentarioService.crearComentario(Comentario), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idComentario}")
    public ResponseEntity<?> eliminarComentarioPorId(@PathVariable int idComentario) {
        try {
            comentarioService.eliminarComentarioPorId(idComentario);
            return ResponseEntity.ok().build();
        } catch (RecursoNoEncontradoException e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @PutMapping("/put/{idComentario}")
    public ResponseEntity<?> modificarComentarioPorId(@PathVariable int idComentario, @RequestBody ComentarioModel comentario) {
        Object comentarioActualizado = comentarioService.modificarComentarioPorId(idComentario, comentario);
       if (comentarioActualizado != null) {
            return ResponseEntity.ok().body(comentarioActualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario no encontrado con el Id " + idComentario);
        }
    }

    @GetMapping("/get/{idComentario}")
    public ResponseEntity<?> obtenerComentarioPorId(@PathVariable int idComentario) {
        try {
            ComentarioModel comentario = comentarioService.obtenerComentarioPorId(idComentario);
            return ResponseEntity.ok().body(comentario);
        } catch (RecursoNoEncontradoException e){
            String mensajeError = e.getMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensajeError);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> obtenerComentarios() {
        return ResponseEntity.ok().body(comentarioService.obtenerComentarios());
    }
}
