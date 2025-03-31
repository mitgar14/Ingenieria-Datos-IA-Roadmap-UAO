package com.example.apiDocsTICS.Controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiDocsTICS.Exception.RecursoNoEncontradoException;
import com.example.apiDocsTICS.Model.UsuariosModel;
import com.example.apiDocsTICS.Service.IUsuariosService;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private IUsuariosService usuarioService;

    @PostMapping("/insertar")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuariosModel usuario) {
        return new ResponseEntity<>(usuarioService.crearUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioId(@PathVariable String id) {
        try {
            ObjectId usuarioId = new ObjectId(id);
            UsuariosModel usuario = usuarioService.buscarUsuarioPorId(usuarioId);
            return ResponseEntity.ok(usuario);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/listarusuarios")
    public ResponseEntity<List<UsuariosModel>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @PutMapping("/actualizarContrasena/{id}")
    public ResponseEntity<String> actualizarContrasena(@PathVariable String id, @RequestBody UsuariosModel usuario) {
        ObjectId usuarioId = new ObjectId(id);
        String resultado = usuarioService.actualizarContrasena(usuarioId, usuario);
        if (resultado.equals("Usuario no encontrado")) {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }
}